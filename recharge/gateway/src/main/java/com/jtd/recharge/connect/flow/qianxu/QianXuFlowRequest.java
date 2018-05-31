package com.jtd.recharge.connect.flow.qianxu;

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
 * @autor lhm 2017/10/11.
 * 千煦流量 充值
 */
@Service
public class QianXuFlowRequest implements ConnectReqest{

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
        log.info("8、发送流程：发送供应商---千煦流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String cid = (String) supplyMap.get("cid");
        String key = (String) supplyMap.get("key");
        String clecs = (String) supplyMap.get("clecs");
        String callbackurl = (String) supplyMap.get("callback_url");

        String phone= chargeRequest.getMobile();
        String app_id = chargeRequest.getPositionCode();

        long timestamp = System.currentTimeMillis()/1000;
        String order_id = chargeRequest.getChannelNum();
        String sign= DigestUtils.md5Hex("app_id="+app_id+"&cid="+cid+"&phone="+phone+"&key="+key);


        Map param = new HashMap();
        param.put("app_id",app_id);
        param.put("clecs",clecs);
        param.put("cid",cid);
        param.put("phone",phone);
        param.put("order_id",order_id);
        param.put("callbackurl",callbackurl);
        param.put("timestamp",Long.toString(timestamp));
        param.put("sign",sign);

        log.info("8、发送流程：发送供应商---千煦流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doGet(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---千煦流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到千煦流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---千煦流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("ResultCode");
        String message=object.getString("ResultDesc");
        if(respCode.equals("0")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(order_id);
            log.info("8、发送流程：发送供应商---千煦流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到千煦流量成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(message+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---千煦流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到千煦流量失败！原因："+message+"请咨询供应商！");
        }
        return response;
    }
}
