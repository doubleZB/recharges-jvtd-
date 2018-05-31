package com.jtd.recharge.connect.base;


import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;

import java.util.List;

/**
 * @autor jipengkun
 */
public interface ConnectReqestList {


    ChargeSubmitResponse chargeRequest(List<ChargeRequest> chargeRequestList) throws Exception;

}
