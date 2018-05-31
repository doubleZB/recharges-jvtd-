package com.jtd.recharge.connect.flow.weiwang;

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
 * @autor jipengkun
 * 微网回执
 */
@Controller
@RequestMapping("/return")
public class WeiWangFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/weiwang")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String status = request.getParameter("Status");
        String mobile = request.getParameter("Mobile");
        String reportCode = request.getParameter("ReportCode");
        String outTradeNo = request.getParameter("OutTradeNo");
        ChargeReport report = new ChargeReport();
        log.info("1.微网流量成功接收到回调，订单状态:==="+ status+"===成功接收到订单号:==="+outTradeNo+"===手机号==="+mobile);

        report.setMobile(mobile);
        report.setChannelNum(outTradeNo);
        report.setMessage(reportCode);

        if ("4".equals(status)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.微网流量回执成功===订单号:==="+outTradeNo+"===手机号==="+mobile+"订单状态:==="+ status);

        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            log.info("1.微网流量回执失败===订单号:==="+outTradeNo+"===手机号==="+mobile+"订单状态:==="+ status+"请咨询供应商！");

        }

        log.info("2.微网流量成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.微网流量成功发送消息Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("ok");
    }
}
