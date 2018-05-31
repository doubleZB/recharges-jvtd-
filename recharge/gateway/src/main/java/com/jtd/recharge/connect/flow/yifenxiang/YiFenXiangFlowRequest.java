package com.jtd.recharge.connect.flow.yifenxiang;

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
import java.util.*;

/**
 * @autor lyp
 * 易分享省网流量 充值
 */
@Service
public class YiFenXiangFlowRequest implements ConnectReqest{

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
        log.info("8、发送流程：发送供应商---易分享省网流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");//时间戳格式
        String timestamp=formatter.format(new java.util.Date());//时间戳
        String host = (String) supplyMap.get("host");//充值地址
        String appKey = (String) supplyMap.get("appKey");//appkey
        String appSecret = (String) supplyMap.get("appSecret");//appsecret
        String callbackURL = (String) supplyMap.get("callbackURL");//回调地址
        String mobile = chargeRequest.getMobile();//充值手机号
        String productName = chargeRequest.getPositionCode();//充值流量代码
        String orderid = chargeRequest.getChannelNum();//订单号
        String secertKey=DigestUtils.md5Hex(appKey+timestamp+appSecret).toUpperCase(); //加密串 大写

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("productName", productName);
        jsonObject.put("mobile", mobile);
        jsonObject.put("orderid", orderid);
        List list =new ArrayList();
        list.add(jsonObject);
        Map map = new HashMap<String,String>();
        map.put("appKey", appKey);
        map.put("secertKey", secertKey);
        map.put("timestamp", timestamp);
        map.put("callbackURL", callbackURL);
        map.put("orderList", list);
        String jsonMap=JSON.toJSONString(map);
        log.info("8、发送流程：发送供应商---易分享省网流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(map));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,jsonMap);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---易分享省网流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到易分享省网流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---易分享省网流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String code=object.getString("code");
        if(code.equals("000")){
            String orderID=orderid;
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);
            log.info("8、发送流程：发送供应商---易分享省网流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到易分享省网流量成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(code);
            response.setStatusMsg(code+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---易分享省网流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到易分享省网流量失败！原因："+code+"请咨询供应商！");

        }
        return response;
    }
}
