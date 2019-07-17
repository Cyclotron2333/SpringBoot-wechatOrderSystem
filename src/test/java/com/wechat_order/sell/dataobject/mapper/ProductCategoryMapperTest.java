package com.wechat_order.sell.dataobject.mapper;

import com.wechat_order.sell.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//websocket是需要依赖tomcat等容器的启动。所以在测试过程中我们要真正的启动一个tomcat作为容器。
@Slf4j
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
//    @Transactional
    public void insertByMap() throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("category_name","肚子最大");
        map.put("category_type",20);
        int result = mapper.insertByMap(map);
        Assert.assertEquals(1,result);
    }

    @Test
    public void insertByObject() throws Exception{
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("脑袋最大");
        productCategory.setCategoryType(19);
        int result = mapper.insertByObject(productCategory);
        Assert.assertEquals(1,result);
    }

    @Test
    public void findByCategoryType(){
        ProductCategory category = mapper.findByCategoryType(19);
        Assert.assertNotNull(category);
    }

    @Test
    public void findByCategoryName(){
        List<ProductCategory> categoryList = mapper.findByCategoryName("屁股最大");
        Assert.assertNotEquals(0,categoryList.size());
    }

    @Test
    public void updateCategoryType(){
        int result = mapper.updateCategoryType("毛最光滑", 10);
        Assert.assertEquals(1,result);
    }

    @Test
    public void updateByObject(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("脑袋最圆");
        productCategory.setCategoryType(19);
        int result = mapper.updateByObject(productCategory);
        Assert.assertEquals(1,result);
    }

    @Test
    public void deleteByCategoryType(){
        int result = mapper.deleteByCategoryType(10);
        Assert.assertEquals(1,result);
    }

    @Test
    public void selectByCategoryType()throws Exception{
        ProductCategory productCategory = mapper.selectByCategoryType(2);
        Assert.assertNotNull(productCategory);
    }

}