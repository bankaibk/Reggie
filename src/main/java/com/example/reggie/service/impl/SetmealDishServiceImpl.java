package com.example.reggie.service.impl;

import com.example.reggie.mapper.SetmealDishMapper;
import com.example.reggie.service.SetmealDishService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lay
 * @version 1.0
 */
@Service
public class SetmealDishServiceImpl implements SetmealDishService {
    @Resource
    private SetmealDishMapper setmealDishMapper;
}
