package com.jtd.recharge.connect.flow.maiguanghuyu;

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
 * Created by 麦广互娱 on 2017/7/21.
 */
@Controller
@RequestMapping("/return")
public class MaiGuangHuYuFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/maiguanghuyu")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
                String orderStatus = request.getParameter("orderStatus");
                String customerOrderId =  request.getParameter("customerOrderId");
                String orderMemo =  request.getParameter("orderMemo");
                //返回的消息体
                log.info("1.麦广互娱成功收到回調report---訂單狀態" + orderStatus + "訂單號:" + customerOrderId);
                ChargeReport report = new ChargeReport();
                report.setChannelNum(customerOrderId);

                if ("1".equals(orderStatus)) {
                    report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                    log.info("1.麦广互娱回执成功---訂單狀態 ：" + orderStatus + "訂單號:" + customerOrderId);
                } else {
                    report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                    report.setMessage(orderStatus + "请咨询供应商！");
                    log.info("1.麦广互娱回执失败---訂單狀態 ：" + orderStatus + "訂單號:" + customerOrderId + "请咨询供应商！状态：" + orderStatus + "请咨询供应商！");

                }

                log.info("2.麦广互娱成功添加回执消息队列" + JSON.toJSONString(report));
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

                Message message = new Message();
                message.setMessageBody(JSON.toJSONString(report));
                Message putMsg = queue.putMessage(message);
                log.info("3.麦广互娱成功发送消息Send message id is: " + putMsg.getMessageId());
            response.getWriter().print("success");
            } catch (Exception e) {
                 log.error(e);
        }
    }

}
