package com.killstan.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.killstan.takeout.entity.po.User;
import com.killstan.takeout.entity.vo.ResultVo;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
public interface UserService extends IService<User> {

    ResultVo loginOrRegister(Map userInfoMap, HttpSession session);
}
