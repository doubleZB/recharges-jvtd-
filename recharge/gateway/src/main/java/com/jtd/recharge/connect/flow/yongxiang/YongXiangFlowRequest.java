package com.jtd.recharge.connect.flow.yongxiang;

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
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @autor lhm 2017/10/12
 * 永祥科技 流量 充值
 */
@Service
public class YongXiangFlowRequest implements ConnectReqest{

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
        log.info("8、发送流程--发送供应商---永祥科技--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String Action = (String) supplyMap.get("action");
        String Account = (String) supplyMap.get("account");
        String V = (String) supplyMap.get("v");
        String key = (String) supplyMap.get("key");
        String Mobile = chargeRequest.getMobile();
        String size = chargeRequest.getPositionCode();
        String OutTradeNo = chargeRequest.getChannelNum();

        String signs="account="+Account+"&mobile="+Mobile+"&package="+size+"&key="+key;

        log.info("sign=============="+signs);
        String sign= DigestUtils.md5Hex("account="+Account+"&mobile="+Mobile+"&package="+size+"&key="+key);

        Map param = new HashMap();
        param.put("V",V);
        param.put("Action",Action);
        param.put("Account",Account);
        param.put("Mobile",Mobile);
        param.put("package",size);
        param.put("Sign",sign);
        param.put("OutTradeNo",OutTradeNo);
        log.info("8、发送流程---发送供应商--永祥科技--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" + JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---永祥科技----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到永祥科技！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---永祥科技---" +JSON.toJSONString(param)+"   ------返回数据：" + resultContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("Code");
        String respMsg=object.getString("Message");
        if(respCode.equals("0")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(OutTradeNo);
            log.info("8、发送流程：发送供应商---永祥科技----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到永祥科技成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(respMsg+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---永祥科技----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到永祥科技失败！原因："+respMsg+"请咨询供应商！");
        }
        return response;
    }
}
