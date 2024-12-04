package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.sky.result.Result;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    RedisTemplate redisTemplate;
    @ApiOperation("增加菜品")
    @PostMapping()
    public Result add(@RequestBody DishDTO dishDTO){
        ////    清理缓存数据
        //在用户端 ，菜品是菜品分类下的一个集合一部分，这个分类下菜品有增删改都将影响这个集合
        //对应影响redis的 key - set，所以要删除key
        String key = "dish_"+dishDTO.getCategoryId();
        clenCache(key);
        dishService.add(dishDTO);
        return Result.success();
    }

    @ApiOperation("根据id查询菜品")
    @GetMapping("/{id}")
    public Result<Dish> selectById(@PathVariable Long id){
        Dish dish = dishService.selectById(id);
        return Result.success(dish);
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    @ApiOperation("修改菜品")
    @PutMapping()
    public Result update(@RequestBody DishDTO dishDTO){
        clenCache("dish_");
        dishService.update(dishDTO);
        return Result.success();
    }

    @ApiOperation("删除菜品")
    @DeleteMapping()
    public Result delete(@RequestBody List<Integer> ids){
        clenCache("dish_");
        dishService.delete(ids);
        return Result.success();
    }
    @ApiOperation("菜品分页查询")
    @GetMapping("/page")
    public Result<PageResult> selectByPage(DishPageQueryDTO dishPageQueryDTO){
        PageResult pageResult = dishService.selectByPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    private void clenCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

    /**
     * 菜品起售停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售停售")
    public Result<String> startOrStop(@PathVariable Integer status, Long id){
        dishService.startOrStop(status,id);
        return Result.success();
    }
}
