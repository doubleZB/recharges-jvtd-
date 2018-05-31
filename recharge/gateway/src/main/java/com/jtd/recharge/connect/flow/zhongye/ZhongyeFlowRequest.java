package com.jtd.recharge.connect.flow.zhongye;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2017-06-19.
 */
public class ZhongyeFlowRequest implements ConnectReqest {


    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        response.setStatus(ChargeSubmitResponse.Status.FAIL);
        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商---中业流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String url = (String)supplyMap.get("host");
        String agenAppkey = (String) supplyMap.get("agenAppkey");
        String agenAppSecret = (String) supplyMap.get("agenAppSecret");
        String plprCode = chargeRequest.getPositionCode();
        String orderPhoneno = chargeRequest.getMobile();
        String orderNextorderno = chargeRequest.getChannelNum();
        String backUrl = (String) supplyMap.get("backUrl");


        Map param = new HashMap();
        param.put("agenAppkey",agenAppkey);
        param.put("agenAppSecret",agenAppSecret);
        param.put("plprCode",plprCode);
        param.put("orderPhoneno",orderPhoneno);
        param.put("orderNextorderno",orderNextorderno);
        param.put("backUrl",backUrl);

        String resultContent = null;

        log.info("8、发送流程：发送供应商---中业流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        try {
            resultContent = HttpTookit.doPost(url,param);
        } catch (Exception e) {
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg("中业 flow提交订单时候，http访问出错"+e.getMessage()+"；订单参数："+JSON.toJSONString(param));
            log.error("8.中业 flow exception；订单参数："+ JSON.toJSONString(param)+"  出错信息"+e.getMessage(),e);
            return response;
        }
        log.info("8、发送流程：发送供应商--中业流量发送数据----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String code = object.getString("code");
        String msg = object.getString("msg");

        if("0000".equals(code) && "下单成功".equals(msg)){//成功下单
            String orderNo = object.getJSONObject("data").getString("orderNo");
            response.setStatusMsg(resultContent);
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderNextorderno);
            response.setSupplyChannelNum(orderNo);
            log.info("8、发送流程：发送供应商---中业流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到中业流量发成功！");
        }else {//下单失败
            response.setStatusMsg("错误代码："+code+",错误信息："+msg);
            log.info("8、发送流程：发送供应商---中业流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到中业流量发失败！原因："+"错误代码："+code+",错误信息："+msg);
        }
        return response;
    }
}
