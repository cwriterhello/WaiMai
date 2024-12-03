package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void add(DishDTO dishDTO);

    Dish selectById(Long id);

    List<Dish> selectByCategoryId(Long categoryId);

    void update(DishDTO dishDTO);

    void delete(List<Integer> ids);

    List<DishVO> listWithFlavor(Dish dish);

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);

    PageResult selectByPage(DishPageQueryDTO dishPageQueryDTO);
}
