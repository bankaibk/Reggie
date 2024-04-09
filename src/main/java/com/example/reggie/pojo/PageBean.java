package com.example.reggie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lay
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean<T> {
    private Long total;
    private List<T> records;
}
