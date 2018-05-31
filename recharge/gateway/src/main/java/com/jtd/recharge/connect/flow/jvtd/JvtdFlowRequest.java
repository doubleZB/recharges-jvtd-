package com.jtd.recharge.connect.flow.jvtd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wangxiangping on 2017-02-06.
 */
public class JvtdFlowRequest  implements ConnectReqest {

    private Log log = LogFactory.getLog(this.getClass());

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        response.setStatus(ChargeSubmitResponse.Status.FAIL);
        long start =System.currentTimeMillis();
        log.info("8、发送流程-老平台----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());


        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String host = (String)supplyMap.get("host");
        String username = (String)supplyMap.get("username");
        String transId = chargeRequest.getChannelNum();
        String appSecret = (String)supplyMap.get("appSecret");
        String productId = chargeRequest.getPositionCode();
        String mobile = chargeRequest.getMobile();
        String appId = "2"; // appId=2流量;appId=3话费
        String md5 =DigestUtils.md5Hex(username + transId + productId
                + mobile + appId + appSecret);

        Map param = new HashMap();
        param.put("username",username);
        param.put("transId",transId);
        param.put("productId",productId);
        param.put("mobile",mobile);
        param.put("appId",appId);
        param.put("sign",md5);

        String result = "";
        log.info("8、发送流程----老平台----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        try {
            result = HttpTookit.doPost(host,param);
        } catch (Exception e) {
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            log.error("8.老平台流量 老平台 exception；订单参数："+ JSON.toJSONString(param),e);
            response.setStatusMsg("提交订单时候，http访问出错"+e.getMessage()+"；订单参数："+JSON.toJSONString(param));
            return response;
        }
        log.info("8、+“返回数据：" +username+"向老流量平台发送数据--" + JSON.toJSONString(param)+" 返回数据---"+ result+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(result);
        String statusCode = object.getString("code");
        String desc = object.getString("message");
        String orderNum = "";
        response.setStatusMsg(result);
        if(statusCode.equals("000")){
            response.setStatusMsg(desc);
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            orderNum = object.getString("billId");
            response.setChannelNum(transId);
            response.setSupplyChannelNum(orderNum);
            log.info("8、发送流程---老平台----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到老平台成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(desc);
            response.setStatusCode(statusCode);
            log.info("8、发送流程---老平台----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到老平台失败！原因："+desc+"请咨询供应商！");
        }
        return response;
    }
}
