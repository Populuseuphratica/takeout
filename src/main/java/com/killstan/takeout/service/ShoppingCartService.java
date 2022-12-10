package com.killstan.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.killstan.takeout.entity.po.ShoppingCart;
import com.killstan.takeout.entity.vo.ShoppingCartVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
public interface ShoppingCartService extends IService<ShoppingCart> {

    /**
     * @Description: 从数据库获取当前用户的购物车
     * @Param: []
     * @Return: java.util.List<com.killstan.takeout.entity.vo.ShoppingCartVo>
     * null:无数据
     * @Author Kill_Stan
     * @Date 2022/12/8 16:52
     */
    List<ShoppingCartVo> listShoppingCart();

    /**
     * @Description: 添加购物车到数据库
     * @Param: [shoppingCartVos] 如果是null或空，直接返回
     * @Return: void
     * @Author Kill_Stan
     * @Date 2022/12/8 17:20
     */
    @Transactional(rollbackFor = Exception.class)
    void addShoppingCart(List<ShoppingCartVo> shoppingCartVos);
}
