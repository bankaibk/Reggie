package com.example.reggie.service;

import com.example.reggie.dto.DishDto;
import com.example.reggie.pojo.Dish;
import com.example.reggie.pojo.PageBean;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lay
 * @version 1.0
 */
public interface DishService {
    void saveDish(DishDto dishDto);

    PageBean<Dish> page(int page, int pageSize, String name);

    DishDto getByIdWithFlavor(Long id);

    void changeStatus(List<Long> ids, int status, LocalDateTime updateTime,Long updateUser);

    void deleteById(List<Long> ids);

    List<Dish> getByCateId(Long categoryId);
}
