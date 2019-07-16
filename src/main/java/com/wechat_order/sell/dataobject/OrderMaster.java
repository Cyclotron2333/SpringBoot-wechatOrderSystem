package com.wechat_order.sell.dataobject;

import com.wechat_order.sell.enums.OrderStatusEnum;
import com.wechat_order.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount; //订单总金额

    private Integer orderStatus = OrderStatusEnum.NEW.getCode();    //订单状态，默认新下单

    private Integer payStatus = PayStatusEnum.WAIT.getCode();    //支付状态，默认等待支付

    private Date createTime;

    private Date updateTime;

    /*@Transient //让某些被修饰的成员属性变量不被序列化。用transient声明一个实例变量，当对象存储时，它的值不需要维持。
    private List<OrderDetail> orderDetailList; */
}
