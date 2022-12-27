package com.killstan.takeout.mapper.po;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.killstan.takeout.entity.po.Orders;
import com.killstan.takeout.entity.vo.OrdersVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
public interface OrderMapper extends BaseMapper<Orders> {

    IPage<OrdersVo> getUserOrders(IPage<OrdersVo> page, @Param("userId") Long userId);

}
