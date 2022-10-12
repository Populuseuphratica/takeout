package com.killstan.takeout.service.impl;

import com.killstan.takeout.entity.po.OrderDetail;
import com.killstan.takeout.mapper.po.OrderDetailMapper;
import com.killstan.takeout.service.IOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

}
