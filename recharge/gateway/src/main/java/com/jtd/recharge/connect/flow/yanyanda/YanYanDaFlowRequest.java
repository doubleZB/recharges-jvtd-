package com.jtd.recharge.connect.flow.yanyanda;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
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
 * Created by 妍妍达 on 2017/7/21
 * lhm
 */
@Service
public class YanYanDaFlowRequest implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {

        log.info("8、发送流程--发送供应商---妍妍达---mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());
        long start =System.currentTimeMillis();

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String host = (String) supplyMap.get("host");
        String userId = (String) supplyMap.get("userId");
        String privateKey = (String) supplyMap.get("privateKey");
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMddhhmmss");
        String dtCreate=df.format(new Date());
        String Uid = chargeRequest.getMobile();
        String itemId = chargeRequest.getPositionCode();

        String Serialno = chargeRequest.getChannelNum();

        String sign= DigestUtils.md5Hex(dtCreate+itemId+Serialno+Uid+userId+privateKey);

        Map param = new HashMap();
        param.put("Uid",Uid);
        param.put("dtCreate",dtCreate);
        param.put("userId",userId);
        param.put("itemId",itemId);
        param.put("Serialno",Serialno);
        param.put("Sign",sign);

        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---妍妍达； 封装应用参数json报文请求参数---" +JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doGet(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---妍妍达----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到妍妍达流量！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程：发送供应商---妍妍达----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));
        Document document = null;
        try {
            document = DocumentHelper.parseText(resultContent);
            Element root = document.getRootElement();
            String resCode = root.elementText("code");
            String desc = root.elementText("desc");
            if(("00").equals(resCode)){
                response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
                response.setChannelNum(Serialno);
                log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到妍妍达成功！");
            }else{
                response.setStatus(ChargeSubmitResponse.Status.FAIL);
                response.setStatusCode(resCode);
                response.setStatusMsg(desc);
                log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到妍妍达失败！原因："+desc);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return response;
    }

}
