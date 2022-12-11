package com.killstan.takeout.util;

/**
 * @Description:
 * @Auther: Kill_Stan
 * @Date: 2022/11/27 12:33
 * @Version: v1.0
 */
public class ConstantUtil {

    // 验证手机号码的正则表达式
    public static final String REGEX_PHONE = "^[1]([3-9])[0-9]{9}$";

    // redis 中存用户信息的 key 前缀
    public static final String REDIS_USER_PREFIX = "loginUser";

    // redis 中，手机登陆验证码的有效时间(请设置单位为分钟)
    public static final String REDIS_CODE_TIME = "5";

    // redis 中，各种数据的有效时间(请设置单位为天)
    public static final String REDIS_DATA_TIME = "1";

    // redis 中，手机登陆验证码的 key 前缀
    public static final String REDIS_CODE_PREFIX = "PHONE_CODE_";

    // redis 中，用户购物车前缀，后面+用户id
    public static final String REDIS_SHOP_CART = "SHOP_CART_";

    // session 中存用户 id 的 key
    public static final String SESSION_USER_ID = "loginUserId";


}
