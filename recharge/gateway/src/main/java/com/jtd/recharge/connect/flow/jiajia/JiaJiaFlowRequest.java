package com.jtd.recharge.connect.flow.jiajia;

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
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liyabin on 2017/9/5.
 */
@Service
public class JiaJiaFlowRequest implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        log.info("8、发送流程--发送供应商---加加---mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());
        long start =System.currentTimeMillis();

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String host = (String) supplyMap.get("host");
        String us = (String) supplyMap.get("us");
        String apiKey = (String) supplyMap.get("AppKey");
        String notify=(String) supplyMap.get("callback_url");
        String type=(String) supplyMap.get("type");
        String phone = chargeRequest.getMobile();
        String flow = chargeRequest.getPositionCode();
        String thirdSeq = chargeRequest.getChannelNum();
        String ts = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        String parms="phone="+phone+"&flow="+flow+"&type="+type+"&notify="+notify+"&pass="+thirdSeq;
        String parm=AES.encrypt(parms,apiKey,apiKey);
        String sig= DigestUtils.md5Hex(us + "||" + phone + "||" + flow + "||" + type + "||" + ts+ "||"+apiKey);

        Map param = new HashMap();
        param.put("us",us);
        param.put("parm",parm);
        param.put("ts",ts);
        param.put("sig",sig);
        param.put("thirdSeq",thirdSeq);

        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---加加； 封装应用参数json报文请求参数---" + JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---加加----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到加加流量！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            return response;
        }
        log.info("8、发送流程：发送供应商---加加----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("result");
        String Message=object.getString("msg");
        if("0".equals(respCode)){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(thirdSeq);
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到加加成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(Message);
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到加加失败！原因："+Message);
        }
        return response;
    }
}
