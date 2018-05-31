package com.jtd.recharge.connect.flow.weizu;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lhm on 2017/3/30.
 * 南昌微族回执
 */
@Controller
@RequestMapping("/return")
public class NanChangWeiZuFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/weiZu")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String status = request.getParameter("status");
        String channelNum = request.getParameter("user_order_id");
        log.info("1.南昌微族成功接收到回调，订单状态:==="+ status+"===成功接收到订单号:==="+channelNum);
        ChargeReport chargeReport = new ChargeReport();
        chargeReport.setChannelNum(channelNum);
        if("4".equals(status)) {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.南昌微族回执成功===订单号:==="+channelNum+"订单状态:==="+ status);

        }else{
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            chargeReport.setMessage(status+"请咨询供应商！");
            log.info("1.南昌微族回执成功===订单号:==="+channelNum+"订单状态:==="+ status+"请咨询供应商！");

        }

        log.info("2.南昌微族成功添加回执消息队列" + JSON.toJSONString(chargeReport));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(chargeReport));
        Message putMsg = queue.putMessage(message);
        log.info("3.南昌微族成功发送消息Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("ok");
    }
}
