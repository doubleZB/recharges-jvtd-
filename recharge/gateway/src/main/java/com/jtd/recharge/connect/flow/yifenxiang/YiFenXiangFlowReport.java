package com.jtd.recharge.connect.flow.yifenxiang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @autor jipengkun
 * 易分享回执
 */
@Controller
@RequestMapping("/return")
public class YiFenXiangFlowReport {
    private Log log = LogFactory.getLog(this.getClass());
    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }
    @RequestMapping("/flow/yifenxiangSFFlow")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {


        InputStream in = request.getInputStream();

        String json = IOUtils.toString(in);
        log.info("1.易分享成功接收到回调内容"+json);

        JSONObject jsonObject = JSON.parseObject(json);
        //返回的消息体
        String result=jsonObject.getString("result");//自己的流水号
        JSONObject jsonObjectResult = JSON.parseObject(result);
        String mobile=jsonObjectResult.getString("mobile");//手机号
        String orderid=jsonObjectResult.getString("orderid");//提交的订单号
        String msgid=jsonObjectResult.getString("msgid");//上有订单号
        String rechargeDesc=jsonObjectResult.getString("rechargeDesc");//充值描述
        String rechargeStatus=jsonObjectResult.getString("rechargeStatus");//充值结果
        log.info("1.易分享成功接收到回调，订单状态:==="+ rechargeStatus+"===成功接收到订单号:==="+orderid+"===手机号==="+mobile);

        ChargeReport report = new ChargeReport();

        report.setMobile(mobile);
        report.setChannelNum(orderid);
        report.setMessage(rechargeDesc);

        if("0".equals(rechargeStatus)||"h0".equals(rechargeStatus)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.易分享回执成功===手机号==="+mobile+"===订单号:==="+orderid+"订单状态:==="+ rechargeStatus);

        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            log.info("1.易分享回执失败===手机号==="+mobile+"===订单号:==="+orderid+"订单状态:==="+ rechargeStatus+"请咨询供应商！");
        }

        log.info("2.易分享添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.易分享成功发送消息Send message id is: " + putMsg.getMessageId());
        Map supplyMap = (Map) config.get("flowyifenxiangSF");
        String appKey=(String) supplyMap.get("appKey");
        String appSecret=(String) supplyMap.get("appSecret");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");//时间戳格式
        String timestamp=formatter.format(new java.util.Date());//时间戳
        String secertKey= DigestUtils.md5Hex(appKey+timestamp+appSecret).toUpperCase(); //加密串 大写
        String code="000";
        String msg="成功";
        HashMap<String, String> map=new HashMap<String, String>();
        map.put("appKey", appKey);
        map.put("timestamp", timestamp);
        map.put("secertKey", secertKey);
        map.put("code", code);
        map.put("msg", msg);
        response.getWriter().print(JSON.toJSONString(map));
    }
}
