package com.jtd.recharge.connect.flow.hongqiekeji;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lhm on 2017/10/10.
 * 四川红茄科技
 */
@Service
public class HongQieKeJiFlowRequest implements ConnectReqest {
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
        log.info("8、发送流程：发送供应商---四川红茄科技----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String goodstype = (String) supplyMap.get("goodstype");
        String key = (String) supplyMap.get("key");
        String callbackurl = (String) supplyMap.get("callback_url");
        String userid = (String) supplyMap.get("userid");
        String method = (String) supplyMap.get("action");

        long timestamp = System.currentTimeMillis()/1000;
        String phone = chargeRequest.getMobile();
        String num = chargeRequest.getPositionCode();
        String userorderid = chargeRequest.getChannelNum();

        Map<String, Object> map = new HashMap();
        map.put("num", num);
        map.put("goodstype", goodstype);
        map.put("userorderid", userorderid);
        map.put("phone", phone);
        map.put("callbackurl", callbackurl);

        String ss=key+"method"+method+"timestamp"+timestamp+"userid"+userid+JSON.toJSONString(map)+key;

        log.info("8、发送流程：发送供应商---四川红茄科技----ss "+ss);

        String sign=DigestUtils.md5Hex(ss);

        log.info("8、发送流程：发送供应商---四川红茄科技----sign "+sign);

        String data="timestamp="+timestamp+"&sign="+sign+"&userid="+userid+"&method="+method;

        log.info("8、发送流程：发送供应商---四川红茄科技----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ host+data+JSON.toJSONString(map));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host+data,JSON.toJSONString(map));
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---四川红茄科技----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到四川红茄科技！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---四川红茄科技----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String ret_code=object.getString("status");
        String errMsg=object.getString("message");
        if(ret_code.equals("2")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(userorderid);
            log.info("8、发送流程：发送供应商---四川红茄科技----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到四川红茄科技成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(ret_code);
            response.setStatusMsg(errMsg+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---四川红茄科技----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到四川红茄科技失败！原因："+errMsg+"请咨询供应商！");
        }
        return response;
    }

}
