package com.wechat_order.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品VO（包含类目），装在ResultVO里
 */
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 563204009950737899L;

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoVoList;
}
