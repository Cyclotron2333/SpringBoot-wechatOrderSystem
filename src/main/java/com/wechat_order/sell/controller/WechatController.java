package com.wechat_order.sell.controller;

import com.wechat_order.sell.config.ProjectUrlConfig;
import com.wechat_order.sell.enums.ResultEnum;
import com.wechat_order.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * SDK方式授权
 */

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    //1.配置，在WeChatMpConfig的wxMpConfigStorage方法中完成
    private WxMpService wxMpService;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        String url = projectUrlConfig.getWechatOpenAuthorize() +  "/sell/wechat/userInfo";
        //2.调用方法
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url
                , WxConsts.OAuth2Scope.SNSAPI_USERINFO
                , URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】获取code，result={}",redirectUrl);
        return "redirect:" + redirectUrl;
    }

    //使用@RestController不会自动
    // 跳转
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl){

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}",e);
            throw new SellException(ResultEnum.WX_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:"+returnUrl+"?openid="+openId;

    }

    /**
     * 二维码登录授权
     * @param returnUrl
     * @return
     */
    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl){
        String url = projectUrlConfig.getWechatOpenAuthorize() +  "/sell/wechat/qrUserInfo";
        String redirectUrl = wxOpenService.buildQrConnectUrl(url
                , WxConsts.QrConnectScope.SNSAPI_LOGIN
                ,URLEncoder.encode(returnUrl));
        return "redirect:" + redirectUrl;

    }

    /**
     *
     * @param code
     * @param returnUrl 疑惑：为什么取的是state
     * @return
     */
    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl){

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}",e);
            throw new SellException(ResultEnum.WX_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        //访问获取openId后回调到下面的地址
        return "redirect:"+returnUrl+"?openid="+openId;
    }

}
