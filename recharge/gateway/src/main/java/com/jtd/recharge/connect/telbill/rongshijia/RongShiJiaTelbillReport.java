package com.jtd.recharge.connect.telbill.rongshijia;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by liyabin on 2017/9/5.
 */
@Controller
@RequestMapping("/return")
public class RongShiJiaTelbillReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/telbill/rongshijia")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            String customerId=request.getParameter("downstreamSerialno");
            String status=request.getParameter("status");
            log.info("1.荣世佳 成功收到回調report---訂單狀態" + status + "訂單號:" + customerId);
            ChargeReport report = new ChargeReport();
            report.setChannelNum(customerId);

            if ("2".equals(status)) {
                report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                log.info("1.荣世佳回执成功---訂單狀態 ：" + status + "訂單號:" + customerId);
            } else {
                report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                report.setMessage(status + "请咨询供应商！");
                log.info("1.荣世佳回执失败---訂單狀態 ：" + status + "訂單號:" + customerId + "请咨询供应商！状态：" + status + "请咨询供应商！");

            }

            log.info("2.荣世佳成功添加回执消息队列" + JSON.toJSONString(report));
            MNSClient client = MessageClient.getClient();
            CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

            Message message = new Message();
            message.setMessageBody(JSON.toJSONString(report));
            Message putMsg = queue.putMessage(message);
            log.info("3.荣世佳成功发送消息Send message id is: " + putMsg.getMessageId());
            response.getWriter().print("success");
        } catch (Exception e) {
            log.error(e);
        }
    }
}
