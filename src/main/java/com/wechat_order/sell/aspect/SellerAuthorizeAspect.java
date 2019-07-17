package com.wechat_order.sell.aspect;

import com.wechat_order.sell.constant.CookieConstant;
import com.wechat_order.sell.constant.RedisConstant;
import com.wechat_order.sell.exception.SellerAuthorizeException;
import com.wechat_order.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 访问前的认证环节
 */

//客户端使用用户名和密码请求登录。服务端收到请求，验证用户名和密码。
//验证成功后，服务端会生成一个token，然后把这个token发送给客户端。客户端收到token后把它存储起来，可以放在cookie或者Local Storage（本地存储）里。
//客户端每次向服务端发送请求的时候都需要带上服务端发给的token。服务端收到请求，然后去验证客户端请求里面带着token，
//如果验证成功，就向客户端返回请求的数据。

@Aspect
//@Aspect:作用是把当前类标识为一个切面供容器读取
@Slf4j
@Component
public class SellerAuthorizeAspect {

    @Autowired
    StringRedisTemplate redisTemplate;

    //@Pointcut：Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，一是表达式，二是方法签名。
    //方法签名必须是 public及void型。可以将Pointcut中的方法看作是一个被Advice引用的助记符，
    //因为表达式不直观，因此我们可以通过方法签名的方式为此表达式命名。因此Pointcut中的方法只需要方法签名，而不需要在方法体内编写实际代码。
    @Pointcut("execution(public * com.wechat_order.sell.controller.Seller*.*(..))"
            + "&& !execution(public * com.wechat_order.sell.controller.SellerUserController.*(..))")
    public void verify(){}

    //@Before：标识一个前置增强方法，相当于BeforeAdvice的功能
    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询cookie，cookie中没有token说明不处于登录状态
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null){
            log.warn("【登录校验】cookie中查不到token");
            throw new SellerAuthorizeException();
        }
        //若存在token则进行检查
        //去redis中查询token对应的值，对应的值（其实是用户名）为空则无需
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if(StringUtils.isEmpty(tokenValue)){
            log.warn("【登录校验】redis中查不到token");
            throw new SellerAuthorizeException();
        }

    }

}

//@Around：环绕增强，相当于MethodInterceptor
//@AfterReturning：后置增强，相当于AfterReturningAdvice，方法正常退出时执行
//@AfterThrowing：异常抛出增强，相当于ThrowsAdvice
//@After: final增强，不管是抛出异常或者正常退出都会执行