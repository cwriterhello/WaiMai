package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper {

    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into dish(name, category_id, price, image, description, status, create_time, update_time, create_user, update_user) " +
            "value(#{name},#{categoryId},#{price},#{image},#{description},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser}) ")
    void add(Dish dish);

}
