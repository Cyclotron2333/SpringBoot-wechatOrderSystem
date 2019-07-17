package com.wechat_order.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class OrderDetail {

    @Id
    private String detailId;

    private String orderId;     //订单Id

    private String productId;   //商品Id

    private String productName;

    private BigDecimal productPrice;    //单价

    private Integer productQuantity;    //商品数量

    private String productIcon;  //商品小图
}

