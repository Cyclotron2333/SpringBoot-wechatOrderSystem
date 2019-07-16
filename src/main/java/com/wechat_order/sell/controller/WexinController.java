package com.wechat_order.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 手工方式授权，ord version
 */

@RestController
@RequestMapping("/weixin")
@Slf4j
public class WexinController {

    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
      log.info("进入auth方法...");
      log.info("code={}",code);
      String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx100bce7a2d33f123&secret=c979f4aeb76938c229038375d06f7b7f&code="+code+"&grant_type=authorization_code";
      //RestTemplate是Spring提供的用于访问Rest服务的客户端，RestTemplate提供了多种便捷访问远程Http服务的方法
      //是Spring用于同步client端的核心类，简化了与http服务的通信，并满足RestFul原则，程序代码可以给它提供URL，并提取结果
      RestTemplate restTemplate = new RestTemplate();
      String response = restTemplate.getForObject(url, String.class);
      log.info("response={}",response);
    }
}
