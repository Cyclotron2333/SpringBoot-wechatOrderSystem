package com.wechat_order.sell.repository;

import com.wechat_order.sell.dataobject.SellerInfo;
import com.wechat_order.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Test
    public void findByOpenid() throws  Exception{
        SellerInfo result = sellerInfoRepository.findByOpenid("test1");
        Assert.assertEquals("test1",result.getOpenid());
    }

    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo() ;
        sellerInfo.setOpenid("test1");
        sellerInfo.setUsername("admin");
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setPassword("admin");

        SellerInfo result = sellerInfoRepository.save(sellerInfo);
        Assert.assertNotNull(result);

    }
}