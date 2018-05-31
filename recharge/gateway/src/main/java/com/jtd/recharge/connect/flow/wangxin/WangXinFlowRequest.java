package com.jtd.recharge.connect.flow.wangxin;

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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @autor jipengkun
 * 微网 流量 充值
 */
@Service
public class WangXinFlowRequest implements ConnectReqest{

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商---微网----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String notifyUrl=(String) supplyMap.get("callback_url");
        //客户修改1：===>订购接口私钥：乐疯平台为用户分配的接口调用私钥
        String key =(String) supplyMap.get("apiKey");
        //客户修改2：===>流量规格:1024,10240
        String orderMeal =chargeRequest.getPositionCode();
        //客户修改3：===>用户名：乐疯平台为用户开的用户
        String userName =(String) supplyMap.get("userName");
        //客户修改4：===>手机号
        String mobile = chargeRequest.getMobile();
        //客户修改5：===>订单编号64位以内
        String msgId = chargeRequest.getChannelNum();
        //客户修改6：===>0：全国  1省内非漫游 2漫游
        String range = (String) supplyMap.get("range");
        //时间戳：5分钟内订单有效
        String timeStamp = Long.toString(new Date().getTime()/1000);

        //签名
        String sign=DigestUtils.sha1Hex("userName"+userName+"mobile"+mobile+"orderMeal"+orderMeal
                +"timeStamp"+timeStamp+"key"+key).toLowerCase();


        Map param = new HashMap();
        param.put("timeStamp", timeStamp);
        param.put("userName", userName);
        param.put("mobile", mobile);
        param.put("sign", sign);
        param.put("range", range);
        param.put("msgId", msgId);
        param.put("orderMeal", orderMeal);
        param.put("notifyUrl", notifyUrl);

        log.info("8、发送流程：发送供应商---微网----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String res=JSONObject.toJSONString(param);
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,res);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---微网----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到微网！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程：发送供应商---微网----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("code");
        if("0000".equals(respCode)){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
            log.info("8、发送流程：发送供应商---微网----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到微网成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setChannelNum(chargeRequest.getChannelNum());
            response.setStatusMsg(respCode+"请咨询供应商！");
            response.setStatusCode(respCode);
            log.info("8、发送流程：发送供应商---微网----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到微网失败！原因："+respCode+"请咨询供应商！");
        }
        return response;
    }
}
