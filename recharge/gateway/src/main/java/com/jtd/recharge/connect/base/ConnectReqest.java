package com.jtd.recharge.connect.base;


import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;

/**
 * @autor jipengkun
 */
public interface ConnectReqest {


    ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception;

}
