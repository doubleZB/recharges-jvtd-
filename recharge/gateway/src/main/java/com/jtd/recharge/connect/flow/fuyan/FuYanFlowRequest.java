package com.jtd.recharge.connect.flow.fuyan;

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
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @autor jipengkun
 * 微网 流量 充值
 */
@Service
public class FuYanFlowRequest implements ConnectReqest{

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
        log.info("8、发送流程：发送供应商--fy----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String key =(String) supplyMap.get("apiKey");
        String userName =(String) supplyMap.get("userName");
        String password =(String) supplyMap.get("password");
        String phone = chargeRequest.getMobile();
        String orderid = chargeRequest.getChannelNum();
        String echostr = RandomStringUtils.randomNumeric(12);
        String product=chargeRequest.getPositionCode();
        SimpleDateFormat dftimp=new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp=dftimp.format(new Date());
        String type="1";
        //签名
        String sign= DigestUtils.md5Hex(userName+password+echostr+orderid+timestamp+phone+type+product+key);

        Map<String,String> map = new HashMap<String,String>();
        map.put("username", userName);
        map.put("password", password);
        map.put("echostr", echostr);
        map.put("timestamp", timestamp);
        map.put("phone", phone);
        map.put("type", type);
        map.put("product", product);
        map.put("sign", sign);
        map.put("orderid",orderid);

        log.info("8、发送流程：发送供应商--fy----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(map));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,map);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商--fy----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到微网！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程：发送供应商--fy----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("code");
        if("000000".equals(respCode)){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
            log.info("8、发送流程：发送供应商--fy----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到微网成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setChannelNum(chargeRequest.getChannelNum());
            response.setStatusMsg(respCode+"请咨询供应商！");
            response.setStatusCode(respCode);
            log.info("8、发送流程：发送供应商--fy----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到微网失败！原因："+respCode+"请咨询供应商！");
        }
        return response;
    }
}
