package com.jtd.recharge.connect.flow.chuanglan;

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
 * Created by wxp on 2017-01-10.
 * 创蓝流量
 */
public class ChuanglanFlowRequest implements ConnectReqest {

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }


    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        long start =System.currentTimeMillis();
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        response.setStatus(ChargeSubmitResponse.Status.FAIL);
        log.info("8、发送流程：发送供应商---创蓝流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String url = (String)supplyMap.get("host");
        String account = (String) supplyMap.get("account");
        String key = (String) supplyMap.get("key");
        String timestamp = (System.currentTimeMillis()/1000)+"";
        String noncestr = RandomStringUtils.randomAlphabetic(6);
        String mobile = chargeRequest.getMobile();
        String code =chargeRequest.getPositionCode();
        String ext_id = chargeRequest.getChannelNum();
        String signature = DigestUtils.sha1Hex(
                        "account="+account +
                        "&ext_id="+ext_id+
                        "&mobile="+mobile+
                        "&noncestr="+noncestr+
                        "&package="+code+
                        "&timestamp="+timestamp+
                        "&key="+key);

        Map param = new HashMap();
        param.put("account",account);
        param.put("timestamp",timestamp);
        param.put("noncestr",noncestr);
        param.put("mobile",mobile);
        param.put("package",code);
        param.put("signature",signature);
        param.put("ext_id",ext_id);

        String resultContent = "";
        log.info("8、发送流程：发送供应商---创蓝流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        try {
            resultContent = HttpTookit.doPost(url,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---创蓝流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到创蓝流量！原因："+ e.getLocalizedMessage());
            response.setStatusMsg("提交订单时候，http访问出错"+e.getMessage()+"；订单参数："+JSON.toJSONString(param));
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：向创蓝流量发送数据-mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String statusCode = object.getString("code");
        String desc = object.getString("desc");
        String orderNum = object.getString("ext_id");
        response.setStatusMsg(resultContent);
        if(statusCode.equals("0")){
            response.setStatusMsg(desc);
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderNum);
            response.setSupplyChannelNum(orderNum);
            log.info("8、发送流程：发送供应商---创蓝流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到创蓝流量成功！");
        }
        return response;
    }
}
