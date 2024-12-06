package com.sky.controller.user;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user/order")
@Api(tags = "订单接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //订单提交
    @ApiOperation("订单提交")
    @PostMapping("/submit")
    public Result<OrderSubmitVO> add(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        log.info("订单提交");
        OrderSubmitVO orderSubmitVO = orderService.add(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }
}
