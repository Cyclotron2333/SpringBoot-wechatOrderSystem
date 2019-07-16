package com.wechat_order.sell.service.impl;

import com.wechat_order.sell.dataobject.OrderDetail;
import com.wechat_order.sell.dto.OrderDTO;
import com.wechat_order.sell.enums.OrderStatusEnum;
import com.wechat_order.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j  //使用日志来打印
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "095627";

    private final String ORDER_ID = "1562638100512968250";

    @Test
    public void create() {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("小蠢驴");
        orderDTO.setBuyerAddress("柯基大学");
        orderDTO.setBuyerPhone("13012021746");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetails = new ArrayList<>();

        OrderDetail o1 = new OrderDetail();
        o1.setProductId("001");
        o1.setProductQuantity(2);
        orderDetails.add(o1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("003");
        o2.setProductQuantity(8);
        orderDetails.add(o2);

        orderDTO.setOrderDetailList(orderDetails);
        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】result={}",result);

        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() throws Exception{
        OrderDTO result = orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】result={}",result);
        Assert.assertEquals(ORDER_ID,result.getOrderId());
    }

    @Test
    public void findList() throws Exception{
        PageRequest request = PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void list() throws Exception{
        PageRequest request = PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
//        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
        //另一种assert写法
        Assert.assertTrue("查询所有订单列表",orderDTOPage.getTotalElements()>0);
    }

    @Test
    public void cancel() throws Exception{
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),orderDTO.getOrderStatus());
    }

    @Test
    public void finish() throws Exception{
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() throws Exception{
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }
}