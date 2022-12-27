package com.killstan.takeout.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description: 订单信息，页面用
 * @Auther: Kill_Stan
 * @Date: 2022/12/26 21:53
 * @Version: v1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderVo {
    private String remark;

    // 支付方式 0:微信,1:支付宝
    private Integer payMethod;

    private Long addressId;
}
