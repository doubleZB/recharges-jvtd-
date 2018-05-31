package com.jtd.recharge.connect.flow.weiyu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import com.jtd.recharge.connect.flow.jilindianxin.DSA;
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
 * Created by liyabin on 2017/12/22.
 */
@Controller
@RequestMapping("/return")
public class WeiYuFlowRepost {
    private Log log = LogFactory.getLog(this.getClass());


    @RequestMapping("/flow/weiyu")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            // 以流的方式接受数据
            InputStream in = request.getInputStream();
            String json = IOUtils.toString(in);
            log.info("1.威宇成功接收到回调，订单内容:==="+ json);

            JSONObject jsonObject = JSON.parseObject(json);

            JSONObject content=jsonObject.getJSONObject("MSGBODY").getJSONObject("CONTENT");
            log.info("1.威宇 成功收到回調内容---"+content.toString());
            String customerId = content.getString("EXTORDER");
            String status = content.getString("CODE");
            String msg = content.getString("STATUS");
            log.info("1.威宇 成功收到回調report---訂單狀態" + status + "訂單號:" + customerId);
            ChargeReport report = new ChargeReport();
            report.setChannelNum(customerId);

            if ("00".equals(status)) {
                report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                log.info("1.威宇回执成功---訂單狀態 ：" + status + "訂單號:" + customerId);
            } else {
                report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                report.setMessage(status +msg+ "请咨询供应商！");
                log.info("1.威宇回执失败---訂單狀態 ：" + status + "訂單號:" + customerId + "请咨询供应商！状态：" + status + "请咨询供应商！");

            }

            log.info("2.威宇成功添加回执消息队列" + JSON.toJSONString(report));
            MNSClient client = MessageClient.getClient();
            CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

            Message message = new Message();
            message.setMessageBody(JSON.toJSONString(report));
            Message putMsg = queue.putMessage(message);
            log.info("3.威宇成功发送消息Send message id is: " + putMsg.getMessageId());
            response.getWriter().print("success");
        } catch (Exception e) {
            log.error(e);
        }
    }
}
