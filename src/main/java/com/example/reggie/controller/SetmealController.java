package com.example.reggie.controller;

import com.example.reggie.common.SnowflakeIdWorker;
import com.example.reggie.dto.DishDto;
import com.example.reggie.dto.SetmealDto;
import com.example.reggie.pojo.*;
import com.example.reggie.service.CateService;
import com.example.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Resource
    private SetmealService setmealService;
    @Resource
    private CateService cateService;

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody SetmealDto setmealDto) {
        log.info(setmealDto.toString());
        setmealDto.setCreateTime(LocalDateTime.now());
        setmealDto.setUpdateTime(LocalDateTime.now());
        setmealDto.setCreateUser((long) request.getSession().getAttribute("employee"));
        setmealDto.setUpdateUser((long) request.getSession().getAttribute("employee"));
        setmealDto.setId(SnowflakeIdWorker.generator());
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    @GetMapping("/page")
    public R<PageBean<SetmealDto>> page(int page, int pageSize, String name) {
        PageBean<Setmeal> pageInfo = setmealService.page(page, pageSize, name);
        PageBean<SetmealDto> setmealDtoPageBean = new PageBean<>();
        BeanUtils.copyProperties(pageInfo, setmealDtoPageBean, "records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item) -> {
            Long categoryId = item.getCategoryId();
            Category category = cateService.getById(categoryId);
            String categoryName = category.getName();
            SetmealDto setmealDto = new SetmealDto();
            setmealDto.setCategoryName(categoryName);
            BeanUtils.copyProperties(item, setmealDto);
            return setmealDto;
        }).collect(Collectors.toList());
        setmealDtoPageBean.setRecords(list);
        return R.success(setmealDtoPageBean);
    }

    @DeleteMapping("")
    public R<String> deleteById(@RequestParam List<Long> ids) {
        int count = setmealService.countById(ids);
        if (count > 0)
            return R.error("起售状态无法删除");
        setmealService.deleteById(ids);
        return R.success("删除成功");
    }

    @PostMapping("/status/{status}")
    public R<String> changeStatus(HttpServletRequest request, @PathVariable int status, @RequestParam List<Long> ids) {
        LocalDateTime updateTime = LocalDateTime.now();
        Long updateUser = (long) request.getSession().getAttribute("employee");
        setmealService.changeStatus(ids, status, updateTime, updateUser);
        return R.success("状态修改完毕");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id) {
        SetmealDto setmealDto = setmealService.getByIdWithCate(id);
        log.info(setmealDto.toString());
        return R.success(setmealDto);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody SetmealDto setmealDto) {
        setmealDto.setUpdateUser((long)request.getSession().getAttribute("employee"));
        setmealDto.setUpdateTime(LocalDateTime.now());
        setmealService.updateWithDish(setmealDto);
        return R.success("修改菜品成功");
    }
}