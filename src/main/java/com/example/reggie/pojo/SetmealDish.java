package com.example.reggie.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 套餐菜品关系
 */
@Data
public class SetmealDish implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    //套餐id
    private Long setmealId;
    //菜品id
    private Long dishId;
    //菜品名称 （冗余字段）
    private String name;
    //菜品原价
    private BigDecimal price;
    //份数
    private Integer copies;
    //排序
    private Integer sort;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
    //是否删除
    private Integer isDeleted;
}
