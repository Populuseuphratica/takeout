package com.killstan.takeout.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.killstan.takeout.entity.po.ComboDish;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
@ApiModel(value = "Combo对象", description = "前端用-套餐")
public class ComboVo {

    @ApiModelProperty(value = "套餐id")
    private Long comboId;

    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "分类名")
    private String categoryName;

    @ApiModelProperty(value = "套餐名")
    private String comboName;

    @ApiModelProperty(value = "套餐价格")
    private BigDecimal price;

    @ApiModelProperty(value = "套餐状态 0：停售；1：在售")
    private Integer status;

    private String code;

    private String description;

    private String imageUrl;

    private List<ComboDish> comboDishes;

    @ApiModelProperty(value = "最后修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @ApiModelProperty(value = "最后修改人id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateId;


}
