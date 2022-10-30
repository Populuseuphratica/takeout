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
 * 套餐表
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Combo对象", description="套餐表")
public class Combo {

    @ApiModelProperty(value = "套餐id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long comboId;

    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "套餐名")
    private String comboName;

    @ApiModelProperty(value = "套餐价格")
    private BigDecimal price;

    @ApiModelProperty(value = "套餐状态 0：停售；1：在售")
    private Integer status;

    private String code;

    private String description;

    private String image;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最后修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建人id")
    @TableField(fill = FieldFill.INSERT)
    private Long createId;

    @ApiModelProperty(value = "最后修改人id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateId;

    @ApiModelProperty(value = "0：删除；1：活跃")
    private Integer isDeleted;


}
