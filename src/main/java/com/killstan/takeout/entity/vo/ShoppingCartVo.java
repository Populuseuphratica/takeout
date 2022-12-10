package com.killstan.takeout.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 购物车表
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ShoppingCartVo对象", description = "前段用-用户购物车")
public class ShoppingCartVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增id，无实意")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "菜品id")
    private Long dishId;

    @ApiModelProperty(value = "菜品名")
    private String dishName;

    @ApiModelProperty(value = "套餐id")
    private Long comboId;

    @ApiModelProperty(value = "套餐名")
    private String comboName;

    @ApiModelProperty(value = "菜品口味")
    private String flavor;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "图片url")
    private String imageUrl;

}
