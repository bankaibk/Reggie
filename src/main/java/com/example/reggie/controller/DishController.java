package com.example.reggie.controller;

import com.example.reggie.common.SnowflakeIdWorker;
import com.example.reggie.dto.DishDto;
import com.example.reggie.pojo.*;
import com.example.reggie.service.CateService;
import com.example.reggie.service.DishFlavorService;
import com.example.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lay
 * @version 1.0
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Resource
    private DishService dishService;
    @Resource
    private DishFlavorService dishFlavorService;
    @Resource
    private CateService cateService;

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishDto.setCreateTime(LocalDateTime.now());
        dishDto.setUpdateTime(LocalDateTime.now());
        dishDto.setId(SnowflakeIdWorker.generator());
        dishDto.setCreateUser((long) request.getSession().getAttribute("employee"));
        dishDto.setUpdateUser((long) request.getSession().getAttribute("employee"));
        dishService.saveDish(dishDto);
        Long dishId = dishDto.getId();
        dishDto.setId(SnowflakeIdWorker.generator());
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            item.setId(SnowflakeIdWorker.generator());
            item.setCreateUser((long) request.getSession().getAttribute("employee"));
            item.setUpdateUser((long) request.getSession().getAttribute("employee"));
            item.setCreateTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return item;
        }).collect(Collectors.toList());
        log.info(flavors.toString());
        dishFlavorService.saveFlavor(flavors);
        return R.success("新增菜品成功");
    }

    @GetMapping("/page")
    public R<PageBean<DishDto>> page(int page, int pageSize, String name) {
        PageBean<Dish> pageInfo = dishService.page(page, pageSize, name);
        PageBean<DishDto> dishDtoPageBean = new PageBean<>();
        BeanUtils.copyProperties(pageInfo, dishDtoPageBean, "records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            Long categoryId = item.getCategoryId();
            Category category = cateService.getById(categoryId);
            String categoryName = category.getName();
            DishDto dishDto = new DishDto();
            dishDto.setCategoryName(categoryName);
            BeanUtils.copyProperties(item, dishDto);
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPageBean.setRecords(list);
        return R.success(dishDtoPageBean);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishDto.setUpdateTime(LocalDateTime.now());
        dishDto.setUpdateUser((long) request.getSession().getAttribute("employee"));
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            item.setId(SnowflakeIdWorker.generator());
            item.setCreateUser((long) request.getSession().getAttribute("employee"));
            item.setUpdateUser((long) request.getSession().getAttribute("employee"));
            item.setCreateTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return item;
        }).collect(Collectors.toList());
        log.info(flavors.toString());
        dishDto.setFlavors(flavors);
        dishFlavorService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

    @PostMapping("/status/{status}")
    public R<String> changeStatus(HttpServletRequest request, @PathVariable int status, @RequestParam List<Long> ids) {
        LocalDateTime updateTime = LocalDateTime.now();
        Long updateUser = (long) request.getSession().getAttribute("employee");
        dishService.changeStatus(ids, status, updateTime, updateUser);
        return R.success("状态修改完毕");
    }

    @DeleteMapping("")
    public R<String> deleteById(@RequestParam List<Long> ids) {
        dishService.deleteById(ids);
        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish){
        List<Dish> list = dishService.getByCateId(dish.getCategoryId());
        return R.success(list);
    }
}
