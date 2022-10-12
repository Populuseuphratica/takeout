package com.killstan.takeout.service.impl;

import com.killstan.takeout.entity.po.Order;
import com.killstan.takeout.mapper.po.OrderMapper;
import com.killstan.takeout.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
