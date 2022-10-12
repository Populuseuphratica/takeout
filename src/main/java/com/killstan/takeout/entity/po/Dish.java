package com.killstan.takeout.entity.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜品
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Dish对象", description="菜品")
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜品id")
    private Long dishId;

    @ApiModelProperty(value = "菜品名")
    private String dishName;

    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "菜品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "商品码")
    private String code;

    @ApiModelProperty(value = "菜品图片路径")
    private String image;

    @ApiModelProperty(value = "菜品描述")
    private String description;

    @ApiModelProperty(value = "菜品状态 0：停售；1：在售")
    private Integer status;

    @ApiModelProperty(value = "排序，越小越前")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最近一次修正时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建人id")
    private Long createId;

    @ApiModelProperty(value = "修正人id")
    private Long updateId;

    @ApiModelProperty(value = "是否删除 0：没删除；1：删除")
    private Integer isDelete;


}
