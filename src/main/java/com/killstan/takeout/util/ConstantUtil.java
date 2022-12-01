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

    // session 中存用户 id 的 key
    public static final String REDIS_CODE_TIME = "5";

    // session 中存用户 id 的 key
    public static final String SESSION_USER_ID = "loginUserId";


}
