package com.wechat_order.sell.service.impl;

import com.wechat_order.sell.dto.OrderDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMassageServiceImplTest {

    @Autowired
    private PushMassageServiceImpl pushMassageService;

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void orderStatus() {

        OrderDTO orderDTO = orderService.findOne("1562638100512968250");

        pushMassageService.orderStatus(orderDTO);
    }
}