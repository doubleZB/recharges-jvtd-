package com.jtd.recharge.connect.flow.dingchikeji;

import com.alibaba.fastjson.JSON;
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
import java.util.Properties;

/**
 * Created by liyabin on 2017/9/19.
 */
public class DingChiKeJiFlowRequest  implements ConnectReqest {

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
        log.info("8、发送流程：发送供应商---鼎驰流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String userId = (String) supplyMap.get("userId");
        String key = (String) supplyMap.get("key");



        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
        String reqTime= sdf.format(new Date());
        String date=reqTime+chargeRequest.getPositionCode()+chargeRequest.getChannelNum()+chargeRequest.getMobile()+userId;
        String sign  = DigestUtils.md5Hex(date+key);
        HashMap<String , String> map=new HashMap<String,String>();
        map.put("userId",userId);
        map.put("itemId",chargeRequest.getPositionCode());
        map.put("uid",chargeRequest.getMobile());
        map.put("serialno",chargeRequest.getChannelNum());
        map.put("dtCreate",reqTime);
        map.put("sign",sign);



        log.info("8、发送流程：发送供应商---鼎驰流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(map));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doGet(host,map);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---鼎驰流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到鼎驰流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---鼎驰流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));
        Document document = XMLUtil.parseStringToXml(resultContent);
        Element root = document.getRootElement();
        String errorCode = root.elementText("code");
        String desc = root.elementText("desc");
        if(errorCode.equals("00")){
            String orderID =root.elementText("serialno");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);
            log.info("8、发送流程：发送供应商---鼎驰流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到鼎驰流量成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(errorCode);
            response.setStatusMsg(desc);
            log.info("8、发送流程：发送供应商---鼎驰流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到鼎驰流量失败！原因："+desc);
        }
        return response;
    }

}
