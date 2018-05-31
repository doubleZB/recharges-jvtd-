package com.jtd.recharge.connect.flow.zongxiong;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lhm on 2017/6/7.
 * 棕熊流量
 */
@Service
public class ZongXiongFlowRequest implements ConnectReqest {

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
        log.info("8、发送流程：发送供应商---棕熊流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");//充值地址
        String SecretKey = (String) supplyMap.get("SecretKey");
        String appId = (String) supplyMap.get("appId");
        String rechargeMode = (String) supplyMap.get("rechargeMode");//充值方式
        String phone = chargeRequest.getMobile();//手机号码
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMddhhmmss");
        String serialNum=df.format(new Date());
//        String channelOrderId = chargeRequest.getPackageSize();//流量大小
        String cardCode = chargeRequest.getPositionCode();//充值流量编码
        String channelOrderId = chargeRequest.getChannelNum();//订单号

        //添加中自动生成一个32位的字符串
//        String usersid = UUID.randomUUID().toString().trim().replaceAll("-", "");
//        String singOne = appId+cardCode+channelOrderId+phone+rechargeMode+serialNum+SecretKey;
        String sign= DigestUtils.md5Hex(appId+cardCode+channelOrderId+phone+rechargeMode+serialNum+SecretKey);//加密串

        Map param = new HashMap();
        param.put("appId",appId);
        param.put("serialNum",serialNum);
        param.put("channelOrderId",channelOrderId);
        param.put("phone",phone);
        param.put("cardCode",cardCode);
        param.put("rechargeMode",rechargeMode);
        param.put("sign",sign);
        log.info("8、发送流程：发送供应商---棕熊流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,JSON.toJSONString(param));
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---棕熊流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到棕熊流量！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程：发送供应商---棕熊流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object= JSON.parseObject(resultContent);
        String errorCode=object.getString("responseCode");
        if(errorCode.equals("KK0000")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(channelOrderId);
            log.info("8、发送流程：发送供应商---棕熊流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到棕熊流量成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(errorCode);
            response.setStatusMsg(errorCode+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---棕熊流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到棕熊流量失败！原因："+errorCode+"请咨询供应商！");
        }
        return response;
    }
}
