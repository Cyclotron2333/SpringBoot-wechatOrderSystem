package com.wechat_order.sell.service.impl;

import com.wechat_order.sell.dto.OrderDTO;
import com.wechat_order.sell.service.OrderService;
import com.wechat_order.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService ;

    @Autowired
    private OrderService orderService ;

    @Test
    public void create() {
        OrderDTO orderDTO = orderService.findOne("1562744959917309910") ;
        payService.create(orderDTO);
    }

    @Test
    public void refund(){
        OrderDTO orderDTO = orderService.findOne("1562744959917309910");
        payService.refund(orderDTO);
    }
}