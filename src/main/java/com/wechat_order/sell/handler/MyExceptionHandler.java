package com.wechat_order.sell.handler;

import com.wechat_order.sell.VO.ResultVO;
import com.wechat_order.sell.config.ProjectUrlConfig;
import com.wechat_order.sell.exception.ResponseBankException;
import com.wechat_order.sell.exception.SellException;
import com.wechat_order.sell.exception.SellerAuthorizeException;
import com.wechat_order.sell.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

//当Controller中发生错误，从而进入了@ControllerAdvice注解标注的类中的@ExceptionHandler(Exception.class)标注的方法时，
//如果想返回json数据，只需要在该方法上标注@ResponseBody，就会将方法的返回值以Json的方式发送给调用者。
//想返回一个html页面，或者ftl模版生成的html页面给访问者。那么此时需要该方法返回一个ModelAndView对象


@ControllerAdvice
//ContrllerAdvice也可以这么理解，其抽象级别应该是用于对Controller进行“切面”环绕的，而具体的业务织入方式则是通过结合其他的注解来实现的。
public class MyExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //拦截登录异常，返回给前端
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handerSellerAuthorizeException(){
        //http://cycsell.mynatapp.cc/sell/wechat/qrAuthorize?returnUrl=http://cycsell.mynatapp.cc/sell/seller/login
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getWechatOpenAuthorize())
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(projectUrlConfig.getSell())
                .concat("/sell/seller/login"));
    }

    /**
     * 返回json格式
     * @param
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = SellException.class)
    public ResultVO handerSellerSellException(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

    /**
     * 演示如何不返回200 OK
     * 这个异常处理和此项目无关
     */
    @ResponseBody
    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)   //返回403
    public void handerSellerResponseBankException(){
    }
}
