package com.jtd.recharge.connect.telbill.duochuang;

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
 * Created by liyabin on 2017/9/5.
 */
@Controller
@RequestMapping("/return")
public class DuoChuangTelbillReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/telbill/duochuang")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            String customerId=request.getParameter("downstreamSerialno");
            String status=request.getParameter("status");
            log.info("1.多创 成功收到回調report---訂單狀態" + status + "訂單號:" + customerId);
            ChargeReport report = new ChargeReport();
            report.setChannelNum(customerId);

            if ("2".equals(status)) {
                report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                log.info("1.多创回执成功---訂單狀態 ：" + status + "訂單號:" + customerId);
            } else {
                report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                report.setMessage(status + "请咨询供应商！");
                log.info("1.多创回执失败---訂單狀態 ：" + status + "訂單號:" + customerId + "请咨询供应商！状态：" + status + "请咨询供应商！");

            }

            log.info("2.多创成功添加回执消息队列" + JSON.toJSONString(report));
            MNSClient client = MessageClient.getClient();
            CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

            Message message = new Message();
            message.setMessageBody(JSON.toJSONString(report));
            Message putMsg = queue.putMessage(message);
            log.info("3.多创成功发送消息Send message id is: " + putMsg.getMessageId());
            response.getWriter().print("success");
        } catch (Exception e) {
            log.error(e);
        }
    }
}
