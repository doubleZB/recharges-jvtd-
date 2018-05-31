package com.jtd.recharge.connect.telbill.mixin;

import com.alibaba.fastjson.JSON;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 米鑫话费发送接口
 * Created by 王相平 on 2017-06-21.
 */
public class MiXinTelBilRequest  implements ConnectReqest {

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
        log.info("8、发送流程：发送供应商---米鑫话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String url = (String) supplyMap.get("url");

        String userid = (String) supplyMap.get("userid");
        String sporderid = chargeRequest.getChannelNum();
        String mobile = chargeRequest.getMobile();
        String price = chargeRequest.getPositionCode();//充值金额
        String chargetype = (String) supplyMap.get("chargetype");
        String back_url = (String) supplyMap.get("back_url");
        String key = (String) supplyMap.get("key");
        String sign = DigestUtils.md5Hex(userid + sporderid + mobile + price + chargetype+ back_url+key);
        String spordertime = DateUtil.Date2String(new Date(),DateUtil.SQL_TIME_FMT);

        Map param = new HashMap();
        param.put("userid",userid);
        param.put("sporderid",sporderid);
        param.put("mobile",mobile);
        param.put("price",price);
        param.put("chargetype",chargetype);
        param.put("back_url",back_url);
        param.put("sign",sign);
        param.put("spordertime",spordertime);

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        response.setStatus(ChargeSubmitResponse.Status.FAIL);
        try {
            String resultContent = HttpTookit.doPost(url,param);
            log.info("8、发送流程：发送供应商---米鑫话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                    (System.currentTimeMillis()-start));

            Document document = XMLUtil.parseStringToXml(resultContent);
            if(document==null){
                throw new Exception("8.米鑫话费响应xml解析异常，返回的内容是----"+resultContent);
            }
            Element order = document.getRootElement();
            String result = order.element("result").getText();
            String status  = order.element("status").getText();
            String desc   = order.element("desc").getText();
            if("0000".equals(result)){
                response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            }
            response.setStatusCode(result);
            response.setChannelNum(sporderid);
            response.setStatusMsg(desc);
        }catch (Exception e){
            log.error("8.米鑫话费发送数据 异常exception:***"+e.getMessage(),e);
            response.setStatusMsg("米鑫话费发送数据 exception:***"+e.getMessage());
            return response;
        }
        return response;
    }
}
