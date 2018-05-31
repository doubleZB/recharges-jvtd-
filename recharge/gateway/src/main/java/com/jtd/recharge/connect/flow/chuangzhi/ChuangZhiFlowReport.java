package com.jtd.recharge.connect.flow.chuangzhi;

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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 创智流量
 * Created by lhm on 2017-08-24.
 */

@Controller
@RequestMapping("/return")
public class ChuangZhiFlowReport {
    private Log log = LogFactory.getLog(this.getClass());



    @RequestMapping("/flow/chuangzhi")
    @ResponseBody
    public void getReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderNum = request.getParameter("transNo");
        String desc = request.getParameter("respMsg");
        String statusCode = request.getParameter("respCode");

        log.info("1.创智流量成功接收到回调，订单状态:==="+ statusCode+"===成功接收到订单号:==="+orderNum);

        ChargeReport chargeReport = new ChargeReport();

        chargeReport.setChannelNum(orderNum);
        chargeReport.setMessage(desc);
        if(statusCode.equals("0000")){
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.创智流量回执成功===订单号:==="+orderNum+"订单状态:==="+ statusCode);
        }else {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            chargeReport.setMessage(desc+"请咨询供应商");
            log.info("1.创智流量回执失败===订单号:==="+orderNum+"订单状态:==="+ statusCode+"请咨询供应商");
        }
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(chargeReport));
        Message messageReturn =queue.putMessage(message);
        log.info("2.创智流量" + JSON.toJSONString(chargeReport)+",消息队列"+
                JSON.toJSONString(message)+",返回message="+JSON.toJSONString(messageReturn));

        response.getWriter().print("success");
    }
}
