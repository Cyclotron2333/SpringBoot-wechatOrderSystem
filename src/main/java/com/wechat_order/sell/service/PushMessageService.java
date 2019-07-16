package com.wechat_order.sell.service;

import com.wechat_order.sell.dto.OrderDTO;

/**
 * 推送消息
 */

public interface PushMessageService {

    /**
     * 订单状态消息推送
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO );

}
