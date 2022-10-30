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
 * 口味表
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Flavor对象", description="口味表")
public class Flavor {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "菜品id")
    private Long dishId;

    @ApiModelProperty(value = "口味名称")
    private String name;

    @ApiModelProperty(value = "口味数据")
    private String value;

    @ApiModelProperty(value = "最后修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateId;

    @ApiModelProperty(value = "0：使用；1：删除")
    private Integer isDeleted;


}
