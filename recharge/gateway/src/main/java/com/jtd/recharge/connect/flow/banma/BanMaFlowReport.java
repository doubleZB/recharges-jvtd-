package com.jtd.recharge.connect.flow.banma;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.GroovyConfigUtil;
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
 * Created by lhm on 2017/3/28.
 */
@Controller
@RequestMapping("/return")
public class BanMaFlowReport {
    private Log log = LogFactory.getLog(this.getClass());
    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @RequestMapping("/flow/banMa")
    public void flowRecharge(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);

        log.info("banMa report---" + json);


        JSONObject jsonObject = JSON.parseObject(json);
        //充值结果错误码
        String respCode=jsonObject.getString("trafficSts");
        String orderID =jsonObject.getString("requestId");//商户流水
        String phoneNo=jsonObject.getString("mobile");//充值手机

        ChargeReport report = new ChargeReport();

        report.setMobile(phoneNo);
        report.setChannelNum(orderID);

        if("0".equals(respCode)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
        } else {
            report.setMessage(respCode+"请咨询供应商");
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
        }

        log.info("添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("Success");
    }
}
