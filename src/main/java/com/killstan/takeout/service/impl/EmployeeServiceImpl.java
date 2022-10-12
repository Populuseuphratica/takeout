package com.killstan.takeout.service.impl;

import com.killstan.takeout.entity.po.Employee;
import com.killstan.takeout.mapper.po.EmployeeMapper;
import com.killstan.takeout.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工表 服务实现类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
