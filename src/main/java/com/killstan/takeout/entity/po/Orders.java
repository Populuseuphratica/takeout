package com.killstan.takeout.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@ApiModel(value = "Orders对象", description = "")
public class Orders {

    @ApiModelProperty(value = "订单号，雪花算法")
    @TableId(type = IdType.ASSIGN_ID)
    private Long orderId;

    @ApiModelProperty(value = "0：待付款；1：待派送；2：派送中；3：已收货：4：取消")
    private Integer status;

    @ApiModelProperty(value = "下单用户id")
    private Long userId;

    @ApiModelProperty(value = "下单用户名，避免连接查询的冗余字段")
    private String username;

    @ApiModelProperty(value = "下单时间")
    private LocalDateTime orderTime;

    @ApiModelProperty(value = "收获手机号")
    private String consigneePhone;

    @ApiModelProperty(value = "收获人名")
    private String consignee;

    @ApiModelProperty(value = "收获地址")
    private String address;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "结账时间")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "支付方式 0:微信,1:支付宝")
    private Integer payMethod;


}
