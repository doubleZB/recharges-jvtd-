package com.jtd.recharge.connect.flow.dianxin;

import com.alibaba.fastjson.JSON;
import com.huateng.haobai.ppcore.control.IDispatchControlLocator;
import com.huateng.haobai.ppcore.control.IDispatchControlPortType;
import com.huateng.util.MacUtil;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.XMLUtil;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.xerces.xni.QName;
import org.dom4j.Document;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @autor jipengkun
 * 容联 流量 充值 电信直连
 */
public class DianXinFlowRequest implements ConnectReqest{

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();
    private static Map payAmountMap = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");

        //档位编码与面值映射
        payAmountMap.put("5120","100");
        payAmountMap.put("10240","200");
        payAmountMap.put("30720","500");
        payAmountMap.put("51200","700");
        payAmountMap.put("102400","1000");
        payAmountMap.put("204800","1500");
        payAmountMap.put("512000","3000");
        payAmountMap.put("1048576","5000");

    }


    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {
        long start =System.currentTimeMillis();
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        log.info("8、发送流程：发送供应商---电信直连----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String callbackUrl = (String) supplyMap.get("callback_url");
        String channelNum = chargeRequest.getChannelNum();
        String appDate = DateUtil.Date2String(new Date(),"yyyyMMdd");
        String appTime = DateUtil.Date2String(new Date(),"HHmmss");
        String mobile = chargeRequest.getMobile();
        String positionCode = chargeRequest.getPositionCode();
        String key = "3135383838303334";
        String payAmount = (String)payAmountMap.get(chargeRequest.getPositionCode());

        String head = "A00013|3000030601|V1.0|124.74.76.126";

        String parameners = "<BILLORGID>9999009999019007</BILLORGID>"
                + "<ORDERID>"+channelNum+"</ORDERID>"
                + "<ACCESSTYPE>10030500</ACCESSTYPE>"
                + "<PRODUCTNO>"+mobile+"</PRODUCTNO>"
                + "<PRODUCTTYPE>1</PRODUCTTYPE>"
                + "<PAYAMOUNT>"+payAmount+"</PAYAMOUNT>"
                + "<RECHARGEFLOWAMOUNT>"+positionCode+"</RECHARGEFLOWAMOUNT>"
                + "<ORDERTYPE>1</ORDERTYPE>"
                + "<CALLBACKURL>"+callbackUrl+"</CALLBACKURL>"
                + "<TEXT1></TEXT1><TEXT2></TEXT2><TEXT3></TEXT3><TEXT4></TEXT4><TEXT5></TEXT5>";

        String macContent = MacUtil.genMac(parameners,key);

        String body =
                "<PayPlatRequestParameter>"
                + "<CTRL-INFO "
                + "WEBSVRNAME=\"流量包订购\" "
                + "WEBSVRCODE=\"A00013\" "
                + "APPFROM=\"3000030601\" "
                + "SERNUM="+"\""+ channelNum +"\" "
                + "APPDATE="+"\""+ appDate + "\" "
                + "APPTIME="+"\""+ appTime +"\"" +"/>"
                + "<PARAMETERS>"
                    + parameners +
                 "</PARAMETERS>"
                + "<MAC>"+macContent+"</MAC>"
                + "</PayPlatRequestParameter>";

        log.info("8、发送流程：发送供应商---电信直连----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ head + body);

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            IDispatchControlLocator locator = new IDispatchControlLocator();
            IDispatchControlPortType client =locator.getIDispatchControlHttpPort();
            resultContent = client.dispatchCommand(head,body);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---电信直连----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到电信直连！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---电信直连----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));


        Document document = XMLUtil.parseStringToXml(resultContent);
        String respCode = document.getRootElement().element("RESPONSECODE").getTextTrim();

        if(respCode.equals("000000")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusCode(respCode);
            log.info("8、发送流程：发送供应商---电信直连----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到电信直连成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(respCode+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---电信直连----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到电信直连失败！原因："+respCode+"请咨询供应商！");
        }
        return response;
    }

}
