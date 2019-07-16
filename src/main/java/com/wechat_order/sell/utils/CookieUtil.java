package com.wechat_order.sell.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtil {

    /**
     * 设置cookie
     * @param servletResponse
     * @param name
     * @param value
     * @param maxAge
     */
    public  static void set(HttpServletResponse servletResponse,String name,String value,int maxAge){
        Cookie cookie = new Cookie(name,value) ;
        cookie.setPath("/");
        cookie.setMaxAge(7200);
        servletResponse.addCookie(cookie);
    }

    /**
     * 查询cookie
     * @param request
     * @param name
     * @return
     */
    public  static Cookie get(HttpServletRequest request,String name){
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if(cookieMap.containsKey(name)){
            return cookieMap.get(name);
        }
        return null;
    }

    /**
     * cookie映射器
     * @param request
     * @return
     */
    private static Map<String,Cookie> readCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }
}
