package com.jtd.recharge.connect.flow.zhangu;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 战鼓通信
 */
@Controller
@RequestMapping(value = "/return")
public class ZhanGuFlowReport {

    private final Logger logger = LoggerFactory.getLogger(ZhanGuFlowReport.class);

    @RequestMapping("/flow/zhangu")
    public void flowRecharge(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("战鼓通信流量回调。");

        request.setCharacterEncoding("GBK");
        String errcode = request.getParameter("Orderstatu_int"); // 错误代码
        String errinfo = request.getParameter("Orderstatu_text"); // 错误信息
        String Orderid = request.getParameter("Orderid"); // 定单号
        // String status = request.getParameter("Orderstatu_int"); // 定单状态代码
        // String statusDesc = request.getParameter("定单状态(文字说明)"); //
        logger.info("战鼓话费充值通知参与:{}", net.sf.json.JSONObject.fromObject(request.getParameterMap()));

        ChargeReport report = new ChargeReport();
        report.setChannelNum(Orderid);
        if ("16".equals(errcode) || "11".equals(errcode)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            logger.info("1.战鼓话费回执成功====订单状态:={}=", errcode);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(errinfo + "请咨询供应商！状态" + errinfo);
            logger.info("1.战鼓话费回执失败===订单状态:={}=请咨询供应商！", errinfo);
        }

        logger.info("2.战鼓话费成功添加回执消息队列{}", JSON.toJSONString(report));

        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        logger.info("3.战鼓话费成功发送消息Send message id is:{} ", putMsg.getMessageId());

        response.getWriter().print("success");
    }

}
