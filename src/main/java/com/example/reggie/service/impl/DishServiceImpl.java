package com.example.reggie.service.impl;

import com.example.reggie.dto.DishDto;
import com.example.reggie.mapper.DishFlavorMapper;
import com.example.reggie.mapper.DishMapper;
import com.example.reggie.pojo.Dish;
import com.example.reggie.pojo.DishFlavor;
import com.example.reggie.pojo.PageBean;
import com.example.reggie.service.DishService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lay
 * @version 1.0
 */
@Service
public class DishServiceImpl implements DishService {
    @Resource
    private DishMapper dishMapper;
    @Resource
    private DishFlavorMapper dishFlavorMapper;
    @Override
    public void saveDish(DishDto dishDto) {
        dishMapper.saveDish(dishDto);
    }

    @Override
    public PageBean<Dish> page(int page, int pageSize, String name) {
        Long total = dishMapper.count();
        List<Dish> records;
        if (StringUtils.isEmpty(name)) {
            records = dishMapper.pageNull((page - 1) * pageSize, pageSize);
        } else {
            records = dishMapper.page((page - 1) * pageSize, pageSize, name);
        }
        PageBean<Dish> pageBean = new PageBean<>(total, records);
        return pageBean;
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = dishMapper.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        List<DishFlavor> flavors = dishMapper.listFlavor(id);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    public void changeStatus(List<Long> ids, int status, LocalDateTime updateTime, Long updateUser) {
        for (long id : ids) {
            dishMapper.changeStatus(id, status,updateTime,updateUser);
        }
    }

    @Override
    public void deleteById(List<Long> ids) {
        for (long id : ids) {
             long count = dishMapper.countById(id);
             if(count==0){//套餐没有关联当前菜品
                 dishMapper.deleteById(id);
                 dishFlavorMapper.deleteById(id);
             }
        }
    }

    @Override
    public List<Dish> getByCateId(Long categoryId) {
        return dishMapper.getByCateId(categoryId);
    }
}
