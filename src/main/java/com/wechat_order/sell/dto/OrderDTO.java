package com.wechat_order.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wechat_order.sell.dataobject.OrderDetail;
import com.wechat_order.sell.enums.OrderStatusEnum;
import com.wechat_order.sell.enums.PayStatusEnum;
import com.wechat_order.sell.utils.EnumUtil;
import com.wechat_order.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
//JsonInclude在将 java pojo 对象序列化成为 json 字符串时，使用 @JsonInclude 注解可以控制在哪些情况下才将被注解的属性转换成 json，例如只有属性不为null 时。
public class OrderDTO {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount; //订单总金额

    private Integer orderStatus;    //订单状态，默认新下单

    private Integer payStatus;    //支付状态，默认等待支付

    //新建一个Date2LongSerializer类，继承了JsonSerializer，
    //复写了serialize方法，使用jsonGenerator.writeNumber()转换long型date数据
    //将数据库中的时间转换为时间戳
    //使用@JsonSerialize(using = Date2LongSerializer.class)进行序列化
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    //若字段为必须，则设置初始值以免为null
    //private List<OrderDetail> orderDetailList = new ArrayList<>();
    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    //对象转成Json返回的时候忽略此方法
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
