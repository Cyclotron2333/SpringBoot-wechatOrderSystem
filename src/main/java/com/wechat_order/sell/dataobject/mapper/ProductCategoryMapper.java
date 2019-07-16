package com.wechat_order.sell.dataobject.mapper;

import com.wechat_order.sell.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 使用Mybatis对数据库进行增删改查
 */

public interface ProductCategoryMapper {

    //字段与数据库相对应
    @Insert("insert into product_category(category_name,category_type) values(#{category_name,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER})")
    int insertByMap(Map<String,Object> map);

    //根据对象查询，查询字段与类相对应
    @Insert("insert into product_category(category_name,category_type) values(#{categoryName,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER})")
    int insertByObject(ProductCategory productCategory );

    @Select("select * from product_category where category_type = #{categoryType}")
    //每个result其实是一个字段，是要返回的对应的内容
    //设置了才会查出来
    @Results({
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName"),
            @Result(column = "category_type",property = "categoryType")
            //将category_type（数据库中的字段）映射到categoryType（变量）
    })
    //category_type唯一，只可能查出一条
    ProductCategory findByCategoryType(Integer categoryType);

    @Select("select * from product_category where category_name = #{categoryName}")
    //每个result其实是一个字段，是要返回的对应的内容
    //设置了才会查出来
    @Results({
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName"),
            @Result(column = "category_type",property = "categoryType")
            //将category_type（数据库中的字段）映射到类中的categoryType（变量）
    })
    //有可能返回多条
    List<ProductCategory> findByCategoryName(String categoryName);

    //根据某一个字段更新
    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    //方法中传多个参数的时候要用@Param，对应类中属性
    int updateCategoryType(@Param("categoryName") String category_name
            ,@Param("categoryType")Integer categoryType);

    //根据对象更新
    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    //方法中传多个参数的时候要用@Param，对应类中属性
    int updateByObject(ProductCategory productCategory);

    @Delete("delete from product_category where category_type = #{categoryType}")
    int deleteByCategoryType(Integer categoryType);

    //使用xml进行查询
    //进行方法声明
    //在resource下的mapper中写其余内容
    //文件名和这个（this）类的名字相对应
    ProductCategory selectByCategoryType(Integer categoryType);
}
