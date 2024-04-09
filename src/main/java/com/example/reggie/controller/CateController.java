package com.example.reggie.controller;

import com.example.reggie.pojo.*;
import com.example.reggie.service.CateService;
import com.example.reggie.service.DishService;
import com.example.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lay
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CateController {
    @Resource
    private CateService cateService;

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser((Long) request.getSession().getAttribute("employee"));
        category.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        cateService.save(category);
        return R.success("新增分类成功");
    }

    @GetMapping("/page")
    public R<PageBean<Category>> page(Integer page, Integer pageSize, String name) {
        log.info("page = {}, pageSie = {}, name = {}", page, pageSize, name);
        PageBean<Category> pageBean = cateService.page(page, pageSize, name);
        return R.success(pageBean);
    }

    @DeleteMapping
    public R<String> delete(Long ids) {
        cateService.delete(ids);
        return R.success("分类删除成功");
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Category category) {
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        cateService.update(category);
        return R.success("修改分类成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        List<Category> categories =  cateService.list(category);
        return R.success(categories);
    }
}
