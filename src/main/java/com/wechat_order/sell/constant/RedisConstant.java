package com.wechat_order.sell.constant;

/**
 * redis常量
 */

public interface RedisConstant {

    String TOKEN_PREFIX = "token_%s";   //前缀

    Integer EXPIRE = 7200;  //过期时间两小时
}
