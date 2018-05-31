package com.jtd.recharge.connect.flow.anhuiyunmei;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpClients;
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
 * @autor lyp
 * 云媒 流量 充值
 */
@Service
public class YunmeiFlowRequest implements ConnectReqest{

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
        log.info("8、发送流程：发送供应商---云媒流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String key = (String) supplyMap.get("key");
        String MPCode = (String) supplyMap.get("MPCode");
        String CallBackURL = (String) supplyMap.get("callback_url");

        SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
        String OrderDay=df.format(new Date());

        SimpleDateFormat dftimp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String StampTime=dftimp.format(new Date());

        String ProductID = chargeRequest.getPositionCode();//positionCode供应商单位编码
        String Mobile = chargeRequest.getMobile();

        String OrderID = chargeRequest.getChannelNum();//订单号

        String SignMsg=DigestUtils.md5Hex(MPCode+OrderID+Mobile+ProductID+key+StampTime);


        HashMap<String, String> param=new HashMap<String, String>();
        param.put("MPCode", MPCode);
        param.put("OrderDay", OrderDay);
        param.put("OrderID", OrderID);
        param.put("StampTime", StampTime);
        param.put("CallBackURL", CallBackURL);
        param.put("ProductID", ProductID);
        param.put("Mobile", Mobile);
        param.put("SignMsg", SignMsg);

        log.info("8、发送流程：发送供应商---云媒流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        String data="json="+JSON.toJSONString(param);
        String header="application/x-www-form-urlencoded";
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpClients.doPost(host,data,header);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---云媒流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到云媒流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---云媒流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("ResultCode");
        if("6000".equals(respCode)){
            String orderID=OrderID;
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);
            log.info("8、发送流程：发送供应商---云媒流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到云媒流量成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(respCode+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---云媒流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到云媒流量失败！原因："+respCode+"请咨询供应商！");
        }
        return response;
    }
}
