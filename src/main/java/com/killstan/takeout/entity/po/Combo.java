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
 * 套餐表
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Combo对象", description="套餐表")
public class Combo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "套餐id")
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
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最后修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建人id")
    private Long createId;

    @ApiModelProperty(value = "最后修改人id")
    private Long updateId;

    @ApiModelProperty(value = "0：删除；1：活跃")
    private Integer isDeleted;


}
