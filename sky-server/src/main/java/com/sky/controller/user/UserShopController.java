package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "店铺状态")
public class UserShopController {
    @Autowired
    RedisTemplate redisTemplate;
    //用户端查询营业状态
    @ApiOperation("user查询店铺营业状态")
    @GetMapping("/shop/status")
    public Result userGetStatus(){
        Integer status = (Integer)redisTemplate.opsForValue().get("SHOP_STATUS");
        return Result.success(status);
    }
}
