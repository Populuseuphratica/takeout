package com.killstan.takeout.service.impl;

import com.killstan.takeout.entity.po.ShoppingCart;
import com.killstan.takeout.mapper.po.ShoppingCartMapper;
import com.killstan.takeout.service.ShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
