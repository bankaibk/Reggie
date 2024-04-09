package com.example.reggie.service.impl;

import com.example.reggie.mapper.EmpMapper;
import com.example.reggie.pojo.Employee;
import com.example.reggie.pojo.PageBean;
import com.example.reggie.service.EmpService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lay
 * @version 1.0
 */
@Service
public class EmpServiceImpl implements EmpService {
    @Resource
    private EmpMapper empMapper;

    @Override
    public Employee getByUsername(String username) {
        return empMapper.getByUsername(username);
    }

    @Override
    public void addEmp(Employee employee) {
        empMapper.addEmp(employee);
    }

    @Override
    public PageBean<Employee> page(Integer page, Integer pageSize, String name) {
        Long total = empMapper.count();
        List<Employee> records;
        if (StringUtils.isEmpty(name)) {
            records = empMapper.pageNull((page - 1) * pageSize, pageSize);
        } else {
            records = empMapper.page((page - 1) * pageSize, pageSize, name);
        }
        PageBean<Employee> pageBean = new PageBean<>(total, records);
        return pageBean;
    }

    @Override
    public void updateById(Employee employee, Long updateUserId) {
        employee.setUpdateUser(updateUserId);
        employee.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(employee);
    }

    @Override
    public Employee getById(Long id) {
        Employee emp = empMapper.getById(id);
        return emp;
    }
}
