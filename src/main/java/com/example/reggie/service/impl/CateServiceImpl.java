package com.example.reggie.service.impl;

import com.example.reggie.common.CustomException;
import com.example.reggie.common.SnowflakeIdWorker;
import com.example.reggie.mapper.CateMapper;
import com.example.reggie.pojo.Category;
import com.example.reggie.pojo.PageBean;
import com.example.reggie.service.CateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lay
 * @version 1.0
 */
@Slf4j
@Service
public class CateServiceImpl implements CateService {
    @Resource
    private CateMapper cateMapper;

    @Override
    public void save(Category category) {
        long id = SnowflakeIdWorker.generator();
        category.setId(id);
        cateMapper.save(category);
    }

    @Override
    public PageBean<Category> page(Integer page, Integer pageSize, String name) {
        Long total = cateMapper.count();
        List<Category> records;
        records = cateMapper.page((page - 1) * pageSize, pageSize, name);
        PageBean<Category> pageBean = new PageBean(total, records);
        return pageBean;
    }

    @Override
    public void delete(Long id) {
        //查询当前分类是否关联了菜品
        int countDish = cateMapper.countDishById(id);
        if (countDish > 0) {
            throw new CustomException("当前项关联了菜品");
        }
        //查询当前分类是否关联了套餐
        int countSetmeal = cateMapper.countSetmealById(id);
        if (countSetmeal > 0) {
            throw new CustomException("当前项关联了套餐");
        }
        //正常删除分类
        cateMapper.delete(id);
    }

    @Override
    public void update(Category category) {
        cateMapper.update(category);
    }

    @Override
    public List<Category> list(Category category) {
        return cateMapper.list(category);
    }

    @Override
    public Category getById(Long categoryId) {
        Category category =  cateMapper.getById(categoryId);
        return category;
    }
}
