package com.jtd.recharge.connect.flow.maiguanghuyu;

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
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 麦广互娱 on 2017/7/21
 * lhm
 */
@Service
public class MaiGuangHuYuFlowRequest implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {

        log.info("8、发送流程--发送供应商---麦广互娱---mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());
        long start =System.currentTimeMillis();

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String host = (String) supplyMap.get("host");
        String customer = (String) supplyMap.get("customer");
        String Token = (String) supplyMap.get("Token");
        String returnUrl=(String) supplyMap.get("callback_url");
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMddhhmmss");
        String dtCreate=df.format(new Date());
        String phone = chargeRequest.getMobile();
        String product = chargeRequest.getPositionCode();

        String customerOrderId = chargeRequest.getChannelNum();

        String sign= DigestUtils.md5Hex("customer="+customer+"&customerOrderId="+customerOrderId+"&phone="+phone+"&product="+product+"&returnUrl="+returnUrl+Token);

        Map param = new HashMap();
        param.put("customer",customer);
        param.put("product",product);
        param.put("phone",phone);
        param.put("customerOrderId",customerOrderId);
        param.put("returnUrl",returnUrl);
        param.put("sign",sign);

        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---麦广互娱； 封装应用参数json报文请求参数---" +JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doGet(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---麦广互娱----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到麦广互娱流量！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程：发送供应商---麦广互娱----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));
        Document document = null;
        try {
            JSONObject object = JSON.parseObject(resultContent);
            String resultCode = object.getString("resultCode");
            String resultMsg = object.getString("resultMsg");
            if(("1").equals(resultCode)){
                response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
                response.setChannelNum(customerOrderId);
                log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到麦广互娱成功！");
            }else{
                response.setStatus(ChargeSubmitResponse.Status.FAIL);
                response.setStatusCode(resultCode);
                response.setStatusMsg(resultMsg);
                log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到麦广互娱失败！原因："+resultMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

}
