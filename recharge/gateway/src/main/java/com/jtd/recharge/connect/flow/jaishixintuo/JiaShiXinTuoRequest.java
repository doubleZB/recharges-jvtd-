package com.jtd.recharge.connect.flow.jaishixintuo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpClients;
import com.jtd.recharge.base.util.HttpTookit;
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
 * Created by lihuimin on 2017/5/5.
 * 嘉石信托通道
 */
@Service
public class JiaShiXinTuoRequest  implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        long start =System.currentTimeMillis();
        log.info("8、发送流程---发送供应商---嘉石信托--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String host = (String) supplyMap.get("host");
        String key = (String) supplyMap.get("key");
        String nation = (String) supplyMap.get("nation");
        String userid = (String) supplyMap.get("userid");
        String type = (String) supplyMap.get("type");
        String callbackurl = (String) supplyMap.get("callback_url");
        String telenum = chargeRequest.getMobile();
        String size = chargeRequest.getPositionCode();
        String orderid = chargeRequest.getChannelNum();
        String timestamp=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        log.info("jiashi flow host---" + host);

        String fist=key+"callbackurl="+callbackurl +"&nation="+nation+"&orderid="+orderid+"&size="+size
                +"&telenum="+telenum+"&timestamp="+timestamp+"&type="+type+"&userid="+userid+key;

        log.info("8.嘉石信托加密前的参数==========="+fist);
        String sign= DigestUtils.md5Hex(key+"callbackurl="+callbackurl +"&nation="+nation+"&orderid="+orderid+"&size="+size
                +"&telenum="+telenum+"&timestamp="+timestamp+"&type="+type+"&userid="+userid+key);

        Map<String, String> param = new HashMap<String, String>();
        param.put("userid", userid);
        param.put("telenum", telenum);
        param.put("orderid", orderid);
        param.put("size", size);
        param.put("type", type);
        param.put("nation", nation);
        param.put("callbackurl", callbackurl);
        param.put("timestamp", timestamp);
        param.put("sign", sign);

        log.info("8、发送流程---发送供应商---嘉石信托--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" +JSON.toJSONString(param));


        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpClients.doPost(host,JSON.toJSONString(param),"application/x-www-form-urlencoded");
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---嘉石信托----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到嘉石信托！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程--嘉石信托--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---嘉石信托---" +JSON.toJSONString(param)+"   ------返回数据：" + resultContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String state=object.getString("state");
        if(state.equals("0000")){
            String orderID=object.getString("orderid");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);
            log.info("8、发送流程：发送供应商---嘉石信托----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到嘉石信托成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(state);
            response.setStatusMsg(state+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---嘉石信托----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到嘉石信托失败！原因："+state+"请咨询供应商！");

        }

        return response;
    }
}
