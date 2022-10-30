package com.killstan.takeout.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 套餐-菜品连接表
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ComboDish对象", description="套餐-菜品连接表")
public class ComboDish  {

    @ApiModelProperty(value = "自增主键，无实意")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "套餐id")
    private Long comboId;

    @ApiModelProperty(value = "菜品id")
    private Long dishId;

    @ApiModelProperty(value = "菜品份数")
    private Integer count;

    @ApiModelProperty(value = "排序")
    private Integer sort;


}
