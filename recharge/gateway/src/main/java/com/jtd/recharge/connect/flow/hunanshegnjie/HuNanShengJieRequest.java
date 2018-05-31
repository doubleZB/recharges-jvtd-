package com.jtd.recharge.connect.flow.hunanshegnjie;

import com.alibaba.fastjson.JSON;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpClients;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import com.jtd.recharge.dao.bean.util.Md5Util;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liyabin on 2017/9/28.
 * 湖南胜杰
 */
@Service
public class HuNanShengJieRequest  implements ConnectReqest {

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
        log.info("8、发送流程---发送供应商---湖南胜杰--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        //准备供应商参数
        String host = (String) supplyMap.get("host");
        String uid = (String)supplyMap.get("uid");
        String sn = (String)supplyMap.get("sn");
        String bkurl = (String)supplyMap.get("callback_url");
        String orderId =chargeRequest.getChannelNum() ;
        String hm = chargeRequest.getMobile();
        String pid = chargeRequest.getPositionCode();
        String sign="uid="+uid+"&od="+orderId+"&hm="+hm+"&pid="+pid+"&sn="+sn;
        String key= DigestUtils.md5Hex(sign);


        //封装参数
        String param = "<?xml version=\"1.0\" encoding=\"gb2312\"?>" +
                "<items>" +
                " <ob>pay</ob>" +
                " <uid>"+uid+"</uid>" +
                " <od>"+orderId+"</od>" +
                " <hm>"+hm+"</hm>" +
                " <bkurl>"+bkurl+"</bkurl>" +
                " <pid>"+pid+"</pid>" +
                " <key>"+key+"</key>" +
                "</items>" ;
        log.info("8、发送流程---发送供应商---湖南胜杰--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" + JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpClients.doPost(host,param,"application/xml");
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---湖南胜杰----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到湖南胜杰！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---湖南胜杰封装应用参数json报文请求参数---" +JSON.toJSONString(param)+"   ------返回数据：" + resultContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));

        Document document = DocumentHelper.parseText(resultContent);
        Element root = document.getRootElement();
        String resCode = root.elementText("state");
        if("8888".equals(resCode)){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderId);
            log.info("8、发送流程：发送供应商---湖南胜杰----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到湖南胜杰成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(resCode);
            response.setStatusMsg(resCode+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---湖南胜杰----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到湖南胜杰失败！原因："+resCode+"请咨询供应商！");

        }
        return response;
    }
}
