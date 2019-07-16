package com.wechat_order.sell.controller;

import com.wechat_order.sell.config.ProjectUrlConfig;
import com.wechat_order.sell.constant.CookieConstant;
import com.wechat_order.sell.constant.RedisConstant;
import com.wechat_order.sell.dataobject.SellerInfo;
import com.wechat_order.sell.enums.ResultEnum;
import com.wechat_order.sell.service.SellerService;
import com.wechat_order.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户相关操作
 */

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService ;

    //用来往redis中写String类型的值
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * 登录
     * 已登录的判断条件：cookie中token对应value值和数据库中的相同
     * @param openid
     * @param servletResponse
     * @param map
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse servletResponse,
                              //HttpServletResponse在此方法中用来设置cookie
                              Map<String,Object> map){

        //1.openid去和数据库里的数据匹配
        //匹配失败说明没有使用系统的资格
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenId(openid);
        if (sellerInfo==null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        //登录成功后
        //2.设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;  //过期时间

        //opsForValue是对value值的操作
        //String.format将字符串格式化，以token_开头
        //第一个是redis的key，第二个是value
        //第三个是过期时间，第四个是时间的单位
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);

        //3.设置token至cookie
        //在今后的访问中,浏览器cookie中taken在redis中存在，且taken对应值不为空（null）则不需要进行登录验证
        CookieUtil.set(servletResponse, CookieConstant.TOKEN,token,expire);

        //只是跳转而非Model的渲染
        //跳转最好用完成的http地址
        //即用绝对地址而非相对地址
        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list");
    }

    /**
     * 注销功能，将cookie和redis对应项删除
     * @param request
     * @param response
     * @param map
     * @return
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       //HttpServletResponse在此方法中用来设置cookie
                       Map<String,Object> map){
        //1.从cookie中查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null){
            //2.清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //3.清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }

        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);

    }
}
