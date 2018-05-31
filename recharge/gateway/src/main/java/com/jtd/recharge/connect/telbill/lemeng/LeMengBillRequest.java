package com.jtd.recharge.connect.telbill.lemeng;

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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lhm 乐盟话费 on 2017/6/21.
 */
public class LeMengBillRequest implements ConnectReqest {

    private Log log = LogFactory.getLog(this.getClass());


    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }


    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商---乐盟话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        String merid = (String) supplyMap.get("merid");
        String host = (String) supplyMap.get("host");
        String usercode = (String) supplyMap.get("usercode");
        String rechargeType = (String) supplyMap.get("rechargeType");
        String merorderid = chargeRequest.getChannelNum();//合作商订单号，请确保订单号的唯一性
        String mobile = chargeRequest.getMobile();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String requestdate=formatter.format(new java.util.Date());
        String amount = chargeRequest.getPositionCode();//充值金额
        String denomination = amount+"00";

        String callBackUri = (String) supplyMap.get("callback_url");
        String key = (String) supplyMap.get("key");

        String sign= DigestUtils.md5Hex("denomination="+denomination+"&merid="+merid+"&merorderid="+merorderid+"&mobile="+mobile+
                "&rechargeType="+rechargeType+"&requestdate="+requestdate+"&usercode="+usercode+"&key="+key).toUpperCase();


        Map param = new HashMap();
        param.put("merid",merid);
        param.put("usercode",usercode);
        param.put("merorderid",merorderid);
        param.put("rechargeType",rechargeType);
        param.put("mobile",mobile);
        param.put("requestdate",requestdate);
        param.put("denomination",denomination);
        param.put("callBackUri",callBackUri);
        param.put("sign",sign);

        String resultContent = "";

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        response.setChannelNum(merorderid);
        log.info("8、发送流程：发送供应商---乐盟话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));
        try {
            resultContent = HttpTookit.doPost(host,JSON.toJSONString(param));
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---乐盟话费----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到乐盟话费！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            return response;
        }
        log.info("8、发送流程：发送供应商---乐盟话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        if(resultContent==null){
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg("提交到供应商返回值为空！");
            response.setStatusCode("999");
            return response;
        }
        JSONObject json= JSON.parseObject(resultContent);
        String error_msg = (String) json.get("error_msg");
        Integer rsp_code =  json.getInteger("rsp_code");//订单状态200成功
        if(rsp_code==200) {
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            log.info("8、发送流程：发送供应商---乐盟话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到乐盟话费成功！");
        } else {
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(error_msg+"请咨询供应商！");
            response.setStatusCode(rsp_code.toString());
            log.info("8、发送流程：发送供应商---乐盟话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到乐盟话费失败！原因："+error_msg+"请咨询供应商！");

        }
        response.setStatusMsg(error_msg);

        return response;
    }
}
