package com.wechat_order.sell.utils;

import com.wechat_order.sell.enums.CodeEnum;

public class EnumUtil {

    /**
     * 返回对象中指定元素的值对应的枚举对象
     * @param code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        //for(元素类型t 元素变量x : 遍历对象obj){
        //     引用了x的java语句;
        //}
        for (T each : enumClass.getEnumConstants()){
            //class的getEnumConstants()方法获取枚举类中所有的枚举常量
            if(code.equals(each.getCode())){
                return each;
                //返回对应的枚举常量对象
            }
        }
        return null;
    }
}
