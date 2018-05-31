package com.jtd.recharge.connect.flow.zuowanghubeiyidong;

import com.alibaba.fastjson.JSON;
import com.jtd.recharge.base.cache.redis.RedisTemplate;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.SmsUtil;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import com.jtd.recharge.connect.base.ConnectReqestList;
import com.jtd.recharge.dao.mapper.ChargeOrderMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
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

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * @autor lhm
 * 卓望湖北移动 流量 充值
 */
@Service
public class ZuoWangHuBeiYiDongFlowRequest implements ConnectReqestList{

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(List<ChargeRequest> chargeRequestList) throws Exception{
        String supplyName = chargeRequestList.get(0).getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        long start =System.currentTimeMillis();

        //准备供应商参数
        String host = (String) supplyMap.get("host");
        String BIPCode = (String)supplyMap.get("BIPCode");
        String product_id = (String)supplyMap.get("product_id");
        String ActivityCode = (String)supplyMap.get("ActivityCode");
        String Appkey=(String)supplyMap.get("Appkey");
        String AppSecret=(String)supplyMap.get("AppSecret");
        String TRANSIDO=(String)supplyMap.get("TRANSIDO");
        /*String SessionID = String.valueOf(System.currentTimeMillis());*/
        String SessionID=chargeRequestList.get(0).getChannelNum();
        String TransIDOTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        String Sequence =RandomStringUtils.randomNumeric(12);
        String TransIDO =TRANSIDO+TransIDOTime+Sequence;

        String Version = "0100";
        String TestFlag = "0"; //测试为1,生产为0
        String ActionCode = "0"; // 0 请求 1 应答
        String RouteType = "00";
        String OrigDomain = "DOMS"; // 发起方应用域代码 由BBOSS分配
        String HomeDomain = "BBSS"; // 归属方应用域代码 由BBOSS分配
        String RouteValue = "998"; // 路由类型对应的关键值，由BBOSS分配


        int nponce= SmsUtil.getRandNum(0,4);
        String created = DateUtil.dateTz();
        String passwordDigest= Base64.encodeBase64String(getSHA256(nponce + created + AppSecret).getBytes());
        String nponces=Base64.encodeBase64String(String.valueOf(nponce).getBytes());
        //鉴权
        String auth = "WSSE realm=\"DOMS\", profile=\"UsernameToken\", type=\"AppKey\"";
        String wsse = "UsernameToken Username=\"" + Appkey + "\", PasswordDigest=\"" + passwordDigest + "\", Nonce=\"" + nponces
                + "\", Created=\"" + created + "\"";

        log.info("Authorization:" + auth + " X-WSSE: " + wsse);
        CloseableHttpClient httpclient = org.apache.http.impl.client.HttpClients.createDefault();
        log.info("发送流程----卓望鉴权执行时长--*******："+(System.currentTimeMillis()-start));
        long start2 =System.currentTimeMillis();
        StringBuffer xmlHeaderBuffer = new StringBuffer();
        StringBuffer xmlDateBuffer = new StringBuffer();

        xmlHeaderBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlHeaderBuffer.append("<InterBOSS>");
        xmlHeaderBuffer.append("<Version>").append(Version).append("</Version>");
        xmlHeaderBuffer.append("<TestFlag>").append(TestFlag).append("</TestFlag>");
        xmlHeaderBuffer.append("<BIPType>");
        xmlHeaderBuffer.append("<BIPCode>").append(BIPCode).append("</BIPCode>");
        xmlHeaderBuffer.append("<ActivityCode>").append(ActivityCode).append("</ActivityCode>");
        xmlHeaderBuffer.append("<ActionCode>").append(ActionCode).append("</ActionCode>");
        xmlHeaderBuffer.append("</BIPType>");
        xmlHeaderBuffer.append("<RoutingInfo>");
        xmlHeaderBuffer.append("<OrigDomain>").append(OrigDomain).append("</OrigDomain>");
        xmlHeaderBuffer.append("<RouteType>").append(RouteType).append("</RouteType>");
        xmlHeaderBuffer.append("<Routing>");
        xmlHeaderBuffer.append("<HomeDomain>").append(HomeDomain).append("</HomeDomain>");
        xmlHeaderBuffer.append("<RouteValue>").append(RouteValue).append("</RouteValue>");
        xmlHeaderBuffer.append("</Routing>");
        xmlHeaderBuffer.append("</RoutingInfo>");
        xmlHeaderBuffer.append("<TransInfo>");
        xmlHeaderBuffer.append("<SessionID>").append(SessionID).append("</SessionID>");
        xmlHeaderBuffer.append("<TransIDO>").append(TransIDO).append("</TransIDO>");
        xmlHeaderBuffer.append("<TransIDOTime>").append(TransIDOTime).append("</TransIDOTime>");
        xmlHeaderBuffer.append("</TransInfo>");
        xmlHeaderBuffer.append("</InterBOSS>");

        xmlDateBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlDateBuffer.append("<InterBOSS>").append("<SvcCont>").append("<![CDATA[");
        xmlDateBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlDateBuffer.append("<UserInfo>");
        xmlDateBuffer.append("<ProductID>").append(product_id).append("</ProductID>");
        for(int i=0;i<chargeRequestList.size();i++) {
            ChargeRequest chargeRequest=chargeRequestList.get(i);
            xmlDateBuffer.append("<UserData>");
            xmlDateBuffer.append("<MobNum>").append(chargeRequest.getMobile()).append("</MobNum>");
            xmlDateBuffer.append("<OprCode>").append("01").append("</OprCode>");
            xmlDateBuffer.append("<UsageLimit>").append(chargeRequest.getPositionCode()).append("</UsageLimit>");
            xmlDateBuffer.append("<ValidMonths>").append(1).append("</ValidMonths>");
            xmlDateBuffer.append("</UserData>");
        }
        xmlDateBuffer.append("<EffRule>").append("0").append("</EffRule>");
        xmlDateBuffer.append("</UserInfo>").append("]]></SvcCont>");
        xmlDateBuffer.append("</InterBOSS>");

        String headerStr = xmlHeaderBuffer.toString();
        String bodyStr = xmlDateBuffer.toString();
        log.info("发送流程----卓望拼接报文执行时长--*******："+(System.currentTimeMillis()-start2));
        log.info("8、发送流程---发送供应商---卓望湖北移动-- 封装应用参数json报文请求参数---" +JSON.toJSONString(headerStr+bodyStr));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String inContent = "";
        InputStream inputStream  = null;
        try {
            long start1 =System.currentTimeMillis();
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setConnectionRequestTimeout(3000).setSocketTimeout(3000).build();
            HttpPost httppost = new HttpPost(host);
            httppost.setConfig(requestConfig);
            StringBody header = new StringBody(headerStr);
            StringBody body = new StringBody(bodyStr);
            // 应答内容
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("xmlhead", header).addPart("xmlbody", body)
                    .build();
            httppost.setEntity(reqEntity);
            httppost.setHeader("Authorization", auth);
            httppost.setHeader("X-WSSE", wsse);
            httppost.setHeader("PRODID", DigestUtils.md5Hex(product_id));
            httppost.setHeader("ACTCODE", ActivityCode);
            CloseableHttpResponse responseTwo = httpclient.execute(httppost);
            HttpEntity resEntity = responseTwo.getEntity();
            if (resEntity != null) {
                inputStream = resEntity.getContent();
                resEntity.getContentLength();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                String result="";
                while((line = bufferedReader.readLine()) != null)
                {
                    result = result + line;
                }
                inContent=new String(result.getBytes("iso8859-1"), "GBK");
                inputStream.close();
                bufferedReader.close();
            }
            httpclient.close();
            responseTwo.close();
            log.info("8、发送流程----  卓望发送请求到返回耗时--*******："+(System.currentTimeMillis()-start1));
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---卓望湖北移动--- 提交到卓望湖北移动！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(SessionID);
            response.setSupplyChannelNum(TransIDO);
            response.setSupplyOrderNum(TransIDO);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            httpclient.close();
            return response;
        }
        log.info("8、发送流程----  发送供应商---卓望湖北移动封装应用参数json报文请求参数---" +JSON.toJSONString(bodyStr)+"   ------返回数据：" + inContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));
        if(!"".equals(inContent)&&inContent!=null) {
            String Response=  "<" + inContent.substring(inContent.indexOf("<Response>") + 1, inContent.indexOf("</Response>")) + "</Response>";
            log.info("Response："+Response);
            Document document = DocumentHelper.parseText(Response);
            Element root = document.getRootElement();
            String resCode = root.elementText("RspCode");
            if("0000".equals(resCode)){
                response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
                response.setChannelNum(SessionID);
                response.setSupplyChannelNum(TransIDO);
                response.setSupplyOrderNum(TransIDO);
                log.info("8、发送流程：发送供应商---卓望湖北移动---- 提交到湖北移动成功！");
            }else{
                response.setStatus(ChargeSubmitResponse.Status.FAIL);
                response.setStatusCode(resCode);
                response.setStatusMsg(resCode+"请咨询供应商！");
                log.info("8、发送流程：发送供应商---卓望湖北移动---- 提交到卓望湖北移动失败！原因："+resCode+"请咨询供应商！");

            }
        }else{
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(SessionID);
            response.setSupplyChannelNum(TransIDO);
            response.setSupplyOrderNum(TransIDO);
            log.info("8、发送流程：发送供应商---卓望湖北移动---- 提交到湖北移动成功！");
        }
        log.info("返回内容："+JSON.toJSONString(response));
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
