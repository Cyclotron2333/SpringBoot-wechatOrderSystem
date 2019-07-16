package com.wechat_order.sell.service.impl;

import com.wechat_order.sell.dataobject.SellerInfo;
import com.wechat_order.sell.repository.SellerInfoRepository;
import com.wechat_order.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenId(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }
}
