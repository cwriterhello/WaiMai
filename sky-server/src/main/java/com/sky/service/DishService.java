package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void add(DishDTO dishDTO);

    Dish selectById(Long id);

    List<Dish> selectByCategoryId(Long categoryId);

    void update(DishDTO dishDTO);

    void delete(List<Integer> ids);

    List<DishVO> listWithFlavor(Dish dish);
}
