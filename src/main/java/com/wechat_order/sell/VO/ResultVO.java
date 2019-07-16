package com.wechat_order.sell.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * http请求返回给最外层对象
 */

@Data
public class ResultVO<T> implements Serializable {
    //为了缓存要将对象序列化

    //用插件生成ID
    private static final long serialVersionUID = 309381178046926036L;

    //错误码code
    private Integer code;

    //提示信息msg
    //若字段为必须，设置初始值以避免为null（在全局或局部排除null的情况下）
    //private String msg = "";
    private String msg;

    //返回的具体内容
    private T data;
}
