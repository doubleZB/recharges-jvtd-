package com.jtd.recharge.connect.telbill.xiangshang;

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
 * 向上话费报告
 */
@Controller
@RequestMapping("/return")
public class XiangShangTelBillReport {

    private final Logger logger = LoggerFactory.getLogger(XiangShangTelBillReport.class);

    @RequestMapping("/telbill/xiangshang")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id = request.getParameter("id"); // 合作方用户编号(充值平台方提供)
        // String orderid = request.getParameter("orderid"); // 合作商户订单号
        // String ejId = request.getParameter("ejId"); // 充值平台方订单号
        // String deno = request.getParameter("deno"); // 订单面额
        // String successdeno = request.getParameter("successdeno"); // 成功面额
        String errcode = request.getParameter("errcode"); // 错误代码
        String errinfo = request.getParameter("errinfo"); // 错误信息
        // String encryptType = request.getParameter("encryptType"); // 加密类型

        logger.info("向上话费充值通知参与:{}", net.sf.json.JSONObject.fromObject(request.getParameterMap()));

        ChargeReport report = new ChargeReport();
        report.setChannelNum(id);

        if ("OrderSuccess".equals(errcode)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            logger.info("1.向上话费回执成功====订单状态:={}=", errcode);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(errcode + "请咨询供应商！");
            logger.info("1.向上话费回执失败===订单状态:={}=请咨询供应商！", errinfo);
        }

        logger.info("2.向上话费成功添加回执消息队列{}", JSON.toJSONString(report));

        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        logger.info("3.向上话费成功发送消息Send message id is:{} ", putMsg.getMessageId());

        response.getWriter().print("true");
    }
}
