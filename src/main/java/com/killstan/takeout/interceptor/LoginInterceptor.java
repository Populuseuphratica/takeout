package com.killstan.takeout.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.util.ThreadLocalForEmp;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/10/13 12:53
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是 HandlerMethod 放行，这样只是不处理能匹配到 @RequestMapping 的记录吗，但可以直接访问 html
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        // 如果用户已登录，将 user 信息存入 ThreadLocal，放行
        HttpSession session = request.getSession();
        Long employeeId = (Long) session.getAttribute("employeeId");
        if(employeeId != null){
            ThreadLocalForEmp.set(employeeId);
            return true;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        // 如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(objectMapper.writeValueAsString(ResultVo.fail("NOTLOGIN")));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 防止内存泄露，移除 ThreadLocal 中数据
        ThreadLocalForEmp.remove();
    }
}
