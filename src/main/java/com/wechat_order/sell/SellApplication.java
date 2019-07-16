package com.wechat_order.sell;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/*@SpringBootApplication  : 是Sprnig Boot项目的核心注解，目的是开启自动配置*/
@SpringBootApplication
//@SpringBootApplication = (默认属性)@Configuration + @EnableAutoConfiguration + @ComponentScan。
//@Configuration的注解类标识这个类可以使用Spring IoC容器作为bean定义的来源。@Bean注解告诉Spring
//一个带有@Bean的注解方法将返回一个对象，该对象应该被注册为在Spring应用程序上下文中的bean。
//IoC容器即“控制反转”，就是具有依赖注入功能的容器，是可以创建对象的容器
//IOC容器负责实例化、定位、配置应用程序中的对象及建立这些对象间的依赖。
//@EnableAutoConfiguration：能够自动配置spring的上下文，试图猜测和配置你想要的bean类，通常会自动根据你的类路径和你的bean定义自动配置。
//@ComponentScan：会自动扫描指定包下的全部标有@Component的类，并注册成bean
// 当然包括@Component下的子注解@Service,@Repository,@Controller。
@EnableCaching
//@EnableCaching启用缓存
@MapperScan(basePackages = "com.wechat_order.sell.dataobject.mapper")
//设置Mybatis的mapper的位置
public class SellApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellApplication.class, args);
    }

}
