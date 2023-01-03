package com.killstan.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.killstan.takeout.entity.po.User;
import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.mapper.po.UserMapper;
import com.killstan.takeout.service.UserService;
import com.killstan.takeout.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @Description: 用户登陆，如果用户不存在，则注册。
     * @Param: [userInfoMap] phone，code
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/11/27 12:35
     */
    @Override
    public ResultVo loginOrRegister(Map userInfoMap, HttpSession session) {

        String phone = (String) userInfoMap.get("phone");
        String code = (String) userInfoMap.get("code");
        // 手机号校验
        if (!Pattern.matches(ConstantUtil.REGEX_PHONE, phone)) {
            return ResultVo.fail("请输入正确的手机号");
        }
        if (code == null) {
            return ResultVo.fail("请输入验证码");
        }

        // 验证码不匹配时，返回
        String redisCode = (String) redisTemplate.opsForValue().get(ConstantUtil.REDIS_CODE_PREFIX + phone);
        if (redisCode == null || !code.equals(redisCode)) {
            return ResultVo.fail("验证码错误，请重新输入");
        }

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getPhone, phone);
        User user = getOne(lambdaQueryWrapper);

        // 如果 user 不存在，注册
        if (user == null) {
            user = new User();
            user.setPhone(phone);
            // 设置手机号为默认用户名
            user.setUsername(phone);
            // user.setIsDeleted(0);
            save(user);
        }

        // 将 user 信息存入 redis 中，期限为 1 天
        // redisTemplate.opsForValue().set(ConstantUtil.REDIS_USER_PREFIX + user.getUserId(), user, 1, TimeUnit.DAYS);

        // 将 userID 存入 session 中
        session.setAttribute(ConstantUtil.SESSION_USER_ID, user.getUserId());

        return ResultVo.success(null);
    }

}
