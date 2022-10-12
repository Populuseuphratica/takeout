package com.killstan.takeout.entity.po;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "0：菜品分类；1：套餐分类")
    private Integer cgType;

    @ApiModelProperty(value = "分类名字")
    private String categoryName;

    @ApiModelProperty(value = "排序，越小越高")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最后修正时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建人id")
    private Integer createId;

    @ApiModelProperty(value = "最后修正人id")
    private Integer updateId;


}
