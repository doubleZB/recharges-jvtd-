package com.jtd.recharge.connect.flow.haihang;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lhm on 2017/6/26.
 * 海航流量
 */
@Controller
@RequestMapping("/return")
public class HaiHangFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/haihang")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String orderId = request.getParameter("orderid");
        String state = request.getParameter("state");

        log.info("1.海航流量成功接收到回调，订单状态:==="+ state+"===成功接收到订单号:==="+orderId);
        ChargeReport report = new ChargeReport();
        report.setChannelNum(orderId);

        if("0".equals(state)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.海航流量回执成功===订单号:==="+orderId+"订单状态:==="+ state);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(state+"请咨询供应商！");
            log.info("1.海航流量回执失败===订单号:==="+orderId+"订单状态:==="+ state+"请咨询供应商！");
        }

        log.info("2.海航流量成功添加到回执消息队列======" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.海航流量成功发送消息，Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("OK");
    }
}
