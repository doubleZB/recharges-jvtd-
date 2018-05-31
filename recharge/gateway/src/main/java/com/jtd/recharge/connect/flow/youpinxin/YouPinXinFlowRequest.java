package com.jtd.recharge.connect.flow.youpinxin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import com.jtd.recharge.dao.bean.util.SHAUtil;
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
 * @autor lhm 2017/10/24
 * 优品信 流量 充值
 */
@Service
public class YouPinXinFlowRequest implements ConnectReqest{

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
        log.info("8、发送流程--发送供应商---优品信--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String appkey = (String) supplyMap.get("apiKey");
        String securityKey = (String) supplyMap.get("securityKey");
        String callbackUrl = (String) supplyMap.get("callback_url");
        String phoneNo = chargeRequest.getMobile();
        String productId = chargeRequest.getPositionCode();
        String cstmOrderNo = chargeRequest.getChannelNum();
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());


        String signs="appkey"+appkey+"callbackUrl"+callbackUrl+"cstmOrderNo"+cstmOrderNo+"phoneNo"+phoneNo+"productId"+productId+"timeStamp"+timeStamp+securityKey;

        log.info("sign=============="+signs);
        String sign= SHAUtil.shaEncode(signs);

        Map param = new HashMap();
        param.put("timeStamp",timeStamp);
        param.put("appkey",appkey);
        param.put("sig",sign);
        param.put("phoneNo",phoneNo);
        param.put("productId",productId);
        param.put("cstmOrderNo",cstmOrderNo);
        param.put("callbackUrl",callbackUrl);
        log.info("8、发送流程---发送供应商--优品信--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" + JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,JSON.toJSONString(param));
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---优品信----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到优品信！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---优品信---" +JSON.toJSONString(param)+"   ------返回数据：" + resultContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("code");
        String respMsg=object.getString("msg");
        if(respCode.equals("0000")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(cstmOrderNo);
            log.info("8、发送流程：发送供应商---优品信----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到优品信成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(respMsg+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---优品信----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到优品信失败！原因："+respMsg+"请咨询供应商！");
        }
        return response;
    }
}
