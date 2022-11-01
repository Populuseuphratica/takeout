package com.killstan.takeout.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class Dish {

    @ApiModelProperty(value = "菜品id")
    @TableId(type = IdType.ASSIGN_ID)
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
    private String imageUrl;

    @ApiModelProperty(value = "菜品描述")
    private String description;

    @ApiModelProperty(value = "菜品状态 0：停售；1：在售")
    private Integer status;

    @ApiModelProperty(value = "排序，越小越前")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最近一次修正时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建人id")
    @TableField(fill = FieldFill.INSERT)
    private Long createId;

    @ApiModelProperty(value = "修正人id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateId;

    @ApiModelProperty(value = "是否删除 0：没删除；1：删除")
    private Integer isDeleted;


}
