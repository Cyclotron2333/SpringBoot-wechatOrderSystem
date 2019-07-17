package com.wechat_order.sell.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 专门处理前端Form提交的字段
 */
@Data
public class ProductForm {

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer categoryType;   //类目编号

}
