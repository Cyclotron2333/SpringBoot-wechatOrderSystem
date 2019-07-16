package com.wechat_order.sell.utils;

public class MathUtil {

    private static final Double MONEY_BOUND = 0.01;

    /*比较两个金额是否相等*/
    public static Boolean equal(Double d1,Double d2){
        double result = Math.abs(d1 - d2);
        if (result<MONEY_BOUND){
            return true;
        }else {
            return false;
        }
    }
}
