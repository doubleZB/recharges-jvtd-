package com.jtd.recharge.connect.flow.baishitong;

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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyabin on 2017/9/27.
 */
@Controller
@RequestMapping("/return")
public class BaiShiTongFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/baishitong")
    public void flowRecharge(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);
        try {
            log.info("1.百事通 成功收到回調report---" + json);
            JSONObject job = JSONObject.parseObject(json);
            log.info("百事通收到回調----" + job);
            String respCode = job.getString("respCode");
            String meg = job.getString("respMsg");
            //返回的消息体
            String orderID = job.getString("orderno_ID");//商户流水

            log.info("1.百事通 report---订单状态 ：订单号" + orderID + ":" + orderID);
            ChargeReport report = new ChargeReport();
            report.setChannelNum(orderID);
            if ("0002".equals(respCode)) {
                report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                log.info("1.百事通回执成功---订单状态 ：" + respCode + "订单号:" + orderID);
            } else {
                report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                report.setMessage(meg + "请咨询供应商！");
                log.info("1.百事通回执失败---订单状态 ：" + respCode + "订单号:" + orderID + "请咨询供应商！");
            }
            log.info("2.百事通成功添加回执消息队列" + JSON.toJSONString(report));
            MNSClient client = MessageClient.getClient();
            CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

            Message message = new Message();
            message.setMessageBody(JSON.toJSONString(report));
            Message putMsg = queue.putMessage(message);
            log.info("3.百事通发送消息Send message id is: " + putMsg.getMessageId());
            Map map=new HashMap<>();
            map.put("null","Success");
            String jsonMap = JSON.toJSONString(map);
            response.getWriter().print(jsonMap);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
