package com.wechat_order.sell.dataobject.dao;

import com.wechat_order.sell.dataobject.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 习惯性先写dao进行封装，再在service中调用
 */
public class ProductCategoryDao {

    @Autowired
    private ProductCategoryMapper mapper;

    public int insertByMap(Map<String,Object> map){
        return mapper.insertByMap(map);
    }
}
