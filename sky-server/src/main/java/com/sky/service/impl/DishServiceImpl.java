package com.sky.service.impl;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Override
    public void add(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        List<DishFlavor> dishFlavor = dishDTO.getFlavors();
//        dishMapper.addDish(dish);
//        dishMapper.addDishFlavor(dishFlavor);

    }
}
