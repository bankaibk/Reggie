package com.example.reggie.service.impl;

import com.example.reggie.common.SnowflakeIdWorker;
import com.example.reggie.dto.DishDto;
import com.example.reggie.dto.SetmealDto;
import com.example.reggie.mapper.CateMapper;
import com.example.reggie.mapper.SetmealDishMapper;
import com.example.reggie.mapper.SetmealMapper;
import com.example.reggie.pojo.*;
import com.example.reggie.service.SetmealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lay
 * @version 1.0
 */
@Service
public class SetmealServiceImpl implements SetmealService {
    @Resource
    private SetmealMapper setmealMapper;
    @Resource
    private SetmealDishMapper setmealDishMapper;
    @Resource
    private CateMapper cateMapper;
    @Override
    @Transactional//事务注解
    public void saveWithDish(SetmealDto setmealDto) {
        setmealMapper.save(setmealDto);
        List<SetmealDish> list = setmealDto.getSetmealDishes();
        list = list.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            item.setId(SnowflakeIdWorker.generator());
            item.setCreateTime(setmealDto.getCreateTime());
            item.setUpdateTime(setmealDto.getUpdateTime());
            item.setCreateUser(setmealDto.getCreateUser());
            item.setUpdateUser(setmealDto.getUpdateUser());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return item;
        }).collect(Collectors.toList());
        setmealDishMapper.save(list);
    }

    @Override
    public PageBean<Setmeal> page(int page, int pageSize, String name) {
        Long total = setmealMapper.count();
        List<Setmeal> records;
        if (StringUtils.isEmpty(name)) {
            records = setmealMapper.pageNull((page - 1) * pageSize, pageSize);
        } else {
            records = setmealMapper.page((page - 1) * pageSize, pageSize, name);
        }
        PageBean<Setmeal> pageBean = new PageBean<>(total, records);
        return pageBean;
    }

    @Override
    public void deleteById(List<Long> ids) {
        for (long id : ids) {
            setmealMapper.deleteById(id);
            setmealDishMapper.deleteById(id);
        }
    }

    @Override
    public int countById(List<Long> ids) {
        int count = 0;
        for (long id : ids) {
            count += setmealMapper.countById(id);
        }
        return count;
    }

    @Override
    public void changeStatus(List<Long> ids, int status, LocalDateTime updateTime, Long updateUser) {
        for (long id : ids) {
            setmealMapper.changeStatus(id, status,updateTime,updateUser);
        }
    }

    @Override
    public SetmealDto getByIdWithCate(Long id) {
        Setmeal setmeal = setmealMapper.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);
        Category category = cateMapper.getById(setmeal.getCategoryId());
        setmealDto.setCategoryName(category.getName());
        List<SetmealDish> setmealDishes =setmealDishMapper.listDish(id);
        setmealDto.setSetmealDishes(setmealDishes);
        return setmealDto;
    }

    @Override
    public void updateWithDish(SetmealDto setmealDto) {
        setmealMapper.update(setmealDto);
        List<SetmealDish> list = setmealDto.getSetmealDishes();
        list = list.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            item.setId(SnowflakeIdWorker.generator());
            item.setCreateTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            item.setCreateUser(setmealDto.getCreateUser());
            item.setUpdateUser(setmealDto.getUpdateUser());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return item;
        }).collect(Collectors.toList());
        setmealDto.setSetmealDishes(list);
        setmealDishMapper.remove(setmealDto);
        setmealDishMapper.save(setmealDto.getSetmealDishes());
    }
}
