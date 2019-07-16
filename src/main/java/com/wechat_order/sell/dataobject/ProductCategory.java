package com.wechat_order.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * ProductCategory类
 * product_category表
 */

//@Table(name = "product_category")     显式指定对应表
 @Entity    //数据库映射成对象 注解
 @DynamicUpdate     //自动更新 注解
 @Data      //包含了get/set/toString等常用方法
public class ProductCategory {

    //类目ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //设置自增
    private Integer categoryId;     //驼峰法命名
    //类目名称
    private String categoryName;
    //类目编号
    private Integer categoryType;

    //空的构造方法
    //否则错误：org.springframework.orm.jpa.JpaSystemException: No default constructor for entity
    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    private Date updateTime;

    private Date createTime;

    //替代传统的gei/set手动设置——在依赖中设置lombok插件
}
