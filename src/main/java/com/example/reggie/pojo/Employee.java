package com.example.reggie.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Employee {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String name;
    private String password;
    private String phone;
    private String sex;
    private String idNumber;//下划线命名法改为驼峰命名，已经在配置文件中开启了驼峰命名
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
}
