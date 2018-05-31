package com.jtd.recharge.connect.flow.danyuan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lhm on 2017/4/19.
 * 单元信息
 */
@Service
public class DanYuanFlowRequest implements ConnectReqest{
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }


    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        long start =System.currentTimeMillis();
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        log.info("8、发送流程：发送供应商---单元信息----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");//充值地址
        String userid = (String) supplyMap.get("username");//用户账号
        String password = (String) supplyMap.get("password");//用户密码
        String action = (String) supplyMap.get("action");
        String phone = chargeRequest.getMobile();//手机号码
        String mbytes = chargeRequest.getPackageSize();//流量大小
        String linkid = chargeRequest.getChannelNum();

        HashMap<String, String> map=new HashMap<String, String>();
        map.put("host", host);
        map.put("password", password);
        map.put("userid", userid);
        map.put("linkid", linkid);
        map.put("action", action);
        map.put("phone", phone);
        map.put("mbytes", mbytes);

        /**
         * 提交消息
         */
        log.info("8、发送流程：发送供应商---单元信息----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(map));
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,map);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---单元信息----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到单元信息！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---单元信息----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        String errorCode ="";
        try {
            Document document = XMLUtil.parseStringToXml(resultContent);
            errorCode = document.getRootElement().attribute("return").getValue();

            if(errorCode.equals("0")){
                response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
                response.setChannelNum(linkid);
                log.info("8、发送流程：发送供应商---单元信息----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到单元信息成功！");
            }else{
                response.setStatus(ChargeSubmitResponse.Status.FAIL);
                response.setStatusCode(errorCode);
                response.setStatusMsg(errorCode+"请咨询供应商！");
                log.info("8、发送流程：发送供应商---单元信息----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到单元信息失败!原因："+errorCode+"请咨询供应商！");

            }
        }catch (Exception e){
            log.error("8、发送异常：发送供应商---单元信息----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"原因："+ e.getLocalizedMessage());
        }
        return response;
    }
}
