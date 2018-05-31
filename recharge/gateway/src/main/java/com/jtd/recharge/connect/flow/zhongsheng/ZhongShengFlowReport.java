package com.jtd.recharge.connect.flow.zhongsheng;

import com.alibaba.fastjson.JSON;
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

/**
 * Created by liyabin on 2017/9/5.
 */
@Controller
@RequestMapping("/return")
public class ZhongShengFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/zhongsheng")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            InputStream in = request.getInputStream();
            String json = IOUtils.toString(in);
            JSONObject job =JSON.parseObject(json);
            String OrderNo = job.getString("OrderNo");
            String State = job.getString("State");
            String RepMsg = job.getString("RepMsg");
            log.info("1.众盛 成功收到回調report---訂單狀態" + State + "訂單號:" + OrderNo);
            ChargeReport report = new ChargeReport();
            report.setChannelNum(OrderNo);

            if ("1000".equals(State)) {
                report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                log.info("1.众盛回执成功---訂單狀態 ：" + State + "訂單號:" + OrderNo);
            } else {
                report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                report.setMessage(State + "请咨询供应商！");
                log.info("1.众盛回执失败---訂單狀態 ：" + State + "訂單號:" + OrderNo + "请咨询供应商！状态：" + State + "请咨询供应商！");

            }

            log.info("2.众盛成功添加回执消息队列" + JSON.toJSONString(report));
            MNSClient client = MessageClient.getClient();
            CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

            Message message = new Message();
            message.setMessageBody(JSON.toJSONString(report));
            Message putMsg = queue.putMessage(message);
            log.info("3.众盛成功发送消息Send message id is: " + putMsg.getMessageId());
            response.getWriter().print("OK");
        } catch (Exception e) {
            log.error(e);
        }
    }
}
