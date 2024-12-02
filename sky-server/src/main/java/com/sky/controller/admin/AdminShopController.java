package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "店铺状态")
public class AdminShopController {

    @Autowired
    RedisTemplate redisTemplate;

    //设置店铺营业状态
    @ApiOperation("设置店铺营业状态")
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status){
        log.info("设置店铺营业状态为：{}",status==1?"营业":"打烊");
        redisTemplate.opsForValue().set("SHOP_STATUS",status);
        return Result.success();
    }
    //管理端查询营业状态
    @ApiOperation("admin查询店铺营业状态")
    @GetMapping("/status")
    public Result adminGetStatus(){
        Integer status = (Integer)redisTemplate.opsForValue().get("SHOP_STATUS");
        return Result.success(status);
    }


}
