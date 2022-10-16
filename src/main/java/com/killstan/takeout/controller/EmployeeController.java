package com.killstan.takeout.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.killstan.takeout.entity.po.Employee;
import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.service.EmployeeService;
import com.killstan.takeout.util.ThreadLocalForEmp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 员工登录
     *
     * @param employee
     * @param session
     * @return
     */
    @PostMapping("/login")
    public ResultVo<Employee> login(@RequestBody Employee employee, HttpSession session) {

        // 根据 username 查询是否存在该用户
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(lambdaQueryWrapper);
        if (emp == null) {
            return ResultVo.fail("用户名不存在");
        }

        // 对用户名登录的密码进行 md5加密后比较
        String pwMd5 = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        if (!pwMd5.equals(emp.getPassword())) {
            return ResultVo.fail("用户名密码错误");
        }

        // 查看员工是否禁用
        if (1 != emp.getStatus()) {
            return ResultVo.fail("员工已禁用");
        }

        // TODO 将登录员工存入 redis
        // 将员工 id 放入session
        session.setAttribute("employeeId", emp.getEmpId());
        // 因为要返回页面，密码清空
        emp.setPassword(null);
        //　登录成功返回员工信息
        return ResultVo.success(emp);
    }

    /**
     * 登出
     *
     * @param session
     * @return
     */
    @PostMapping("/logout")
    public ResultVo logout(HttpSession session) {
        session.removeAttribute("employeeId");
        //TODO 刪除redis中員工id
        return ResultVo.success(null);
    }

    /**
     * 添加员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public ResultVo addEmployee(@RequestBody Employee employee) {

        // 根据 username 查询是否存在该用户
        String username = employee.getUsername();
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getUsername, username);
        Employee emp = employeeService.getOne(lambdaQueryWrapper);
        if (emp != null) {
            // 如果是禁用账户
            if (emp.getStatus() == 0) {
                return ResultVo.fail("该用户已被禁用");
            }
            return ResultVo.fail("该用户已存在");
        }

        // 设定员工表的属性
        LocalDateTime now = LocalDateTime.now();
        employee.setCreateTime(now);
        employee.setUpdateTime(now);
        Long employeeId = ThreadLocalForEmp.get();
        employee.setCreateId(employeeId);
        employee.setUpdateId(employeeId);
        System.out.println(now);
        // 设定默认密码为123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setStatus(1);
        boolean save = employeeService.save(employee);
        if (save) {
            return ResultVo.success(null);
        } else {
            return ResultVo.fail(null);
        }
    }

    /**
     * 员工管理页面，查询员工列表
     * @param current 当前页
     * @param pageSize 页面数据条数
     * @param empName 员工名
     * @return 员工数据
     */
    @GetMapping("/page")
    public ResultVo<IPage> empList(@RequestParam("page") Integer current, @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "empName", required = false) String empName) {

        Page page = new Page<Employee>(current, pageSize);
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper();
        // 按最后更新时间排序
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        // 如果带了 empName 检索条件
        if (empName != null) {
            lambdaQueryWrapper.like(Employee::getUsername, empName);
        }
        IPage result = employeeService.page(page,lambdaQueryWrapper);
        // 因为要返回页面，密码清空
        List<Employee> records = result.getRecords();
        records.forEach(e -> e.setPassword(null));

        return ResultVo.success(result);
    }

    /**
     * 根据 id 更新员工信息
     * 根据 id 禁用与启用员工
     * @param employee 待更新员工 id与 要更新到的状态
     * @return
     */
    @PutMapping
    public ResultVo updateEmpStatus(@RequestBody Employee employee){

        employee.setUpdateId(ThreadLocalForEmp.get());
        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return ResultVo.success(null);
    }

    /**
     * 根据 id 获取员工信息
     * @param empId
     * @return
     */
    @GetMapping("/{empId}")
    public ResultVo<Employee> getEmpById(@PathVariable("empId") Long empId){
        Employee emp = employeeService.getById(empId);
        if(emp == null){
            return  ResultVo.fail("该用户不存在");
        }
        emp.setPassword(null);
        return ResultVo.success(emp);
    }


}

