package com.jtd.recharge.connect.base;

import com.jtd.recharge.base.util.PropertiesUtils;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

/**
 * @autor jipengkun
 */
@Service
public class ChargeAdapterList {

    private Log log = LogFactory.getLog(this.getClass());

    private  static Properties properties = null;

    static {
        properties = PropertiesUtils.loadProperties("config/supply-mapping.properties");
    }

    public ChargeSubmitResponse chargeRequest(List<ChargeRequest> chargeRequestList) throws Exception{

        String supplyName = chargeRequestList.get(0).getSupplyName();
        String supplyClass = properties.getProperty(supplyName);//供应商与实现类映射

        //反射对应的通道实现类
        Class cls = Class.forName(supplyClass);
        Method setMethod = cls.getDeclaredMethod("chargeRequest", List.class);
        ChargeSubmitResponse chargeSubmitResponse = (ChargeSubmitResponse) setMethod.invoke(cls.newInstance(), chargeRequestList);
        return chargeSubmitResponse;
    }
}
