package com.killstan.takeout.entity.po;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "0：女；1：男")
    private Integer sex;

    private String idCard;

    @ApiModelProperty(value = "0：删除；1：活跃")
    private Integer isDeleted;

    @ApiModelProperty(value = "头像图片地址")
    private String profilePicture;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
