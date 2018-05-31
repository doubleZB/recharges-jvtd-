package com.jtd.recharge.connect.flow.beijingyidong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bmcc.service.pub.util.Tea;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpClients;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lyp on 2017-01-10.
 */
public class BJYDFlowRequest implements ConnectReqest {

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }


    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception{
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        response.setStatus(ChargeSubmitResponse.Status.FAIL);

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        String header="application/xml";
        String url = (String)supplyMap.get("host");
        Tea tea=new Tea();
        String ChannelID = tea.encryptByTea((String) supplyMap.get("ChannelID"));
        String CA = tea.encryptByTea((String) supplyMap.get("CA"));
        String AdminName = tea.encryptByTea((String) supplyMap.get("AdminName"));
        String Password = tea.encryptByTea((String) supplyMap.get("Password"));
        String PhoneList = tea.encryptByTea(chargeRequest.getMobile());//提交手机号
        String ResName =tea.encryptByTea(chargeRequest.getPositionCode());//资源名称
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String RequestTime = tea.encryptByTea(sdf.format(new Date()));


        String param="<WebRequest>\n" +
                "<Header>\n" +
                "<ChannelID>"+ChannelID+"</ChannelID>\n" +
                "<RequestTime>"+RequestTime+"</RequestTime>\n" +
                "</Header>\n" +
                "<WebBody>\n" +
                "<Params>\n" +
                "<CA>"+CA+"</CA>\n" +
                "<AdminName>"+AdminName+"</AdminName>\n" +
                "<Password>"+Password+"</Password>\n" +
                "<ResName>"+ResName+"</ResName>\n" +
                "<PhoneList>"+PhoneList+"</PhoneList>\n" +
                "</Params>\n" +
                "</WebBody>\n" +
                "</WebRequest>";


        String resultContent = "";
        Service service = new Service();
        Call call;
        try {
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(url);

            call.setOperationName("tf_tfzycx");

            resultContent = (String) call.invoke(new Object[]{param}); // 返回String，传入参数
            log.info("BJYD提交的订单参数："+param);
        } catch (Exception e) {
            log.error("BJYD flow exception；订单参数："+param,e);
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg("提交订单时候，http访问出错"+e.getMessage()+"；订单参数："+JSON.toJSONString(param));
            return response;
        }
        log.info("向BJYD流量发送数据---" + param+" 返回数据---"+resultContent);

        Document document = DocumentHelper.parseText(resultContent);
        Element root = document.getRootElement();
        String RetCode= root.element("Header").elementText("RetCode");
        String RetDesc= root.element("Header").elementText("RetDesc");

       response.setStatusMsg(resultContent);
        if(RetCode.equals("0")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            String OrderId= root.element("WebBody").element("RetInfo").elementText("OrderId");
            response.setChannelNum(OrderId);
            response.setSupplyChannelNum(OrderId);
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(RetCode);
            response.setStatusMsg(RetDesc);
        }
        return response;
    }
}
