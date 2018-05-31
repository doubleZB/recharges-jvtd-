package com.jtd.recharge.connect.flow.neimengyidong;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpClients;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @autor lhm
 * 内蒙移动 流量 充值
 */
@Service
public class NeiMengFlowRequest implements ConnectReqest{

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception{
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        long start =System.currentTimeMillis();
        log.info("8、发送流程---发送供应商---内蒙移动--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        //准备供应商参数
        String authHost = (String) supplyMap.get("authHost");
        String host = (String) supplyMap.get("host");
        String AppKey = (String)supplyMap.get("AppKey");
        String AppSecret = (String)supplyMap.get("AppSecret");
        String dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .format(new Date())+"+08:00";

        String serialNum =chargeRequest.getChannelNum() ;
        String mobile = chargeRequest.getMobile();
        String productId = chargeRequest.getPositionCode();

        String sign = getSHA256(AppKey+dateTime+AppSecret);
        //token参数
        String param="<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
                "<Request>\n" +
                "<Datetime>"+dateTime+"</Datetime>\n" +
                "<Authorization>\n" +
                "<AppKey>"+AppKey+"</AppKey>\n" +
                "<Sign>"+sign+"</Sign>\n" +
                "</Authorization>\n" +
                "</Request>";

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContentToken = "";
        try {
            resultContentToken = HttpClients.doPost(authHost,param,"application/xml");
        } catch (Exception e) {
            log.error("8、认证发送异常：发送供应商---内蒙移动----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到内蒙移动！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、认证发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---内蒙移动封装应用参数json报文请求参数---" +param+"   ------返回数据：" + resultContentToken+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));

        Document document = DocumentHelper.parseText(resultContentToken);
        Element root = document.getRootElement();
        String token = root.element("Authorization").elementText("Token");

        String paramBody ="<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
        "<Request>\n" +
        "<Datetime>"+dateTime+"</Datetime>\n" +
        "<ChargeData>\n" +
        "<Mobile>"+mobile+"</Mobile>\n" +
        "<ProductId>"+productId+"</ProductId>\n" +
        "<SerialNum>"+serialNum+"</SerialNum>\n" +
        "</ChargeData>\n" +
        "</Request>";

       String Signature = getSHA256(paramBody+AppSecret);

        String getSignature = getSHA256(AppSecret);
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,paramBody,"application/xml",token,Signature);
        } catch (Exception e) {
            log.error("8、请求发送异常：发送供应商---内蒙移动----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到内蒙移动！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、请求发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---内蒙移动返回数据：" + resultContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));

        Document documentResult = DocumentHelper.parseText(resultContent);
        Element rootResult = documentResult.getRootElement();
        String SystemNum = rootResult.element("ChargeData").elementText("SystemNum");
        String hostSelect = "http://www.nm.10086.cn/flowplat/chargeRecords/"+SystemNum+".html";
        String resultContentAll = "";
        try {
            resultContentAll = HttpTookit.doGetParams(hostSelect,null,token,getSignature,"UTF-8");
        } catch (Exception e) {
            log.error("8、查询发送异常：发送供应商---内蒙移动----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到内蒙移动！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、查询发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---内蒙移动返回数据：" + resultContentAll+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));

        Document documentSelectResult = DocumentHelper.parseText(resultContentAll);
        Element rootSelectResult = documentSelectResult.getRootElement();

        String Status = rootSelectResult.element("Records").element("Record").elementText("Status");
        String Description = rootSelectResult.element("Records").element("Record").elementText("Description");

        if(Status.equals("3")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(serialNum);
            ChargeReport chargeReport = new ChargeReport();
            chargeReport.setChannelNum(serialNum);
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("8.内蒙移动提交成功添加回执消息队列mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum() + JSON.toJSONString(chargeReport));
            MNSClient client = MessageClient.getClient();
            CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

            Message message = new Message();
            message.setMessageBody(JSON.toJSONString(chargeReport));
            Message putMsg = queue.putMessage(message);
            log.info("10.内蒙移动发送Send message id is: " + putMsg.getMessageId());
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(Status);
            response.setStatusMsg(Description+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---内蒙移动----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到内蒙移动失败！原因："+Description+"请咨询供应商！");
        }
        return response;
    }



    public static String getSHA256(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}
