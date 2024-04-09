package com.example.reggie.controller;

import com.example.reggie.pojo.Employee;
import com.example.reggie.pojo.PageBean;
import com.example.reggie.pojo.R;
import com.example.reggie.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author lay
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmpController {
    @Resource
    private EmpService empService;

    @PostMapping("/login")
    // @RequestBody是对json格式数据接收的注解
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 1.将页面提交的密码进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        // 2.根据用户名查询数据库
        Employee emp = empService.getByUsername(employee.getUsername());
        // 3.未查询到返回用户名错误
        if (emp == null) {
            return R.error("用户名错误");
        }
        // 4.查询到需要比对密码
        if (!password.equals(emp.getPassword())) {
            return R.error("密码错误");
        }
        // 5.查询员工是否为禁用状态
        if (emp.getStatus() == 0) {
            return R.error("用户失效");
        }
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出登录");
    }

    @PostMapping
    public R<String> addEmp(HttpServletRequest request, @RequestBody Employee employee) {
        // 设置初始密码以及其他信息
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        empService.addEmp(employee);
        return R.success("添加员工成功");
    }

    @GetMapping("/page")
    public R<PageBean<Employee>> page(Integer page, Integer pageSize, String name) {
        log.info("page = {}, pageSie = {}, name = {}", page, pageSize, name);
        PageBean<Employee> pageBean = empService.page(page, pageSize, name);
        return R.success(pageBean);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        Long updateUserId = (Long) request.getSession().getAttribute("employee");
        empService.updateById(employee, updateUserId);
        return R.success("员工信息修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        Employee emp = empService.getById(id);
        return R.success(emp);
    }
}
