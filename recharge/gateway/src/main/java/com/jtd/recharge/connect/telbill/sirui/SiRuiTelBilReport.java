package com.jtd.recharge.connect.telbill.sirui;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lhm on 2017/7/13.
 * 思锐话费
 */
@Controller
@RequestMapping("/return")
public class SiRuiTelBilReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/telbill/sirui")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        //合作商订单号
        String taskid = request.getParameter("taskid");
        //系统订单号
        String orderid = request.getParameter("orderid");
        //订单状态(1：成功；2：失败)
        String status = request.getParameter("state");

        log.info("1.收到思锐话费状态报告数据：订单号 =" + orderid + ", 合作商订单号=" + taskid + ", 状态=" + status);
        ChargeReport report = new ChargeReport();

        report.setChannelNum(orderid);

        if ("1".equals(status)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.思锐话费回执成功====订单号:===" + orderid + ", 状态=" + status);
        }else{
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(status+"请咨询供应商！");
            log.info("1.思锐话费回执失败====订单号:===" + orderid + ", 状态=" + status+"请咨询供应商！");
        }

        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.思锐话费添加回执消息队列" + JSON.toJSONString(report) + "  message id is: " + putMsg.getMessageId());

        response.getWriter().print("success");

    }
}
