package com.example.reggie.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分类
 */
@Data
public class Category {
    private static final long serialVersionUID = 1L;
    private Long id;
    //类型 1 菜品分类 2 套餐分类
    private Integer type;
    //分类名称s
    private String name;
    private Integer sort;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    //创建人
    private Long createUser;
    //修改人
    private Long updateUser;
    //是否删除
    private Integer isDeleted;
}
