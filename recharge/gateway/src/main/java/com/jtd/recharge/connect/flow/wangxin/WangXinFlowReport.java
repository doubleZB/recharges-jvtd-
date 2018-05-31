package com.jtd.recharge.connect.flow.wangxin;

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
 * @autor jipengkun
 * 网信回执
 */
@Controller
@RequestMapping("/return")
public class WangXinFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/wangxin")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            String status = jsonObject.getString("err");
            String msg = jsonObject.getString("fail_describe");
            String mobile = jsonObject.getString("mobile");
            String msgId = jsonObject.getString("msgId");
            log.info("1.网信流量成功接收到回调，订单状态:===" + status + "===成功接收到订单号:===" + msgId + "===手机号===" + mobile);
            ChargeReport report = new ChargeReport();
            report.setMobile(mobile);
            report.setChannelNum(msgId);
            report.setMessage(status+msg);

            if ("0".equals(status)) {
                report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                log.info("1.网信流量回执成功===订单号:===" + msgId + "===手机号===" + mobile + "订单状态:===" + status);

            } else {
                report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                log.info("1.网信流量回执失败===订单号:===" + msgId + "===手机号===" + mobile + "订单状态:===" + status + "请咨询供应商！");

            }

            log.info("2.网信流量成功添加回执消息队列" + JSON.toJSONString(report));
            MNSClient client = MessageClient.getClient();
            CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

            Message message = new Message();
            message.setMessageBody(JSON.toJSONString(report));
            Message putMsg = queue.putMessage(message);
            log.info("3.网信流量成功发送消息Send message id is: " + putMsg.getMessageId());

            response.getWriter().print("0000");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
