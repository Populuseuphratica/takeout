package com.killstan.takeout.controller;


import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.service.UserService;
import com.killstan.takeout.util.ConstantUtil;
import com.killstan.takeout.util.RandomUtil;
import com.killstan.takeout.util.sms.SendSmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final RedisTemplate redisTemplate;

    private final SendSmsUtil sendSmsUtil;

    @Autowired
    public UserController(UserService userService, RedisTemplate redisTemplate, SendSmsUtil sendSmsUtil) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        this.sendSmsUtil = sendSmsUtil;
    }

    /**
     * @Description: 登陆或者注册用户
     * @Param: [userInfoMap] phone，code
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/11/27 12:38
     */
    @PostMapping("/login")
    public ResultVo login(@RequestBody Map userInfoMap, HttpSession session) {
        ResultVo resultVo = userService.loginOrRegister(userInfoMap, session);
        return resultVo;
    }

    /**
     * @Description: 发送验证码
     * @Param: [phone]
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/11/27 12:40
     */
    @PostMapping("/sendMsg")
    public ResultVo sendMsg(@RequestBody Map userInfoMap) {

        String phone = (String) userInfoMap.get("phone");
        // 手机号校验
        if (!Pattern.matches(ConstantUtil.REGEX_PHONE, phone)) {
            return ResultVo.fail("请输入正确的手机号");
        }
        // 获取6位随机数字验证码
        String code = RandomUtil.getSixBitRandom();
        boolean isSend = sendSmsUtil.send(phone, code);

        if (!isSend) {
            return ResultVo.fail("短信发送失败，请重试");
        }

        // 将验证码存入 redis
        redisTemplate.opsForValue().set(ConstantUtil.REDIS_CODE_PREFIX + phone, code, Long.parseLong(ConstantUtil.REDIS_CODE_TIME), TimeUnit.MINUTES);

        return ResultVo.success(null);
    }

    /**
     * @Description: 用户登出
     * @Param: []
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2023/1/3 16:44
     */
    @PostMapping("/logout")
    public ResultVo logout(HttpSession session) {
        session.removeAttribute(ConstantUtil.SESSION_USER_ID);

        return ResultVo.success(null);
    }

}

