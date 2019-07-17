package com.wechat_order.sell.service;

import com.wechat_order.sell.dataobject.ProductCategory;

import java.util.List;

/**
 * 类目
 */

public interface CategoryService {

    ProductCategory findOne(Integer categoryID);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
