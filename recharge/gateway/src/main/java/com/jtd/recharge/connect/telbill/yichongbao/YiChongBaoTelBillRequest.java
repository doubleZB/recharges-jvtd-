package com.jtd.recharge.connect.telbill.yichongbao;

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
 * @autor jipengkun
 * 易充宝
 */
public class YiChongBaoTelBillRequest implements ConnectReqest{

    private Log log = LogFactory.getLog(this.getClass());


    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }


    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商---易充宝----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        String userId = (String) supplyMap.get("userId");
        String userOrderNo = chargeRequest.getChannelNum();//合作商订单号，请确保订单号的唯一性
        String phoneNumber = chargeRequest.getMobile();
        String chargeMon = chargeRequest.getPositionCode();//充值金额
        String chargeType = "0";
        String notifyUrl = (String) supplyMap.get("callback_url");
        String key = (String) supplyMap.get("key");
        String sign=DigestUtils.md5Hex(userId+ userOrderNo+ phoneNumber+ chargeMon+ chargeType+ notifyUrl +key);

        String host = (String) supplyMap.get("host");

        Map param = new HashMap();
        param.put("userId",userId);
        param.put("userOrderNo",userOrderNo);
        param.put("phoneNumber",phoneNumber);
        param.put("chargeMon",chargeMon);
        param.put("chargeType",chargeType);
        param.put("notifyUrl",notifyUrl);
        param.put("sign",sign);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp=formatter.format(new java.util.Date());

        param.put("timestamp",timestamp);

        String resultContent = "";

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        log.info("8、发送流程：发送供应商---易充宝----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        try {
            resultContent = HttpTookit.doPost(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---易充宝----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到易充宝！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            return response;
        }
        log.info("8、发送流程：发送供应商---易充宝----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject json= JSON.parseObject(resultContent);

        String resultno = (String) json.get("resultno");
        String resulDes = (String) json.get("resulDes");
        String userOrderNoResult = (String) json.get("userOrderNo");//合作商订单号
        String serialId = (String) json.get("serialId");//系统定单号
        String orderStatus = (String) json.get("orderStatus");//订单状态，10000为创建订单成功 10001创建失败
        String payMoney = (String) json.get("payMoney");//扣款金额


        if("10000".equals(orderStatus)) {
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            log.info("8、发送流程：发送供应商---易充宝----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到易充宝成功！");
        } else {
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(orderStatus);
            response.setStatusMsg(orderStatus+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---易充宝----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到易充宝失败！原因："+orderStatus+"请咨询供应商！");
        }
        response.setStatusCode(resultno);
        response.setChannelNum(userOrderNoResult);
        response.setStatusMsg(resulDes);

        return response;
    }


}
