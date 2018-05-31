package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.ChargeProductStore;
import com.jtd.recharge.dao.po.ChargeProductStoreF;
import com.jtd.recharge.dao.po.UserMobileRecharge;

import java.util.List;

public interface ChargeProductStoreFMapper {


    List<ChargeProductStoreF> selectProductStore(ChargeProductStoreF productStoreF);

    /**
     * 一键修改折扣
     * @param productStoreF
     * @return
     */
    int edit_one_key(ChargeProductStoreF productStoreF);

    /**
     * 用户端页面充值通过手机号获取产品
     * @param userMobileRecharge
     * @return
     */
    List<UserMobileRecharge> selectUserMobileProductStore(UserMobileRecharge userMobileRecharge);

    List<ChargeProductStore> getStoreIds(List<Integer> groupIds);
}