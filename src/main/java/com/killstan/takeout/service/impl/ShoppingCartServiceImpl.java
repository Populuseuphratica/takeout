package com.killstan.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.killstan.takeout.entity.po.ShoppingCart;
import com.killstan.takeout.entity.vo.ShoppingCartVo;
import com.killstan.takeout.mapper.po.ShoppingCartMapper;
import com.killstan.takeout.service.ShoppingCartService;
import com.killstan.takeout.util.ConstantUtil;
import com.killstan.takeout.util.ThreadLocalForId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    private final ShoppingCartMapper shoppingCartMapper;

    private final RedisTemplate redisTemplate;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartMapper shoppingCartMapper, RedisTemplate redisTemplate) {
        this.shoppingCartMapper = shoppingCartMapper;
        this.redisTemplate = redisTemplate;
    }

    /**
     * @Description: 从数据库获取当前用户的购物车
     * @Param: []
     * @Return: java.util.List<com.killstan.takeout.entity.vo.ShoppingCartVo>
     * 无数据返回空List
     * @Author Kill_Stan
     * @Date 2022/12/8 16:52
     */
    @Override
    public List<ShoppingCartVo> listShoppingCart() {
        // 用户id
        long userId = ThreadLocalForId.get();
        List<ShoppingCartVo> shoppingCartVos = shoppingCartMapper.listShoppingCartByUserId(userId);

        // 无数据返回空List
        return shoppingCartVos;
    }

    /**
     * @Description: 添加购物车到数据库
     * @Param: [shoppingCartVos] 如果是null或空，直接返回
     * @Return: void
     * @Author Kill_Stan
     * @Date 2022/12/8 17:20
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addShoppingCart(List<ShoppingCartVo> shoppingCartVos) {
        if (shoppingCartVos == null || shoppingCartVos.size() == 0) {
            return;
        }

        long userId = ThreadLocalForId.get();
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        for (ShoppingCartVo shoppingCartVo : shoppingCartVos) {
            ShoppingCart shoppingCart = new ShoppingCart();
            BeanUtils.copyProperties(shoppingCartVo, shoppingCart);
            shoppingCarts.add(shoppingCart);
        }
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, userId);
        remove(lambdaQueryWrapper);
        saveBatch(shoppingCarts);
    }

    /**
     * @Description: 删除用户的购物车
     * @Param: []
     * @Return: void
     * @Author Kill_Stan
     * @Date 2022/12/27 14:34
     */
    @Override
    public void deleteUserShoppingCart() {
        // 用户id
        long userId = ThreadLocalForId.get();
        // redis 中用户购物车的 key
        String redisShoppingCartKey = ConstantUtil.REDIS_SHOP_CART + userId;
        redisTemplate.delete(redisShoppingCartKey);
        redisTemplate.opsForSet().remove(ConstantUtil.REDIS_SHOP_CART_SET, userId);
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, userId);
        remove(lambdaQueryWrapper);
    }
}
