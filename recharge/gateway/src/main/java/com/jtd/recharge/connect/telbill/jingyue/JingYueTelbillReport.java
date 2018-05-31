package com.jtd.recharge.connect.telbill.jingyue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
 * Created by 云启航 on 2017/7/6.
 * lhm
 */
@Controller
@RequestMapping("/return")
public class JingYueTelbillReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/telbill/jingyue")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);
        try {

            log.info("1.竞越 成功收到回調report---" + json);
            JSONArray jsonArray = JSONArray.parseArray(json);
            log.info("yunqihang jsonArray report---" + jsonArray);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject job = jsonArray.getJSONObject(i);
                log.info("竞越收到回調----"+job);
                String respCode = job.getString("Status");
                String Mobile = job.getString("Mobile");
                String ReportCode = job.getString("ReportCode");
                //返回的消息体
                String orderID = job.getString("OutTradeNo");//商户流水

                log.info("1.竞越 report---訂單狀態 ：訂單號" + respCode + ":" + orderID);
                ChargeReport report = new ChargeReport();
                report.setChannelNum(orderID);
                report.setMobile(Mobile);

                if ("4".equals(respCode)) {
                    report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                    log.info("1.竞越回执成功---訂單狀態 ：" + respCode + "訂單號:" + orderID);
                } else {
                    report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                    report.setMessage(ReportCode+"请咨询供应商！");
                    log.info("1.竞越回执失败---訂單狀態 ：" + respCode + "訂單號:" + orderID+"请咨询供应商！");

                }

                log.info("2.竞越成功添加回执消息队列" + JSON.toJSONString(report));
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

                Message message = new Message();
                message.setMessageBody(JSON.toJSONString(report));
                Message putMsg = queue.putMessage(message);
                log.info("3.竞越成功发送消息Send message id is: " + putMsg.getMessageId());
            }
            response.getWriter().print("ok");
        } catch (Exception e) {
            log.error(e);
        }
    }
}
