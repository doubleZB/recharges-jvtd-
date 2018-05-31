package com.jtd.recharge.connect.telbill.xuanjie;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.icu.text.SimpleDateFormat;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lhm on 2017/9/27
 * 炫捷话费
 */
@Service
public class XuanJieTelBillRequest implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商---炫捷话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");//充值地址
        String key = (String) supplyMap.get("key");
        String szAgentId = (String) supplyMap.get("szAgentId");
        String szNotifyUrl = (String) supplyMap.get("callback_url");
        String nProductClass = (String) supplyMap.get("nProductClass");
        String nProductType = (String) supplyMap.get("nProductType");
        String szPhoneNum = chargeRequest.getMobile();//手机号码
        String nMoney = chargeRequest.getPackageSize();

        String positionCode = chargeRequest.getPositionCode();
        String substring = positionCode.substring(0, 1);
        String nSortType ="";
        if("4".equals(substring)){
            nSortType="1";
        }else if("5".equals(substring)){
            nSortType="2";
        }else if("6".equals(substring)){
            nSortType="3";
        }
        log.info("---packageSize ="+positionCode+" substring=" +nSortType+"截取后的substring："+substring);

        String szTimeStamp  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String szOrderId=chargeRequest.getChannelNum();
        String szFormat="JSON";
        String szVerifyString = DigestUtils.md5Hex("szAgentId="+szAgentId+"&szOrderId="+szOrderId+"&szPhoneNum="+szPhoneNum+"&nMoney="+nMoney+
                "&nSortType="+nSortType+"&nProductClass="+nProductClass+"&nProductType="+nProductType+"&szTimeStamp="+szTimeStamp+"&szKey="+key);

        Map param = new HashMap();
        param.put("szAgentId",szAgentId);
        param.put("szOrderId",szOrderId);
        param.put("szPhoneNum",szPhoneNum);
        param.put("nMoney",nMoney);
        param.put("nSortType",nSortType);
        param.put("nProductClass",nProductClass);
        param.put("nProductType",nProductType);
        param.put("szTimeStamp",szTimeStamp);
        param.put("szVerifyString",szVerifyString);
        param.put("szNotifyUrl",szNotifyUrl);
        param.put("szFormat",szFormat);

        log.info("8、发送流程：发送供应商---炫捷话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---炫捷话费----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到炫捷话费！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---炫捷话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object= JSON.parseObject(resultContent);
        String code=object.getString("nRtn");
        String massage=object.getString("szRtnCode");
        if(code.equals("0")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(szOrderId);
            log.info("8、发送流程：发送供应商---炫捷话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到炫捷话费成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(code);
            response.setStatusMsg(massage+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---炫捷话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到炫捷话费失败！原因："+massage+"请咨询供应商！");
        }
        return response;
    }
}
