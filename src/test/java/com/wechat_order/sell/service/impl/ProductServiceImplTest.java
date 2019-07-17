package com.wechat_order.sell.service.impl;

import com.wechat_order.sell.dataobject.ProductInfo;
import com.wechat_order.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() throws Exception{

        ProductInfo productInfo = productService.findOne("001");
        Assert.assertEquals("001",productInfo.getProductId());
    }

    @Test
    public void findUpAll() throws Exception{
        List<ProductInfo> productInfos = productService.findUpAll();
        Assert.assertNotEquals(0,productInfos.size());
    }

    @Test
    public void findAll() throws Exception{
        Pageable request = PageRequest.of(0,2);
        Page<ProductInfo> productInfoPage = productService.findAll(request);;
//        System.out.println(productInfoPage.getTotalElements());
        Assert.assertNotEquals(0,productInfoPage.getTotalElements());
    }

    @Test
    public void save() throws Exception{
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("002");
        productInfo.setProductName("黄泥土");
        productInfo.setProductPrice(new BigDecimal(2.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("好吃的土");
        productInfo.setProductIcon("http://yyyy.jpg");
        productInfo.setProductStatus(ProductStatusEnum.Down.getCode());
        productInfo.setCategoryType(5);

        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void onSale() throws Exception{
        ProductInfo result = productService.onSale("005");
        Assert.assertEquals(ProductStatusEnum.Up,result.getProductStatusEnum());
    }

    @Test
    public void offSale() throws Exception{
        ProductInfo result = productService.offSale("001");
        Assert.assertEquals(ProductStatusEnum.Down,result.getProductStatusEnum());
    }
}