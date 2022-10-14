package com.killstan.takeout.util;

import com.killstan.takeout.entity.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/10/14 17:24
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultVo<String> handleAllException(Exception e){
        log.error("----------------------------出现异常：----------------------------");
        log.error("异常消息为"+e.getMessage());

        return ResultVo.fail(e.getMessage());
    }

}
