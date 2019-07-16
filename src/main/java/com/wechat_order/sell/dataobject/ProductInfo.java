package com.wechat_order.sell.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wechat_order.sell.enums.ProductStatusEnum;
import com.wechat_order.sell.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.function.BiConsumer;

/**
 * 商品
 */

@Entity
@Data
@DynamicUpdate  //自动更新注解
public class ProductInfo implements Serializable {
    //序列化以缓存
    private static final long serialVersionUID = 6936852848020796282L;

    @Id
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer productStatus = ProductStatusEnum.Up.getCode();  //商品状态，0正常1下架,默认0在架

    private Integer categoryType;   //类目编号

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus,ProductStatusEnum.class);
    }
}
