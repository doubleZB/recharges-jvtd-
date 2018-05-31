package com.jtd.recharge.connect.telbill.yuanmai;

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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @autor jipengkun
 * 元迈话费
 */
public class YuanMaiBillRequest implements ConnectReqest {

    private Log log = LogFactory.getLog(this.getClass());

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商---元迈话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());


        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String dealerid = (String) supplyMap.get("dealerid");
        String dealerkey = (String) supplyMap.get("dealerkey");
        String callbackUrl = (String) supplyMap.get("callback_url");

        String host = (String) supplyMap.get("host");
        String phoneno = chargeRequest.getMobile();
        String orderid = chargeRequest.getChannelNum();
        String amount = chargeRequest.getPositionCode();
        String ordertime = DateUtil.Date2String(new Date(), "yyyyMMddHHmmss");
        String mark = "mark";
        String ispid = "";
        String signContent = "ispid="+ispid+"&amount=" + amount + "&dealerid=" + dealerid + "&orderid=" + orderid + "&photonum="
                + phoneno + "&ordertime=" + ordertime + "&mark="+mark+"&dealerkey=" + dealerkey;
        String sign = DigestUtils.md5Hex(signContent);


        Map param = new HashMap();
        param.put("ispid",ispid);
        param.put("amount", amount);
        param.put("dealerid", dealerid);
        param.put("orderid", orderid);
        param.put("photonum", phoneno);
        param.put("backurl", callbackUrl);
        param.put("ordertime", ordertime);
        param.put("mark",mark);
        param.put("sign",sign);
        log.info("8、发送流程：发送供应商---元迈话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();

        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host, param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---元迈话费----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到元迈话费！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            return response;
        }
        log.info("8、发送流程：发送供应商---元迈话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        String serialid = "";//元迈话费平台订单号
        String orderamount = "";//订单金额
        String resultno = "";//resultno=0000时，并不代理该订单充值成功，而是表示该订单已经成功提交到元迈话费平台

        /**
         * 解析提交结果
         */
        Document doc = XMLUtil.parseStringToXml(resultContent);
        Element item = doc.getRootElement().element("items");

        List<Element> elements = item.elements();//当前节点的所有属性的list
        for (int i = 0; i < elements.size(); i++) {//遍历当前节点的所有属性
            Element element = elements.get(i);

            if("serialid".equals(element.attribute("name").getStringValue())) {
                serialid = element.attribute("value").getStringValue();
                log.info("serialid---" + serialid);
            }
            if("resultno".equals(element.attribute("name").getStringValue())) {
                resultno = element.attribute("value").getStringValue();
                log.info("resultno---" + resultno);
            }
        }

        if ("0000".equals(resultno)) {

            response.setStatusCode(resultno);
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            log.info("8、发送流程：发送供应商---元迈话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到云元迈话费成功！");

        } else {
            response.setStatusCode(resultno);
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(resultno+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---元迈话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到云元迈话费失败！原因："+resultno+"请咨询供应商！");
        }

        return response;
    }

    public static void main(String[] args) {
        /*String xml = "<?xml version=\"1.0\" encoding=\"GB2312\" ?>" +
                "<fill version=\"1.0\">" +
                "<items>" +
                "<item name=\"ispid\" value=\"0\" />" +
                "<item name=\"orderid\" value=\"123456789\" />" +
                "<item name=\"serialid\" value=\"987654321\" />" +
                "<item name=\"orderamount\" value=\"98.76\" />" +
                "<item name=\"balance\" value=\"33043.65\"/>" +
                "<item name=\"resultno\" value=\"0000\" />" +
                "<item name=\"mark\" value=\"\" />" +
                "<item name=\"sign\" value=\"192ae0099f46de1d71228a5fa00c2d2e\" />" +
                "</items>" +
                "</fill>";



        String serialid = "";//元迈话费平台订单号
        String orderamount = "";//订单金额
        String resultno = "";//resultno=0000时，并不代理该订单充值成功，而是表示该订单已经成功提交到元迈话费平台

        Document doc = XMLUtil.parseStringToXml(xml);
        Element item = doc.getRootElement().element("items");

        List<Element> elements = item.elements();//当前节点的所有属性的list
        for (int i = 0; i < elements.size(); i++) {//遍历当前节点的所有属性
            Element element = elements.get(i);
            //System.out.println(element.attribute("name").getStringValue());
            //System.out.println(element.attribute("value").getStringValue());

            if("serialid".equals(element.attribute("name").getStringValue())) {
                serialid = element.attribute("value").getStringValue();
                System.out.println("serialid---" + serialid);
            }
            if("resultno".equals(element.attribute("name").getStringValue())) {
                resultno = element.attribute("value").getStringValue();
                System.out.println("resultno---" + resultno);
            }
        }
*/

    }
}
