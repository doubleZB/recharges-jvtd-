package com.jtd.recharge.connect.telbill.sirui;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.XMLUtil;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.SimpleFormatter;

/**
 * Created by lhm on 2017/7/13.
 * 思锐话费
 */
public class SiRuiTelBilRequest implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());


    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }


    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商---思锐话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String url = (String) supplyMap.get("host");

        String username = (String) supplyMap.get("appId");
        String orderid = chargeRequest.getChannelNum();
        String mobile = chargeRequest.getMobile();
        String price = chargeRequest.getPositionCode();//充值金额
        String action = (String) supplyMap.get("action");
        String apikey = (String) supplyMap.get("apikey");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp=formatter.format(new java.util.Date());

        String sign = DigestUtils.md5Hex("apikey="+apikey +"&mobile="+mobile+"&orderid="+orderid+"&package="+price+"&timestamp="+timestamp+"&username="+username);

        Map param = new HashMap();
        param.put("action",action);
        param.put("username",username);
        param.put("mobile",mobile);
        param.put("package",price);
        param.put("orderid",orderid);
        param.put("timestamp",timestamp);
        param.put("sign",sign);

        log.info("8、发送流程：发送供应商---思锐话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        response.setStatus(ChargeSubmitResponse.Status.FAIL);
        try {
            String resultContent = HttpTookit.doPost(url,param);

            log.info("8、发送流程：发送供应商---思锐话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                    (System.currentTimeMillis()-start));

            JSONObject json= JSON.parseObject(resultContent);
            String code = json.getString("code");
            String message = json.getString("message");
            if("1".equals(code)){
                response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
                response.setChannelNum(orderid);
                log.info("8、发送流程：发送供应商---思锐话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到思锐话费成功！");
            }else{
                response.setStatusCode(code);
                response.setStatusMsg(message+"请咨询供应商！");
                log.info("8、发送流程：发送供应商---思锐话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到思锐话费失败！原因："+message+"请咨询供应商！");
            }
        }catch (Exception e){
            log.error("8.思锐话费发送数据 异常exception:***"+e.getMessage(),e);
            response.setStatusMsg("思锐话费发送数据 exception:***"+e.getMessage());
            return response;
        }
        return response;
    }
}
