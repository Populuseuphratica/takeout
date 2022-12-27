package com.killstan.takeout.entity.vo;

import com.killstan.takeout.entity.po.OrderDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OrdersVo对象", description = "前端用订单对象")
public class OrdersVo {

    @ApiModelProperty(value = "订单号")
    private Long orderId;

    @ApiModelProperty(value = "0：待付款；1：待派送；2：派送中；3：已收货：4：取消")
    private Integer status;

    @ApiModelProperty(value = "下单用户id")
    private Long userId;

    @ApiModelProperty(value = "下单用户名，避免连接查询的冗余字段")
    private String username;

    @ApiModelProperty(value = "下单时间")
    private LocalDateTime orderTime;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "当前订单总菜品/套餐数量")
    private Integer sumCount;

    @ApiModelProperty(value = "订单详细")
    private List<OrderDetail> orderDetailList;

}
