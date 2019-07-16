package com.wechat_order.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
//告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定；
// prefix = "xxx"：配置文件中哪个下面的所有属性进行一一映射//
// 只有这个组件是容器中的组件，才能拥有容器提供的@ConfigurationProperties功能；
// @ConfigurationProperties(prefix = "xxx")默认从全局配置文件中获取值；
public class WechatAccountConfig {

    /*公众平台id*/
    private String myAppId;

    /*公众平台密钥*/
    private String myAppSecret;

    /*开放平台id*/
    private String openAppId;

    /*开放平台密钥*/
    private String openAppSecret;

    /*商户号*/
    private String mchId;

    /*商户密钥*/
    private String mchKey;

    /*商户证书路径*/
    private String keyPath;

    /* 证书内容*/
    private SSLContext sslContext;

    /*微信支付异步通知地址*/
    private String notifyUrl;

    /*微信模版id*/
    private Map<String,String> templateId;


}
