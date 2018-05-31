package com.jtd.recharge.connect.flow.chengduchongxin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpClients;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.XMLUtil;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import com.jtd.recharge.dao.bean.util.Md5Util;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lhm on 2017/8/10
 * 成都众信
 */
@Service
public class ChengDuZhongXinFlowRequest implements ConnectReqest{
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
        log.info("8、发送流程：发送供应商---成都众信----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");//充值地址
        String account = (String) supplyMap.get("account");//用户账号
        String apiKey = (String) supplyMap.get("key");
        String action = (String) supplyMap.get("action");
        String callbackurl = (String) supplyMap.get("callback_url");

        String phone = chargeRequest.getMobile();//手机号码
        String size = chargeRequest.getPositionCode();//流量大小
        String orderNo = chargeRequest.getChannelNum();
        String timeStamp = String.valueOf(System.currentTimeMillis()).substring(0,10);
        int range=0;

        String sign= DigestUtils.md5Hex(apiKey+"account="+account+"&action="+action+"&phone="+phone+"&range="+range+
                "&size="+Integer.parseInt(size)+"&timeStamp="+Integer.parseInt(timeStamp)+apiKey);


        HashMap map=new HashMap();
        map.put("account", account);
        map.put("action", action);
        map.put("phone", phone);
        map.put("size", Integer.parseInt(size));
        map.put("orderNo", orderNo);
        map.put("timeStamp", Integer.parseInt(timeStamp));
        map.put("sign", sign);
        map.put("range", range);

        String date="account="+account+"&action="+action+"&phone="+phone+"&size="+size+"&range="
                +range+"&timeStamp="+timeStamp+"&sign="+sign+"&orderNo="+orderNo;


        /**
         * 提交消息
         */
        log.info("8、发送流程：发送供应商---成都众信----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(map));
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpClients.doPost(host,date,"application/x-www-form-urlencoded");
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---成都众信----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到成都众信！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---成都众信----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        String errorCode ="";
        String respMsg ="";
        try {
            JSONObject object= JSON.parseObject(resultContent);
            errorCode=object.getString("respCode");
            respMsg=object.getString("respMsg");

            if(errorCode.equals("0000")){
                response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
                response.setChannelNum(orderNo);
                log.info("8、发送流程：发送供应商---成都众信----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到成都众信成功！");
            }else{
                response.setStatus(ChargeSubmitResponse.Status.FAIL);
                response.setStatusCode(errorCode);
                response.setStatusMsg(respMsg+"请咨询供应商！");
                log.info("8、发送流程：发送供应商---成都众信----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到成都众信失败!原因："+respMsg+"请咨询供应商！");

            }
        }catch (Exception e){
            log.error("8、发送异常：发送供应商---成都众信----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"原因："+ e.getLocalizedMessage());
        }
        return response;
    }
}
