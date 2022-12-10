package com.killstan.takeout.mapper.po;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.killstan.takeout.entity.po.ShoppingCart;
import com.killstan.takeout.entity.vo.ShoppingCartVo;

import java.util.List;

/**
 * <p>
 * 购物车表 Mapper 接口
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

    List<ShoppingCartVo> listShoppingCartByUserId(long userId);
}
