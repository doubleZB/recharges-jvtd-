package com.jtd.recharge.connect.flow.chuangkewotu;

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
 * 创客沃土回执
 */
@Controller
@RequestMapping("/return")
public class  ChuangKeWoTuFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/chuangkewotu")
    public void report(HttpServletRequest request, HttpServletResponse response) throws Exception {

//        InputStream in = request.getInputStream();
//        String json = IOUtils.toString(in);
       // JSONObject jsonObject = JSON.parseObject(json);
        //充值结果错误码Report_Code
        String respCode=request.getParameter("Report_Code");//状态码 4 充值成功，5充值失败
        //充值结果状态描述
        String respMsg = request.getParameter("Report_Msg");//状态消息
        String orderID = request.getParameter("orderSeq");//流水号
        String phoneNo = request.getParameter("mobileNum");//充值手机
        String customParm = request.getParameter("orderSn");//商户订单号
        log.info("1.创客沃土成功接收到回调，订单状态:==="+ respCode+"===成功接收到订单号:==="+orderID+"===手机号==="+phoneNo);

        ChargeReport report = new ChargeReport();

        report.setMobile(phoneNo);
        report.setChannelNum(customParm);
        report.setMessage(respMsg);

        if("4".equals(respCode)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.创客沃土回执成功===手机号==="+phoneNo+"===订单号:==="+orderID+"订单状态:==="+ respCode);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            log.info("1.创客沃土回执成功===手机号==="+phoneNo+"===订单号:==="+orderID+"订单状态:==="+ respCode+"请咨询供应商");
        }

        log.info("2.创客沃土成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3，创客沃土成功发送消息，Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("Success");
    }
}
