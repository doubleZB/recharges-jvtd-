package com.jtd.recharge.connect.flow.ximang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 西芒流量 on 2018/3/7
 * lhm
 */
@Service
public class XiMangFlowRequest implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        log.info("8、发送流程--发送供应商---西芒流量---mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());
        long start =System.currentTimeMillis();

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String host = (String) supplyMap.get("host");
        String custInteId = (String) supplyMap.get("custInteId");
        String secretKey = (String) supplyMap.get("secretKey");
        String orderType = (String) supplyMap.get("orderType");
        String effectType = (String) supplyMap.get("effectType");
        String mobile = chargeRequest.getMobile();
        String packCode = chargeRequest.getPositionCode();
        String orderId = chargeRequest.getChannelNum();
        String echo = String.valueOf(System.currentTimeMillis());
        int version =1;
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        MessageDigest md5 = MessageDigest.getInstance("md5");
        BASE64Encoder encoder = new BASE64Encoder();
        String chargeSign = encoder.encode(md5.digest((custInteId+orderId+secretKey+echo+timestamp).getBytes("UTF-8")));

        //封装参数
        String param = "<request>\n" +
                "<head>\n" +
                "<custInteId>"+custInteId+"</custInteId>\n" +
                "<echo>"+echo+"</echo>\n" +
                "<orderId>"+orderId+"</orderId>\n" +
                "<timestamp>"+timestamp+"</timestamp>\n" +
                "<orderType>"+orderType+"</orderType>\n" +
                "<version>"+version+"</version>\n" +
                "<chargeSign>"+chargeSign+"</chargeSign>\n" +
                "</head>\n" +
                "<body>\n" +
                "<item>\n" +
                "<packCode>"+packCode+"</packCode>\n" +
                "<mobile>"+mobile+"</mobile>\n" +
                "<effectType>"+effectType+"</effectType>\n" +
                "</item>\n" +
                "</body>\n" +
                "</request>";

        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---西芒流量； 封装应用参数json报文请求参数---" +JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---西芒流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到西芒流量流量！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程：发送供应商---西芒流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        Document document = DocumentHelper.parseText(resultContent);
        Element root = document.getRootElement();
        log.info("成功接收返回数据========="+root);
        String respCode = root.elementText("result");
        String desc = root.elementText("desc");
        if(respCode.equals("0000")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderId);
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到西芒流量成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(desc);
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到西芒流量失败！原因："+desc);
        }
        return response;
    }

}
