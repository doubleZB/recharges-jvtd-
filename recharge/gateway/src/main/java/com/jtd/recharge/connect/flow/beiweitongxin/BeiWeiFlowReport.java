package com.jtd.recharge.connect.flow.beiweitongxin;

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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lhm on 2017/10/12.
 * 北纬通信
 */
@Controller
@RequestMapping("/return")
public class BeiWeiFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/beiweitongxin")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);
        try {
            log.info("1.北纬通信 成功收到回調report---" + json);
            JSONObject jsonObject = JSON.parseObject(json);
            String respCode = jsonObject.getString("result");
            String orderID = jsonObject.getString("orderid");

            log.info("1.北纬通信 report---订单状态 ：订单号" + respCode + ":" + respCode);
            ChargeReport report = new ChargeReport();
            report.setChannelNum(orderID);
            if ("0000".equals(respCode)) {
                report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                log.info("1.北纬通信回执成功---订单状态 ：" + respCode + "订单号:" + orderID);
            } else {
                report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                report.setMessage(json+"请咨询供应商！");
                log.info("1.北纬通信回执失败---订单状态 ：" + respCode + "订单号:" + orderID+"请咨询供应商！");
            }

            log.info("2.北纬通信成功添加回执消息队列" + JSON.toJSONString(report));
            MNSClient client = MessageClient.getClient();
            CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

            Message message = new Message();
            message.setMessageBody(JSON.toJSONString(report));
            Message putMsg = queue.putMessage(message);
            log.info("3.北纬通信成功发送消息Send message id is: " + putMsg.getMessageId());

            Map paramHome = new HashMap();
            paramHome.put("code","1");

            response.getWriter().print(JSON.toJSONString(paramHome));
        } catch (Exception e) {
            log.error(e);
        }
    }
}
