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
 * 地址表
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Address对象", description = "地址表")
public class Address {

    @ApiModelProperty(value = "地址id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long addressId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "收货人姓名")
    private String consignee;

    @ApiModelProperty(value = "性别 0：女；1：男")
    private Integer sex;

    @ApiModelProperty(value = "收货人手机")
    private String consigneePhone;

    @ApiModelProperty(value = "省级区编号")
    private String provinceCode;

    @ApiModelProperty(value = "省级区名称")
    private String provinceName;

    @ApiModelProperty(value = "市级区编号")
    private String cityCode;

    @ApiModelProperty(value = "市级区名称")
    private String cityName;

    @ApiModelProperty(value = "区级区划编号")
    private String districtCode;

    @ApiModelProperty(value = "区级名称")
    private String districtName;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "是否默认 0：否；1为是")
    private Integer isDefault;

    @ApiModelProperty(value = "最后修正时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除 0：没删除；1：删除")
    private Integer isDeleted;


}
