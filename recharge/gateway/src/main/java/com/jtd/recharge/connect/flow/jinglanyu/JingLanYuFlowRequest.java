package com.jtd.recharge.connect.flow.jinglanyu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpClients;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lhm on 2017/10/9.
 * 京蓝宇
 */
@Service
public class JingLanYuFlowRequest  implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        long start =System.currentTimeMillis();
        log.info("8、发送流程---发送供应商---京蓝宇--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String host = (String) supplyMap.get("host");
        String key = (String) supplyMap.get("key");
        String action = (String) supplyMap.get("action");
        String v = (String) supplyMap.get("v");
        String account = (String) supplyMap.get("account");
        String range = (String) supplyMap.get("range");
        String callbackurl = (String) supplyMap.get("callback_url");
        String mobile = chargeRequest.getMobile();
        String size = chargeRequest.getPositionCode();
        String corderid = chargeRequest.getChannelNum();

        String fist=account+mobile+key+range+size;

        log.info("8.京蓝宇加密前的参数==========="+fist);
        String sign= DigestUtils.md5Hex(account+mobile+key+range+size);

        String date = "action=" + action + "&v=" + v + "&account=" + account + "&range=" + range + "&mobile=" + mobile + "&size=" + size + "&callbackurl=" + callbackurl + "&corderid=" + corderid + "&sign=" + sign;

        log.info("8、发送流程---发送供应商---京蓝宇--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" + date);


        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpClients.doPost(host,date,"application/x-www-form-urlencoded");
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---京蓝宇----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到京蓝宇！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程--京蓝宇--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---京蓝宇---" +date+"   ------返回数据：" + resultContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String state=object.getString("status");
        String message=object.getString("message");
        if(state.equals("1")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(corderid);
            log.info("8、发送流程：发送供应商---京蓝宇----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到京蓝宇成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(state);
            response.setStatusMsg(message+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---京蓝宇----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到京蓝宇失败！原因："+message+"请咨询供应商！");
        }
        return response;
    }
}
