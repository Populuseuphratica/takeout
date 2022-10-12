package com.killstan.takeout.entity.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OrderDetail对象", description="订单明细表")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "递增主键，无实际意义")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单号")
    private Long orderId;

    @ApiModelProperty(value = "菜品id，统计数据用")
    private Long dishId;

    @ApiModelProperty(value = "菜品价格，为了有历史记录记载")
    private BigDecimal dishPrice;

    @ApiModelProperty(value = "菜品数量")
    private Integer dishCount;

    @ApiModelProperty(value = "菜品图片")
    private String dishImage;

    @ApiModelProperty(value = "套餐id")
    private Long comboId;

    @ApiModelProperty(value = "口味")
    private String flavor;


}
