package com.jtd.recharge.connect.flow.beibei;

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
 * 贝贝流量回执
 */
@Controller
@RequestMapping("/return")
public class BeiBeiFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/beibei")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String orderNo = request.getParameter("orderNo");//订单号
        String resCode = request.getParameter("resCode");//成功：00 失败：-99999
        String businessType = request.getParameter("businessType");//订单类型	1：话费   2流量
        String sign = request.getParameter("sign");
        String redMsg = request.getParameter("redMsg");
        String mobile= request.getParameter("mobile");
        log.info("1.贝贝流量成功接收到回调，订单状态:==="+ resCode+"===成功接收到订单号:==="+orderNo+"===手机号==="+mobile);


        ChargeReport report = new ChargeReport();

        report.setMobile(mobile);
        report.setChannelNum(orderNo);


        if("00".equals(resCode)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.贝贝流量回执成功===手机号==="+mobile+"===订单号:==="+orderNo+"状态:==="+ resCode);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(redMsg+"请咨询供应商");
            log.info("1.贝贝流量回执失败===手机号==="+mobile+"===订单号:==="+orderNo+"状态:==="+ resCode+"请咨询供应商");
        }

        log.info("2.贝贝流量成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.贝贝流量成功发送消息：Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("success");
    }
}
