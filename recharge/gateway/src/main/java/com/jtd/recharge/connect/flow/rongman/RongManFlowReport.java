package com.jtd.recharge.connect.flow.rongman;

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
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by liyabin on 2017/9/28.
 * 融曼流量
 */
@Controller
@RequestMapping("/return")
public class RongManFlowReport {
       private Log log = LogFactory.getLog(this.getClass());

        @RequestMapping("/flow/rongman")
        public void report(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
            InputStream in = request.getInputStream();
            String json = IOUtils.toString(in);
            try {
                log.info("1.融曼流量 成功收到回調report---" + json);
                    JSONObject job = JSONObject.parseObject(json);
                    log.info("融曼流量收到回調----" + job);
                    String respCode = job.getString("respCode");
                    String Mobile = job.getString("Mobile");
                    //返回的消息体
                    String OutTradeNo = job.getString("orderId");//商户流水
                    String rspDesc = job.getString("rspDesc");

                    log.info("1.融曼流量 成功收到回調report---订单状态" + respCode + "订单号:" + OutTradeNo);
                    ChargeReport report = new ChargeReport();
                    report.setChannelNum(OutTradeNo);
                    report.setMobile(Mobile);

                    if ("0000".equals(respCode)) {
                        report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                        log.info("1.融曼流量回执成功---订单状态 ：" + respCode + "订单号:" + OutTradeNo);
                    } else {
                        report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                        report.setMessage(rspDesc + "请咨询供应商！");
                        log.info("1.融曼流量回执失败---订单状态 ：" + respCode + "订单号:" + OutTradeNo + "请咨询供应商！状态：" + respCode + "请咨询供应商！");

                    }

                    log.info("2.融曼流量成功添加回执消息队列" + JSON.toJSONString(report));
                    MNSClient client = MessageClient.getClient();
                    CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

                    Message message = new Message();
                    message.setMessageBody(JSON.toJSONString(report));
                    Message putMsg = queue.putMessage(message);
                    log.info("3.融曼流量成功发送消息Send message id is: " + putMsg.getMessageId());
                response.getWriter().print("ok");
            } catch (Exception e) {
                log.error(e);
            }
        }
}
