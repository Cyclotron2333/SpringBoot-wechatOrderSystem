package com.wechat_order.sell.service;

import com.wechat_order.sell.dataobject.SellerInfo;

/**
 *  管理卖家登陆权限和账号密码等
 */

public interface SellerService {

    /**
     * 通过openid查询卖家信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenId(String openid);

}
