package com.wechat_order.sell.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.wechat_order.sell.dto.OrderDTO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 支付
 */

public interface PayService {

    PayResponse create(OrderDTO orderDTO );

    PayResponse notify(String notifyData);

    RefundResponse refund(OrderDTO orderDTO );
}
