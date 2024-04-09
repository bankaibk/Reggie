package com.example.reggie.dto;

import com.example.reggie.pojo.Setmeal;
import com.example.reggie.pojo.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {
    private List<SetmealDish> setmealDishes;
    private String categoryName;
}
