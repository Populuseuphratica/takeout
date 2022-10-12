package com.killstan.takeout.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.killstan.takeout.entity.po.Employee;
import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 员工表 前端控制器
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public ResultVo login(@RequestBody Employee employee) {

        String pwMd5 = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getPassword,pwMd5);
        Employee emp = employeeService.getOne(lambdaQueryWrapper);

        System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
        if(emp == null){
            return ResultVo.fail("用户名或密码错误");
        }

        return ResultVo.success(emp);
    }

}

