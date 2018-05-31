package com.jtd.recharge.connect.flow.xuancai;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.icu.text.SimpleDateFormat;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.SmsUtil;
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
 * Created by lhm on 2017/9/04.
 * 炫彩流量
 */
@Service
public class XuanCaiFlowRequest implements ConnectReqest {
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
        log.info("8、发送流程：发送供应商---炫彩流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");//充值地址
        String key = (String) supplyMap.get("key");
        String channel_code = (String) supplyMap.get("channel_code");
        String phone = chargeRequest.getMobile();//手机号码
        String pkg_id = chargeRequest.getPositionCode();
        String pkg_name = chargeRequest.getPositionCode();
        String timestamp =String.valueOf(System.currentTimeMillis());

        String date  = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int randNum = (int)((Math.random()*9+1)*100000);
        String req_id = date+randNum;//订单号
        String orderId=chargeRequest.getChannelNum();
        String md5= DigestUtils.md5Hex(req_id+phone+pkg_id+channel_code + timestamp + key);//加密串

        Map param = new HashMap();
        param.put("req_id",req_id);
        param.put("phone",phone);
        param.put("pkg_id",pkg_id);
        param.put("channel_code",channel_code);
        param.put("pkg_name",pkg_name);
        param.put("timestamp",timestamp);
        param.put("md5",md5);

        log.info("8、发送流程：发送供应商---炫彩流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doGet(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---炫彩流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到炫彩流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---炫彩流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object= JSON.parseObject(resultContent);
        String errorCode=object.getString("code");
        String massage=object.getString("text");
        if(errorCode.equals("0")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderId);
            response.setSupplyChannelNum(req_id);
            log.info("8、发送流程：发送供应商---炫彩流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到炫彩流量成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(errorCode);
            response.setStatusMsg(massage+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---炫彩流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到炫彩流量失败！原因："+massage+"请咨询供应商！");
        }
        return response;
    }
}
