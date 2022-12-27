package com.killstan.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.killstan.takeout.entity.po.Orders;
import com.killstan.takeout.entity.vo.OrderVo;
import com.killstan.takeout.entity.vo.ResultVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
public interface OrderService extends IService<Orders> {

    @Transactional(rollbackFor = Exception.class)
    ResultVo submitOrder(OrderVo orderVo);
}
