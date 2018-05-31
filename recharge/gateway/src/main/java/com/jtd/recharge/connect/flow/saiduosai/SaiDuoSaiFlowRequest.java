package com.jtd.recharge.connect.flow.saiduosai;

import com.alibaba.fastjson.JSON;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
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
 * @autor jipengkun
 * 流量 充值
 */
@Service
public class SaiDuoSaiFlowRequest implements ConnectReqest{

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商--易赛流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String UserSystemKey =(String) supplyMap.get("apiKey");
        String UserNumber =(String) supplyMap.get("UserNumber");
        String CallBackUrl=(String)supplyMap.get("callback_url");
        String PhoneNumber = chargeRequest.getMobile();
        String OutOrderNumber = chargeRequest.getChannelNum();
        String ProId=chargeRequest.getPositionCode();
        SimpleDateFormat dftimp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String StartTime=dftimp.format(new Date());
        String TimeOut="7200";
        String PayAmount="1";
        //签名
        String sign= Md5Digest.getKeyedDigest(UserNumber+OutOrderNumber+ProId+PhoneNumber+PayAmount+StartTime+TimeOut+UserSystemKey);

        Map map = new HashMap<>();
        map.put("UserNumber", UserNumber);
        map.put("OutOrderNumber", OutOrderNumber);
        map.put("ProId", ProId);
        map.put("PhoneNumber", PhoneNumber);
        map.put("PayAmount", PayAmount);
        map.put("StartTime", StartTime);
        map.put("TimeOut", TimeOut);
        map.put("CallBackUrl", CallBackUrl);
        map.put("RecordKey", sign);

        log.info("8、发送流程：发送供应商--易赛流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(map));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,map);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商--易赛流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到易赛流量！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程：发送供应商--易赛流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        Document document = null;
        try {
            document = DocumentHelper.parseText(resultContent);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        String Result = root.elementText("Result");
        if("success".equals(Result)){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
            log.info("8、发送流程：发送供应商--易赛流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到易赛流量成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setChannelNum(chargeRequest.getChannelNum());
            response.setStatusMsg(Result+"请咨询供应商！");
            response.setStatusCode(Result);
            log.info("8、发送流程：发送供应商--易赛流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到易赛流量失败！原因："+Result+"请咨询供应商！");
        }
        return response;
    }
}
