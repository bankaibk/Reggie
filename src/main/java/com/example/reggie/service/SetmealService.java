package com.example.reggie.service;

import com.example.reggie.dto.SetmealDto;
import com.example.reggie.pojo.PageBean;
import com.example.reggie.pojo.Setmeal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lay
 * @version 1.0
 */
@Service
public interface SetmealService {
    void saveWithDish(SetmealDto setmealDto);

    PageBean<Setmeal> page(int page, int pageSize, String name);

    void deleteById(List<Long> ids);

    int countById(List<Long> ids);

    void changeStatus(List<Long> ids, int status, LocalDateTime updateTime, Long updateUser);

    SetmealDto getByIdWithCate(Long id);

    void updateWithDish(SetmealDto setmealDto);
}
