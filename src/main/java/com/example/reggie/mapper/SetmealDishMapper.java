package com.example.reggie.mapper;

import com.example.reggie.dto.SetmealDto;
import com.example.reggie.pojo.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lay
 * @version 1.0
 */
@Mapper
public interface SetmealDishMapper {
    void save(List<SetmealDish> list);

    @Delete("delete from setmeal_dish where setmeal_id=#{id}")
    void deleteById(long id);

    @Select("select * from  setmeal_dish where setmeal_id=#{id}")
    List<SetmealDish> listDish(Long id);

    @Delete("delete from setmeal_dish where setmeal_id=#{id}")
    void remove(SetmealDto setmealDto);
}
