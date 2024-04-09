package com.example.reggie.mapper;

import com.example.reggie.dto.DishDto;
import com.example.reggie.pojo.Dish;
import com.example.reggie.pojo.DishFlavor;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lay
 * @version 1.0
 */
@Mapper
public interface DishMapper {
    @Insert("insert into dish(id, name, price,category_id,code, image, description, create_time, update_time, create_user, update_user) values (#{id},#{name},#{price},#{categoryId},#{code},#{image},#{description},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void saveDish(DishDto dishDto);

    @Select("select count(*) from dish")
    Long count();

    @Select("select * from dish limit #{start},#{pageSize}")
    List<Dish> pageNull(int start, int pageSize);

    @Select("select * from dish where name like #{name} limit #{start},#{pageSize}")
    List<Dish> page(int start, int pageSize, String name);

    @Select("select * from dish where id=#{id}")
    Dish getById(Long id);

    @Select("select * from dish_flavor where dish_id=#{id}")
    List<DishFlavor> listFlavor(Long id);

    @Update("update dish set name=#{name}, price=#{price}, category_id=#{categoryId}, image=#{image}, description=#{description}, update_time=#{updateTime}, update_user=#{updateUser} where id=#{id}")
    void update(DishDto dishDto);

    @Update("update dish set status=#{status}, update_time=#{updateTime}, update_user=#{updateUser} where id=#{id}")
    void changeStatus(long id, int status, LocalDateTime updateTime, Long updateUser);

    @Select("select count(*) from setmeal_dish where id=#{id}")
    Long countById(long id);

    @Delete("delete from dish where id=#{id}")
    void deleteById(long id);

    @Select("select * from dish where category_id=#{categoryId} and status=1 order by sort desc ,update_time desc")
    List<Dish> getByCateId(Long categoryId);
}
