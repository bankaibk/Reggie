package com.example.reggie.mapper;

import com.example.reggie.pojo.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lay
 * @version 1.0
 */
@Mapper
public interface EmpMapper {
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @Insert("insert into employee(name,username,password,phone,sex,id_number,create_time,update_time,create_user,update_user) values (#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void addEmp(Employee employee);

    @Select("select count(*) from employee")
    Long count();

    @Select("select * from employee where name = #{name} limit #{start},#{pageSize}")
    List<Employee> page(Integer start, Integer pageSize, String name);

    @Select("select * from employee limit #{start},#{pageSize}")
    List<Employee> pageNull(Integer start, Integer pageSize);

//    @Update("update employee set update_time = #{updateTime},update_user = #{updateUser},status = #{status} where id = #{id}")
    void updateById(Employee employee);

    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}
