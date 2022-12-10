package com.killstan.takeout.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.util.ConstantUtil;
import com.killstan.takeout.util.ThreadLocalForId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/10/13 12:53
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private final RedisTemplate redisTemplate;

    @Autowired
    public LoginInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * @Description: 拦截请求，检验是否登陆
     * @Param: [request, response, handler]
     * @Return: boolean
     * @Author Kill_Stan
     * @Date 2022/11/27 12:45
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是 HandlerMethod 放行，这样只是不处理能匹配到 @RequestMapping 的记录吗，但可以直接访问 html
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 如果用户已登录，将 employeeId 信息存入 ThreadLocal，放行
        HttpSession session = request.getSession();
        Long employeeId = (Long) session.getAttribute("employeeId");
        if (employeeId != null) {
            log.info("后台账号：" + employeeId + "登陆，时间为：" + LocalDateTime.now());
            ThreadLocalForId.set(employeeId);
            return true;
        }

        // 如果用户已登录，将 userId 信息存入 ThreadLocal，放行
        Long userId = (Long) session.getAttribute(ConstantUtil.SESSION_USER_ID);
        if (userId != null) {
            log.info("前台账号：" + userId + "登陆，时间为：" + LocalDateTime.now());
            ThreadLocalForId.set(userId);
            return true;
        }


        // 如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        ObjectMapper objectMapper = new ObjectMapper();
        // 前端会判断返回，如果 msg 为 NOT_LOGIN，自动跳转登陆页面
        response.getWriter().write(objectMapper.writeValueAsString(ResultVo.fail("NOT_LOGIN")));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 防止内存泄露，移除 ThreadLocal 中数据
        ThreadLocalForId.remove();
    }
}
