package com.killstan.takeout.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 分类表
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Category对象", description="分类表")
public class Category{

    @ApiModelProperty(value = "分类id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long categoryId;

    @ApiModelProperty(value = "0：套餐分类；1：菜品分类")
    private Integer cgType;

    @ApiModelProperty(value = "分类名字")
    private String categoryName;

    @ApiModelProperty(value = "排序，越小越高")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最后修正时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建人id")
    @TableField(fill = FieldFill.INSERT)
    private Long createId;

    @ApiModelProperty(value = "最后修正人id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateId;


}
