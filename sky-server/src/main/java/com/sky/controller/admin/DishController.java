package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.service.DishService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sky.result.Result;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @ApiOperation("增加菜品")
    @PostMapping()
    public Result add(@RequestBody DishDTO dishDTO){
        dishService.add(dishDTO);
        return Result.success();
    }

//    @ApiOperation("根据id查询菜品")
//    @GetMapping("/{id}")
//    public Result<Dish> selectById(@PathVariable Long id){
//        Dish dish = dishService.selectById(id);
//        return Result.success(dish);
//    }
//
//    @ApiOperation("根据分类id查询菜品")
//    @GetMapping("/list")
//    public Result<List<Dish>> selectByCategoryId(@RequestParam String categoryId){
//       List <Dish> dishs = dishService.selectByCategoryId(categoryId);
//       return Result.success(dishs);
//    }
}
