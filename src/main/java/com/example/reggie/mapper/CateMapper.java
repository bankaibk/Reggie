package com.example.reggie.mapper;

import com.example.reggie.pojo.Category;
import com.example.reggie.pojo.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author lay
 * @version 1.0
 */
@Mapper
public interface CateMapper {
    @Insert("insert into category(id,name,sort,type,create_time, update_time,create_user,update_user) values (#{id},#{name},#{sort},#{type},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void save(Category category);

    @Select("select * from category order by sort asc limit #{start},#{pageSize}")
    List<Category> page(Integer start, Integer pageSize, String name);

    @Select("select count(*) from category")
    Long count();

    @Delete("delete from category where id=#{id}")
    void delete(Long id);

    @Select("select count(*) from dish where category_id = #{id}")
    int countDishById(Long id);

    @Select("select count(*) from setmeal where category_id = #{id}")
    int countSetmealById(Long id);

    @Update("update category set name = #{name},sort = #{sort},update_time = #{updateTime},update_user = #{updateUser} where id = #{id}")
    void update(Category category);

    @Select("select * from category where type = #{type} order by sort desc")
    List<Category> list(Category category);

    @Select("select * from category where id = #{id}")
    Category getById(Long id);
}
