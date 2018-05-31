package com.jtd.recharge.connect.flow.liuliangfengbao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 流量风暴 话费 充值
 */
public class LiuLiangFengBaoTelBillRequest implements ConnectReqest {

    private final Logger logger = LoggerFactory.getLogger(LiuLiangFengBaoTelBillRequest.class);

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        logger.info("流量风暴话费充值");
        long start =System.currentTimeMillis();
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        String tradeUser = (String) supplyMap.get("trade_user"); // 用户标识
        String key = (String) supplyMap.get("apiKey");
        String host = (String) supplyMap.get("host");
        String phoneno = chargeRequest.getMobile(); // 手机号
        String cardnum = chargeRequest.getPositionCode(); // 档案编码
        String orderid = chargeRequest.getChannelNum();    // 订单号

        String sign = DigestUtils.md5Hex(tradeUser + "-" + orderid + "-" + cardnum + "-" + phoneno + "-" + key);
        Map<String, String> param = new HashMap<>();
        param.put("trade_user", tradeUser); // 用户标识
        param.put("trade_order", orderid); // 用户订单编号
        param.put("trade_product", cardnum); // 订购产品编号
        param.put("trade_mobile", phoneno); // 订购产品的手机号码
        param.put("trade_passcode", sign); // 密文

        logger.info("1、发送流程：发送供应商---流量风暴话费----mobile ={} orderNum={} 封装应用参数json报文请求参数:{}",
                chargeRequest.getMobile(), chargeRequest.getChannelNum(), JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent;
        try {
            resultContent = HttpTookit.doPost(host,param);
        } catch (Exception e) {
            logger.error("2、发送异常：发送供应商---流量风暴流量----mobile={} orderNum={} 提交到聚合话费！原因：",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }

        logger.info("3、发送流程：发送供应商---流量风暴流量----mobile ={} orderNum={} 返回数据：{}*******发送请求耗时：{}"
                ,chargeRequest.getMobile(),chargeRequest.getChannelNum(),resultContent,(System.currentTimeMillis()-start));

        JSONObject jsonObject= JSON.parseObject(resultContent);
        String retinfo = jsonObject.getString("retinfo");
        if ("00001".equals(retinfo)) {
            response.setStatusCode(retinfo);
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            logger.info("4、发送流程：发送供应商---流量风暴----mobile ={} orderNum={} 提交到流量风暴流量成功！",
                    chargeRequest.getMobile(),chargeRequest.getChannelNum());
        } else {
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(retinfo);
            response.setStatusMsg(retinfo + "请咨询供应商！");
            logger.info("5、发送流程：发送供应商---流量风暴----mobile ={} orderNum={} 提交到聚合话费失败！原因：{}请咨询供应商！",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), retinfo);
        }

        return response;
    }
}
