package com.jtd.recharge.connect.flow.lianlian;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.base.constant.SysConstants;
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
 * @autor lyp
 * 连连回执
 */
@Controller
@RequestMapping("/return")
public class LianlianFlowReport {
    private Log log = LogFactory.getLog(this.getClass());


    @RequestMapping("/flow/lianlian")
    public void flowRecharge(HttpServletRequest request, HttpServletResponse response) throws IOException{

        InputStream inputStream = request.getInputStream();
        String json = IOUtils.toString(inputStream);


        JSONObject jsonObject = JSON.parseObject(json);
        String orderNo = jsonObject.getString("order_no");
        String stream_id = jsonObject.getString("stream_id");
        String status = jsonObject.getString("status");

         log.info("1.连连返回的成功接收到回调，内容"+JSON.toJSONString(json)+"平台流水号："+stream_id);

        ChargeReport report = new ChargeReport();
        report.setChannelNum(orderNo);

        if("SUCCESS".equals(status)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.连连流量回执成功====订单号:==="+orderNo+"订单状态:==="+ status);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(status+"请咨询供应商！");
            log.info("1.连连流量回执失败===订单号:==="+orderNo+"订单状态:==="+ status+"请咨询供应商！");
        }


        log.info("2.连连流量成功添加回执消息队列的" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.连连流量成功发送消息Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("SUCCESS");
    }
}
