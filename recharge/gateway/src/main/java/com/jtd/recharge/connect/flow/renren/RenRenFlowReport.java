package com.jtd.recharge.connect.flow.renren;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.util.Iterator;
import java.util.Map;

/**
 * @autor lyp
 * 仁仁回执
 */
@Controller
@RequestMapping("/return")
public class RenRenFlowReport {
    private Log log = LogFactory.getLog(this.getClass());
    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }
    @RequestMapping("/flow/renren")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        InputStream in = request.getInputStream();

        String json = IOUtils.toString(in);
        log.info("1.仁仁返回的成功接收到回调，内容"+json);

        JSONArray jsonArray=JSONArray.parseArray(json);
        for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
            JSONObject jsonObject = (JSONObject) iterator.next();
            String mobile=jsonObject.getString("mobile");//充值号码
            String orderNo=jsonObject.getString("orderNo");//订单号
            String status=jsonObject.getString("status");//订购结果状态码
            String otherParam=jsonObject.getString("otherParam");//客户提供的附加参数，如果没有提供则返回null

         ChargeReport report = new ChargeReport();

        report.setMobile(mobile);
        report.setChannelNum(orderNo);


        if("00000".equals(status)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.仁仁流量回执成功====订单号:==="+orderNo+"订单状态"+status);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(status+"请咨询供应商！");
            log.info("1.仁仁流量回执失败====订单号:==="+orderNo+"订单状态"+status+"请咨询供应商！");
        }

        log.info("2.仁仁流量成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.仁仁流量成功发送消息Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("ok");
    }
    }
}
