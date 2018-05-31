package com.jtd.recharge.connect.flow.huangteng;

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
 * Created by lhm on 2017/8/17.
 * 凰腾流量
 */
@Service
public class HuangTengFlowRequest implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        long start =System.currentTimeMillis();
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        log.info("8、发送流程：发送供应商---凰腾流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String username = (String) supplyMap.get("account");
        String key = (String) supplyMap.get("key");
        String reporturl = (String) supplyMap.get("callback_url");
        String number = chargeRequest.getMobile();
        String flowsize = chargeRequest.getPositionCode();
        String user_order_id = chargeRequest.getChannelNum();
        long timestamp = System.currentTimeMillis();

        String sign = DigestUtils.md5Hex("username="+username+"&apikey="+key).toUpperCase();

        Map<String, String> param = new HashMap<String, String>();
        param.put("username", username);
        param.put("number", number);
        param.put("flowsize", flowsize);
        param.put("user_order_id", user_order_id);
        param.put("sign", sign);
        param.put("reporturl", reporturl);
        param.put("timestamp", String.valueOf(timestamp));

        log.info("8、发送流程：发送供应商---凰腾流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doGet(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---凰腾流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到凰腾流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---凰腾流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String errcode=object.getString("errcode");
        String errmsg=object.getString("errmsg");
        if(errcode.equals("0")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(user_order_id);
            log.info("8、发送流程：发送供应商---凰腾流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到凰腾流量成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(errcode);
            response.setStatusMsg(errmsg+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---凰腾流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到凰腾流量失败！原因："+errmsg+"请咨询供应商！");
        }
        return response;
    }

}
