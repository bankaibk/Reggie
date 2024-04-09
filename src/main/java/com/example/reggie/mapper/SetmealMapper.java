package com.example.reggie.mapper;

import com.example.reggie.dto.SetmealDto;
import com.example.reggie.pojo.Setmeal;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lay
 * @version 1.0
 */
@Mapper
public interface SetmealMapper {
    @Insert("insert into setmeal(id, category_id, name, price, status, code, description, image, create_time, update_time, create_user, update_user) VALUES (#{id}, #{categoryId}, #{name}, #{price}, #{status}, #{code}, #{description}, #{image}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void save(SetmealDto setmealDto);

    @Select("select count(*) from setmeal")
    Long count();

    @Select("select * from setmeal limit #{start},#{pageSize}")
    List<Setmeal> pageNull(int start, int pageSize);

    @Select("select * from setmeal where name like #{name} limit #{start},#{pageSize}")
    List<Setmeal> page(int start, int pageSize, String name);

    @Delete("delete from setmeal where id=#{id}")
    void deleteById(long id);

    @Select("select count(*) from setmeal where id=#{id} and status=1")
    int countById(long id);

    @Update("update setmeal set status=#{status}, update_time=#{updateTime}, update_user=#{updateUser} where id=#{id}")
    void changeStatus(long id, int status, LocalDateTime updateTime, Long updateUser);

    @Select("select * from setmeal where id=#{id}")
    Setmeal getById(Long id);
    @Update("update setmeal set category_id=#{categoryId}, name=#{name}, price=#{price},description=#{description}, image=#{image}, update_time=#{updateTime}, update_user=#{updateUser} where id=#{id}")
    void update(SetmealDto setmealDto);
}
