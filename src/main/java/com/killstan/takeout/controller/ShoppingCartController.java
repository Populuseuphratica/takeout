package com.killstan.takeout.controller;


import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.entity.vo.ShoppingCartVo;
import com.killstan.takeout.service.ShoppingCartService;
import com.killstan.takeout.util.ConstantUtil;
import com.killstan.takeout.util.ThreadLocalForId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
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

    /**
     * @Description: 添加菜品/套餐到购物车
     * @Param: [shoppingCartVo]
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/12/12 22:36
     */
    @PostMapping("/add")
    public ResultVo add(@RequestBody ShoppingCartVo shoppingCartVo) {
        // 获取购物车里已有数据
        List<ShoppingCartVo> shoppingCartVos = getShoppingCartList();

        // 检查添加菜品/套餐的数量
        Integer count = shoppingCartVo.getCount();
        if (count == null) {
            count = 1;
            shoppingCartVo.setCount(count);
        }

        // 取得其套餐/菜品id
        long addId = shoppingCartVo.getComboId() == null ? shoppingCartVo.getDishId() : shoppingCartVo.getComboId();
        boolean added = false;
        // 如果时购物车中有的菜品/套餐，增加其数量
        for (ShoppingCartVo cartVo : shoppingCartVos) {
            long id = cartVo.getComboId() == null ? cartVo.getDishId() : cartVo.getComboId();
            if (addId == id) {
                Integer voCount = cartVo.getCount();
                cartVo.setCount(voCount + count);
                added = true;
            }
        }

        // 用户id
        long userId = ThreadLocalForId.get();
        // 如果是购物车中没有的菜品，添加
        if (!added) {
            shoppingCartVo.setUserId(userId);
            // 将购物车信息加入 redis 中
            shoppingCartVos.add(shoppingCartVo);
        }

        // 向 redis 中存入用户购物车
        String redisShoppingCart = ConstantUtil.REDIS_SHOP_CART + userId;
        redisTemplate.opsForValue().set(redisShoppingCart, shoppingCartVos, Long.parseLong(ConstantUtil.REDIS_DATA_TIME), TimeUnit.DAYS);
        redisTemplate.opsForSet().add(ConstantUtil.REDIS_SHOP_CART_SET, userId);
        // TODO 现阶段测试用，以后删除
        shoppingCartService.addShoppingCart(shoppingCartVos);

        return ResultVo.success(null);
    }

    /**
     * @Description: 减少购物车中菜品的数量
     * @Param: [shoppingCartVo]
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/12/21 21:44
     */
    @PostMapping("/sub")
    public ResultVo subtract(@RequestBody ShoppingCartVo shoppingCartVo) {
        // 获取购物车里已有数据
        List<ShoppingCartVo> shoppingCartVos = getShoppingCartList();

        // 取得其套餐/菜品id
        long subId = shoppingCartVo.getComboId() == null ? shoppingCartVo.getDishId() : shoppingCartVo.getComboId();

        Iterator<ShoppingCartVo> iterator = shoppingCartVos.iterator();
        while (iterator.hasNext()) {
            ShoppingCartVo cartVo = iterator.next();
            long id = cartVo.getComboId() == null ? cartVo.getDishId() : cartVo.getComboId();
            // 找到购物车中对应菜品
            if (subId == id) {
                Integer voCount = cartVo.getCount() - 1;
                // 如果删除的是最后一件，将其从购物车中删除
                if (voCount <= 0) {
                    iterator.remove();
                    cartVo.setCount(0);
                } else {
                    cartVo.setCount(voCount);
                }

                // 用户id
                long userId = ThreadLocalForId.get();
                // 向 redis 中存入用户购物车
                String redisShoppingCart = ConstantUtil.REDIS_SHOP_CART + userId;
                redisTemplate.opsForValue().set(redisShoppingCart, shoppingCartVos, Long.parseLong(ConstantUtil.REDIS_DATA_TIME), TimeUnit.DAYS);
                // 返回修改数量后的菜品
                return ResultVo.success(cartVo);
            }
        }

        // 没找到相应菜品也不用报错，可能是并发问题被删除，返回 null
        return ResultVo.success(null);
    }

    private void addCartToRedis(List<ShoppingCartVo> shoppingCartVoList) {

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

