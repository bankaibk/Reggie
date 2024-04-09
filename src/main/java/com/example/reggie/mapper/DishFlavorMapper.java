package com.example.reggie.mapper;

import com.example.reggie.dto.DishDto;
import com.example.reggie.pojo.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lay
 * @version 1.0
 */
@Mapper
public interface DishFlavorMapper {
    void saveFlavor(List<DishFlavor> dishFlavors);

    @Delete("delete from dish_flavor where dish_id=#{dishId}")
    void remove(DishDto dishDto);

    @Delete("delete from dish_flavor where dish_id=#{id}")
    void deleteById(long id);
}
