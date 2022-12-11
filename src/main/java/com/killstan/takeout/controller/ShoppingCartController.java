package com.killstan.takeout.controller;


import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.entity.vo.ShoppingCartVo;
import com.killstan.takeout.service.ShoppingCartService;
import com.killstan.takeout.util.ConstantUtil;
import com.killstan.takeout.util.ThreadLocalForId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    /**
     * @Description: 获取当前用户购物车
     * @Param: []
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/12/10 21:12
     */
    @GetMapping("/list")
    public ResultVo listShoppingCart() {

        List<ShoppingCartVo> shoppingCartVos = getShoppingCartList();

        return ResultVo.success(shoppingCartVos);
    }

    @PostMapping("/add")
    public ResultVo add(@RequestBody ShoppingCartVo shoppingCartVo) {
        List<ShoppingCartVo> shoppingCartVos = getShoppingCartList();
        // 用户id
        long userId = ThreadLocalForId.get();
        shoppingCartVo.setUserId(userId);
        if (shoppingCartVo.getCount() == null) {
            shoppingCartVo.setCount(1);
        }
        // redis 中用户购物车的 key
        String redisShoppingCart = ConstantUtil.REDIS_SHOP_CART + userId;

        // 将购物车信息加入 redis 中
        shoppingCartVos.add(shoppingCartVo);
        redisTemplate.opsForValue().set(redisShoppingCart, shoppingCartVos, Long.parseLong(ConstantUtil.REDIS_DATA_TIME), TimeUnit.DAYS);
        // TODO 现阶段测试用，以后删除
        shoppingCartService.addShoppingCart(shoppingCartVos);

        return ResultVo.success(null);
    }

    @PostMapping("/shoppingCart/sub")
    public ResultVo subtract(@RequestBody ShoppingCartVo shoppingCartVo) {

        // TODO 返回菜品count
        return ResultVo.success(null);
    }

    /**
     * @Description: 获取当前用户购物车
     * @Param: []
     * @Return: java.util.List<com.killstan.takeout.entity.vo.ShoppingCartVo>
     * @Author Kill_Stan
     * @Date 2022/12/10 21:11
     */
    private List<ShoppingCartVo> getShoppingCartList() {
        // 用户id
        long userId = ThreadLocalForId.get();
        // redis 中用户购物车的 key
        String redisShoppingCart = ConstantUtil.REDIS_SHOP_CART + userId;
        // 先从 redis 中取
        Object obj = redisTemplate.opsForValue().get(redisShoppingCart);

        // redis 中不存在时，从数据库中取
        List<ShoppingCartVo> shoppingCartVos = null;
        if (obj == null) {
            shoppingCartVos = shoppingCartService.listShoppingCart();
        } else {
            shoppingCartVos = (List<ShoppingCartVo>) obj;
        }

        return shoppingCartVos;
    }

}

