package com.wechat_order.sell.service.impl;

import com.wechat_order.sell.config.WechatAccountConfig;
import com.wechat_order.sell.dto.OrderDTO;
import com.wechat_order.sell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PushMassageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService ;

    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        //模版号
        templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
        //发给谁
        templateMessage.setToUser(orderDTO.getOrderId());

        List<WxMpTemplateData> data = Arrays.asList(
                //这里瞎写的，填模版里的值
                new WxMpTemplateData("first1","hello"),
                new WxMpTemplateData("keyword1","hello"),
                new WxMpTemplateData("keyword2","hello"),
                new WxMpTemplateData("keyword3",orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4",orderDTO.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("keyword5",orderDTO.getOrderAmount().toString()),
                new WxMpTemplateData("remark","hello")
        );

        templateMessage.setData(data);

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e){
            log.error("【微信模版消息】发送失败，{}",e);
        }
    }
}
