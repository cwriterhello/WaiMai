package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Transactional
    @Override
    public OrderSubmitVO add(OrdersSubmitDTO ordersSubmitDTO) {

        //处理各种异常（地址铺异常和购物车异常）???? 其实在前端已经校验过了（没有地址或购物车数据是不能提交订单的），但为了程序的健壮性仍要在后端检验异常信息（例如使用postman直接请求）
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        if (addressBook == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();
        List<ShoppingCart> list = shoppingCartMapper.select(shoppingCart);
        if (list == null || list.isEmpty()) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        log.info("通过异常处理");
        Long number = System.currentTimeMillis();
        //向订单中插入一条数据
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        orders.setUserId(userId);
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setPayStatus(Orders.UN_PAID);
        orders.setNumber(String.valueOf(number));
        orders.setOrderTime(LocalDateTime.now());
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        orders.setAddress(addressBook.getDetail());
        log.info("插入了一条数据");
        orderMapper.insert(orders);
        //像订单细节中插入多条数据
        //这样循环对数据库进行添加操作很消耗性能
//        OrderDetail orderDetail = OrderDetail.builder()
//                .orderId(number)
//                .build();
//        for (ShoppingCart cart : list){
//            BeanUtils.copyProperties(cart,orderDetail);
//            orderDetailMapper.insert(orderDetail);
//        }
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for(ShoppingCart cart : list){
            OrderDetail orderDetail  = new OrderDetail();
            BeanUtils.copyProperties(cart,orderDetail);
            orderDetail.setOrderId(number);
            orderDetailList.add(orderDetail);
        }
        orderDetailMapper.insertBatch(orderDetailList);

        //清空购物车
        shoppingCartMapper.clean(userId);

        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(userId)
                .orderAmount(orders.getAmount())
                .orderNumber(orders.getNumber())
                .orderTime(orders.getOrderTime())
                .build();
        return orderSubmitVO;
    }
}
