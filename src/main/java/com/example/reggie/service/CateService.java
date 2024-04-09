package com.example.reggie.service;

import com.example.reggie.pojo.Category;
import com.example.reggie.pojo.PageBean;

import java.util.List;

/**
 * @author lay
 * @version 1.0
 */
public interface CateService {
    void save(Category category);

    PageBean<Category> page(Integer page, Integer pageSize, String name);

    void delete(Long id);

    void update(Category category);

    List<Category> list(Category category);

    Category getById(Long categoryId);
}
