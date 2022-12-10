package com.killstan.takeout.controller;


import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.entity.vo.ShoppingCartVo;
import com.killstan.takeout.service.ShoppingCartService;
import com.killstan.takeout.util.ConstantUtil;
import com.killstan.takeout.util.ThreadLocalForId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 购物车表 前端控制器
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, RedisTemplate<String, Object> redisTemplate) {
        this.shoppingCartService = shoppingCartService;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/list")
    public ResultVo listShoppingCart() {
        List<ShoppingCartVo> shoppingCartVos = shoppingCartService.listShoppingCart();

        return ResultVo.success(shoppingCartVos);
    }

    @PostMapping("/add")
    public ResultVo add(@RequestBody ShoppingCartVo shoppingCartVo) {
        // 用户id
        long userId = ThreadLocalForId.get();
        shoppingCartVo.setUserId(userId);
        // redis 中用户购物车的 key
        String redisShoppingCart = ConstantUtil.REDIS_SHOP_CART + userId;
        // 先从 redis 中取
        List<ShoppingCartVo> shoppingCartVos = (List<ShoppingCartVo>) redisTemplate.opsForValue().get(redisShoppingCart);
        if (shoppingCartVos == null) {
            // redis 中不存在时，从数据库中取
            shoppingCartVos = shoppingCartService.listShoppingCart();
            if (shoppingCartVos == null) {
                // 数据库中也不存在时，新建
                shoppingCartVos = new ArrayList<>();
            }
        }

        // 将购物车信息加入 redis 中
        shoppingCartVos.add(shoppingCartVo);
        redisTemplate.opsForValue().set(redisShoppingCart, shoppingCartVos);
        // TODO 现阶段测试用，以后删除
        shoppingCartService.addShoppingCart(shoppingCartVos);

        return ResultVo.success(null);
    }

    @PostMapping("/shoppingCart/sub")
    public ResultVo subtract(@RequestBody ShoppingCartVo shoppingCartVo) {

        // TODO 返回菜品count
        return ResultVo.success(null);
    }

}

