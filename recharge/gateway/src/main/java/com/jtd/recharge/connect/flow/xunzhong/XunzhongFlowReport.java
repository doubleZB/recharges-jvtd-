package com.jtd.recharge.connect.flow.xunzhong;

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

/**
 * @autor lyp
 * 讯众回执
 */
@Controller
@RequestMapping("/return")
public class XunzhongFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/xunzhong")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);

        log.info("1.讯众流量成功接收到回调，内容"+json);

        JSONObject jsonObject = JSON.parseObject(json);
        //充值结果错误码
        String respCode=jsonObject.getString("trafficSts");
        //充值结果状态描述
        String respMsg=jsonObject.getString("remark");
        String orderID =jsonObject.getString("requestId");//商户流水
        String phoneNo=jsonObject.getString("mobile");//充值手机
        String customParm=jsonObject.getString("customParm");//用户传给平台的订单编号（若用户提交订单时未带有该参数，回调时则无此参数）

        ChargeReport report = new ChargeReport();

        report.setMobile(phoneNo);
        report.setChannelNum(customParm);
        report.setMessage(respMsg);

        if("1".equals(respCode)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.讯众流量回执成功===手机号==="+phoneNo+"===订单号:==="+orderID+"订单状态"+respCode);

        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            log.info("1.讯众流量回执失败===手机号==="+phoneNo+"===订单号:==="+orderID+"订单状态"+respCode+"请咨询供应商！");

        }

        log.info("讯众成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("讯众成功发送消息Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("Success");
    }
}
