package com.sky.mapper;

import com.sky.entity.SetmealDish;

import java.util.List;

public interface SetmealDishMapper {
    /**
     * 批量保存套餐和菜品的关联关系
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);
}
