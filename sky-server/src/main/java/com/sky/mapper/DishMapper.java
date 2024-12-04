package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into dish(name, category_id, price, image, description, status, create_time, update_time, create_user, update_user) " +
            "value(#{name},#{categoryId},#{price},#{image},#{description},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser}) ")
    void add(Dish dish);

    @Select("select *from dish where id = #{id}")
    Dish selectById(Long id);

    @Select("select *from dish where category_id = #{categoryId}")
    List<Dish> selectByCategoryId(String categoryId);

    void updateDish(Dish dish);

    @Update("update dish_flavor set dish_id = #{dishId} ,name=#{name},value = #{value} where id = #{id}")
    void updateDishFlavors(List<DishFlavor> dishFlavors);

    void delete(List<Integer> ids);

    /**
     * 动态条件查询菜品
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);

    /**
     * 根据套餐id查询菜品
     * @param setmealId
     * @return
     */
    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);

    List<Dish> selectByPage(DishPageQueryDTO dishPageQueryDTO);

    @Update("update dish set name = #{name},category_id= #{categoryId},price= #{price},image=#{image},description=#{description},update_time=#{updateTime},update_user=#{updateUser}")
    void update(Dish dish);
}
