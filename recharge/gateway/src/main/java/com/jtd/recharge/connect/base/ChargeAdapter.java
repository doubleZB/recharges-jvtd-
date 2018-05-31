package com.jtd.recharge.connect.base;

import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.base.util.PropertiesUtils;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.flow.ronglian.RongLiangFlowRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @autor jipengkun
 */
@Service
public class ChargeAdapter {

    private Log log = LogFactory.getLog(this.getClass());

    private  static Properties properties = null;

    static {
        properties = PropertiesUtils.loadProperties("config/supply-mapping.properties");
    }

    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception{

        String supplyName = chargeRequest.getSupplyName();
        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---" + supplyName);
        String supplyClass = properties.getProperty(supplyName);//供应商与实现类映射

        //反射对应的通道实现类
        Class cls = Class.forName(supplyClass);
        Method setMethod = cls.getDeclaredMethod("chargeRequest", ChargeRequest.class);
        ChargeSubmitResponse chargeSubmitResponse = (ChargeSubmitResponse) setMethod.invoke(cls.newInstance(), chargeRequest);
        log.info("9、发送结束----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---" + supplyName+"  供应商提交结果---" + chargeSubmitResponse.getStatus());
        return chargeSubmitResponse;
    }
}
