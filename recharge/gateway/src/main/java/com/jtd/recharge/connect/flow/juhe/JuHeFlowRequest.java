package com.jtd.recharge.connect.flow.juhe;

import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @autor jipengkun
 */
public class JuHeFlowRequest implements ConnectReqest{
    private Log log = LogFactory.getLog(this.getClass());


    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        return null;
    }
}
