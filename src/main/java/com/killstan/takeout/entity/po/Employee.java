package com.killstan.takeout.entity.po;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 员工表
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Employee对象", description="员工表")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键：自增id")
    private Long empId;

    @ApiModelProperty(value = "员工名")
    private String empName;

    @ApiModelProperty(value = "登录账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "性别，0：默认，1：男，2：女")
    private Integer sex;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "状态，0:禁用，1:正常")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建人id")
    private Long createId;

    @ApiModelProperty(value = "修改人id")
    private Long updateId;


}
