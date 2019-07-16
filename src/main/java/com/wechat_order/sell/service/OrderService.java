package com.wechat_order.sell.service;

import com.wechat_order.sell.dataobject.OrderMaster;
import com.wechat_order.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    //创建订单
    OrderDTO create(OrderDTO orderMaster);

    //查询单个订单
    OrderDTO findOne(String orderId);

    //查询列表订单
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    //查询列表订单
    Page<OrderDTO> findList(Pageable pageable);

    //取消订单
    OrderDTO cancel(OrderDTO orderDTO);

    //完结订单
    OrderDTO finish(OrderDTO orderDTO );

    //支付订单
    OrderDTO paid(OrderDTO orderDTO);
}
