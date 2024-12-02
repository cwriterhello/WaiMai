package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into sky_take_out.employee(name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user) VALUES (#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void add(Employee employee);

    List<Employee> selectByPage(EmployeePageQueryDTO epq);

    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    @Select("select *from employee where id = #{id}")
    Employee getById(Long id);
}
