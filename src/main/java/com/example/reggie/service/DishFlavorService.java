package com.example.reggie.service;

import com.example.reggie.dto.DishDto;
import com.example.reggie.pojo.DishFlavor;

import java.util.List;

/**
 * @author lay
 * @version 1.0
 */
public interface DishFlavorService {
    void saveFlavor(List<DishFlavor> dishFlavors);

    void updateWithFlavor(DishDto dishDto);
}
