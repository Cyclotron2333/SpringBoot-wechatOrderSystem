package com.wechat_order.sell.repository;

import com.wechat_order.sell.dataobject.OrderDetail;
import com.wechat_order.sell.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    List<OrderDetail> findByOrderId(String orderId);
}
