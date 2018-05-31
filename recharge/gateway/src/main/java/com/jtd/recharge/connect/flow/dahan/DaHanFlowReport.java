package com.jtd.recharge.connect.flow.dahan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyabin on 2017/8/3.
 * 大汉三通流量
 */
@Controller
@RequestMapping("return")
public class DaHanFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/dahansantong")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);

        log.info("1.大汉流量成功接收到回调，内容:==="+ json);

        JSONObject jsonObject = JSON.parseObject(json);

        String status=jsonObject.getString("status");
        String mobile=jsonObject.getString("mobile");
        String reportTime=jsonObject.getString("reportTime");
        String massage=jsonObject.getString("errorDesc");
        String channelNum=jsonObject.getString("clientOrderId");

        log.info("1.大汉流量成功接收到回调，订单状态:==="+ status+"手机号："+mobile+"===成功接收到订单号:==="+channelNum+"回\n" +
                "调时间"+reportTime);
        ChargeReport chargeReport = new ChargeReport();
        chargeReport.setChannelNum(channelNum);
        String resultCode = null;
        String resultMsg = null;
        if("0".equals(status)) {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            resultCode="0000";
            resultMsg="处理成功！";
            log.info("1.大汉流量回执成功===订单号:==="+channelNum+"订单状态:==="+ status);
        }else{
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            chargeReport.setMessage(massage);
            resultCode="1111";
            resultMsg="处理失败！"+massage;
            log.info("1.大汉流量回执失败==订单号:==="+channelNum+"订单状态:==="+ massage);
        }

        log.info("2.大汉流量成功添加回执消息队列" + JSON.toJSONString(chargeReport));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(chargeReport));
        Message putMsg = queue.putMessage(message);
        log.info("3.大汉流量成功发送消息：Send message id is: " + putMsg.getMessageId());
        HashMap<String, String> map=new HashMap<String, String>();
        map.put("resultCode",resultCode);
        map.put("resultMsg",resultMsg);
        response.getWriter().print(JSON.toJSONString(map));
    }
}
