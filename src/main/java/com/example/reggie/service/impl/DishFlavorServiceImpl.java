package com.example.reggie.service.impl;

import com.example.reggie.dto.DishDto;
import com.example.reggie.mapper.DishFlavorMapper;
import com.example.reggie.mapper.DishMapper;
import com.example.reggie.pojo.DishFlavor;
import com.example.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lay
 * @version 1.0
 */
@Service
public class DishFlavorServiceImpl implements DishFlavorService {
    @Resource
    private DishFlavorMapper dishFlavorMapper;
    @Resource
    private DishMapper dishMapper;

    @Override
    @Transactional
    public void saveFlavor(List<DishFlavor> dishFlavors) {
        dishFlavorMapper.saveFlavor(dishFlavors);
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        dishMapper.update(dishDto);
        dishFlavorMapper.remove(dishDto);
        dishFlavorMapper.saveFlavor(dishDto.getFlavors());
    }
}
