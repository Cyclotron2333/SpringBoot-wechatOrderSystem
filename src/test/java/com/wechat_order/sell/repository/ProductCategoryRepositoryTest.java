package com.wechat_order.sell.repository;

import com.wechat_order.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest(){
        ProductCategory productCategory = repository.findById(1).get();
        System.out.println(productCategory.toString());
    }

    @Test
    @Transactional  //在测试中，这个事务代表完全回滚——使测试结果不留存在数据库中
    public void saveTest(){

        /*ProductCategory productCategory = repository.findById(2).get();
        productCategory.setCategoryType(10);*/

        ProductCategory productCategory = new ProductCategory("皮皮最爱",5);
//        repository.save(productCategory);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
//        Assert.assertNotEquals(null,result);
    }

    @Test
    public void updateTest(){
 /*       ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(2);   //设置更改项主键
        productCategory.setCategoryName("莱基最爱");
        productCategory.setCategoryType(3);
        repository.save(productCategory);*/
    }

    @Test
    public void findByCategoryTypeIn(){
        List<Integer> list = Arrays.asList(2,3,4);
        List<ProductCategory> result =  repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }


}