package com.killstan.takeout.entity.vo;

import lombok.Data;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description: 返回前端结果类
 * @date 2022/10/12 18:34
 */
@Data
public class ResultVo<T> {

    /**
     * 编码：1成功，0和其它数字为失败
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 成功时返回
     *
     * @param object 返回数据
     * @param <T>    返回数据类型
     * @return
     */
    public static <T> ResultVo<T> success(T object) {
        ResultVo<T> resultVo = new ResultVo<T>();
        resultVo.data = object;
        resultVo.code = 1;
        return resultVo;
    }

    /**
     * 失败时返回
     *
     * @param message 错误信息
     * @return
     */
    public static ResultVo fail(String message) {
        ResultVo resultVo = new ResultVo();
        resultVo.code = 0;
        resultVo.msg = message;
        return resultVo;
    }

    /**
     * 失败时返回
     *
     * @param code    错误代码
     * @param message 错误信息
     * @return
     */
    public static ResultVo fail(Integer code, String message) {
        ResultVo resultVo = new ResultVo();
        resultVo.code = code;
        resultVo.msg = message;
        return resultVo;
    }
}
