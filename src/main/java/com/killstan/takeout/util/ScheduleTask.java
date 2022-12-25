package com.killstan.takeout.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.killstan.takeout.entity.po.ShoppingCart;
import com.killstan.takeout.entity.vo.ShoppingCartVo;
import com.killstan.takeout.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 定时任务
 * @Auther: Kill_Stan
 * @Date: 2022/12/22 17:19
 * @Version: v1.0
 */
@Configuration
@Slf4j
public class ScheduleTask {

    private final RedisTemplate redisTemplate;

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ScheduleTask(RedisTemplate redisTemplate, ShoppingCartService shoppingCartService) {
        this.redisTemplate = redisTemplate;
        this.shoppingCartService = shoppingCartService;
    }

    /**
     * @Description: 每天凌晨3点，将redis中购物车数据同步到数据库中
     * @Param: []
     * @Return: void
     * @Author Kill_Stan
     * @Date 2022/12/22 17:25
     */
    @Scheduled(cron = "0 0 3 * * ?")
    private void updateShoppingCart() {
        SetOperations setOperations = redisTemplate.opsForSet();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Long setSize = setOperations.size(ConstantUtil.REDIS_SHOP_CART_SET);
        if (setSize == null) {
            return;
        }
        int size = setSize.intValue();

        for (int i = 0; i < size; i++) {
            Long userId = (Long) setOperations.pop(ConstantUtil.REDIS_SHOP_CART_SET);
            Object o = valueOperations.get(ConstantUtil.REDIS_SHOP_CART + userId);
            if (o == null) {
                continue;
            }
            // 将每个用户的购物车更新
            List<ShoppingCartVo> shoppingCartVoList = (List<ShoppingCartVo>) o;
            List<ShoppingCart> shoppingCartList = new ArrayList<>();
            for (ShoppingCartVo shoppingCartVo : shoppingCartVoList) {
                ShoppingCart shoppingCart = new ShoppingCart();
                BeanUtils.copyProperties(shoppingCartVo, shoppingCart);
                shoppingCartList.add(shoppingCart);
            }
            LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ShoppingCart::getUserId, userId);
            LocalDateTime localDateTime = LocalDateTime.now();
            log.info(localDateTime + "----------更新用户" + userId + "购物车");
            shoppingCartService.remove(lambdaQueryWrapper);
            if (shoppingCartList.size() > 0) {
                shoppingCartService.saveBatch(shoppingCartList);
            }
            // 数据库中更新完成后redis中不用删除，等待其过期
        }
    }
}
