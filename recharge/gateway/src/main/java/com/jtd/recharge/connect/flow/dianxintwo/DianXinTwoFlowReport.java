package com.jtd.recharge.connect.flow.dianxintwo;

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
 * @autor lyp
 * 电信直连2回调
 */
@Controller
@RequestMapping("/return")
public class DianXinTwoFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/dianXinTwo")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);
        log.info("dianXinTwo report---"+json);
        JSONObject jsonObject = JSON.parseObject(json);
        String head = jsonObject.getString("head");
        JSONObject jsonHead = JSON.parseObject(head);
        String biz = jsonObject.getString("biz");
        JSONObject jsonBiz = JSON.parseObject(biz);
        String desc = jsonBiz.getString("desc");
        String type = jsonBiz.getString("type");
        String comCode = jsonBiz.getString("compCode");
        String orderId = jsonBiz.getString("merchantOrderId");
        log.info("1.电信直连2成功接收到回调，订单状态:==="+ comCode+"===成功接收到订单号:==="+orderId);

        ChargeReport report = new ChargeReport();

        report.setChannelNum(orderId);
        report.setMessage(desc);

        if ("300000".equals(comCode)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.电信直连2回执成功==订单号:==="+orderId+"状态："+comCode);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            log.info("1.电信直连2回执失败==订单号:==="+orderId+"状态："+comCode+"请咨询供应商");
        }

        log.info("2.电信直连2成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.电信直连2成功添加消息Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("success");
    }
}
