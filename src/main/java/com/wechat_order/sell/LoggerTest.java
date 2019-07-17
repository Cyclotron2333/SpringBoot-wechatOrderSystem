package com.wechat_order.sell;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j  //使用此注解以后可以直接用log
public class LoggerTest {

    /*private final Logger looger = LoggerFactory.getLogger(LoggerTest.class); */   //写当前的类。可以使用注解替换。
    @Test
    public void test1(){
        /*looger.debug("debug...");   //级别低于info，不显示
        looger.info("info...");
        looger.error("error...");*/
        String name = "Lee";    //日志中输出变量
        String password = "123456";
        log.debug("debug...");   //级别低于info，不显示
        log.info("name: " + name + ", password: " + password);
        log.info("name: {}, password: {}" ,name,password);  //使用占位符{}节省代码量
        log.error("error...");
        log.warn("waen....");

        //使用application.yml进行日志配置
    }


}
