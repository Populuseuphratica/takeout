package com.killstan.takeout.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.killstan.takeout.entity.po.Flavor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
@ApiModel(value = "Dish对象", description = "前端用-菜品")
public class DishVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜品id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long dishId;

    @ApiModelProperty(value = "菜品名")
    private String dishName;

    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "分类名")
    private String categoryName;

    @ApiModelProperty(value = "菜品数量")
    private Integer count;

    @ApiModelProperty(value = "菜品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "商品码")
    private String code;

    @ApiModelProperty(value = "菜品图片路径")
    private String imageUrl;

    @ApiModelProperty(value = "菜品描述")
    private String description;

    @ApiModelProperty(value = "菜品状态 0：停售；1：在售")
    private Integer status;

    @ApiModelProperty(value = "排序，越小越前")
    private Integer sort;

    @ApiModelProperty(value = "菜品口味list")
    private List<Flavor> flavors;

    @ApiModelProperty(value = "最近一次修正时间")
    private LocalDateTime updateTime;

}
