package com.jtd.recharge.connect.telbill.moyou;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liyabin on 2017/9/5.
 */
@Service
public class MoYouTelbillRequest implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {

        log.info("8、发送流程--发送供应商---魔游话费---mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());
        long start =System.currentTimeMillis();

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String host = (String) supplyMap.get("host");
        String customer = (String) supplyMap.get("customer");
        String token = (String) supplyMap.get("token");
        String callbackUrl = (String) supplyMap.get("callback_url");
        String phone = chargeRequest.getMobile();
        String product = chargeRequest.getPositionCode();

        String customerId = chargeRequest.getChannelNum();
        String sign= DigestUtils.md5Hex("customer="+customer+"&customerOrderId="+customerId+"&phone="+phone+
                "&product="+product+"&returnUrl="+callbackUrl+token);

        Map param = new HashMap();
        param.put("customer",customer);
        param.put("product",product);
        param.put("phone",phone);
        param.put("customerOrderId",customerId);
        param.put("returnUrl",callbackUrl);
        param.put("sign",sign);

        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---魔游话费； 封装应用参数json报文请求参数---" + JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doGet(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---魔游话费----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到魔游话费流量！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            return response;
        }
        log.info("8、发送流程：发送供应商---魔游话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("resultCode");
        String Message=object.getString("resultMsg");
        if("1".equals(respCode)){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(customerId);
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到魔游话费成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(Message);
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到魔游话费失败！原因："+Message);
        }
        return response;
    }
}
