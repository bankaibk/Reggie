package com.example.reggie.service;

import com.example.reggie.pojo.Employee;
import com.example.reggie.pojo.PageBean;

/**
 * @author lay
 * @version 1.0
 */
public interface EmpService {
    Employee getByUsername(String username);

    void addEmp(Employee employee);

    PageBean<Employee> page(Integer page, Integer pageSize, String name);

    void updateById(Employee employee,Long updateUserId);

    Employee getById(Long id);
}
