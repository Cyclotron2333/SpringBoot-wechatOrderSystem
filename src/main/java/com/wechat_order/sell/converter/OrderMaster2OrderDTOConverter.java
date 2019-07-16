package com.wechat_order.sell.converter;

import com.wechat_order.sell.dataobject.OrderMaster;
import com.wechat_order.sell.dto.OrderDTO;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster){

        OrderDTO orderDTO = new OrderDTO() ;

        BeanUtils.copyProperties(orderMaster,orderDTO);

        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasters ){
        return orderMasters.stream()
                .map(e ->convert(e))
                .collect(Collectors.toList());
    }
}
