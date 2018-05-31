package com.jtd.recharge.connect.flow.youpinxin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @autor lhm 2017/10/12
 * 优品信
 */
@Controller
@RequestMapping("/return")
public class YouPinXinFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/youpinxin")
    public void flowRecharge(HttpServletRequest request, HttpServletResponse response) throws IOException{
        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);
        log.info("1.优品信 成功收到回調report---" + json);
        JSONObject jsonObject = JSON.parseObject(json);

            String orderID = jsonObject.getString("cstmOrderNo");
            String status = jsonObject.getString("status");
            String msg = jsonObject.getString("msg");

            log.info("1.优品信 report---訂單狀態 ：訂單號" + status + ":" + orderID);
            ChargeReport report = new ChargeReport();
            report.setChannelNum(orderID);

            if ("7".equals(status)) {
                report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                log.info("1.优品信回执成功---訂單狀態 ：" + status + "訂單號:" + orderID);
            } else {
                report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                report.setMessage(msg+"请咨询供应商！");
                log.info("1.优品信回执失败---訂單狀態 ：" + status + "訂單號:" + orderID+"请咨询供应商！");
            }

            log.info("2.优品信成功添加回执消息队列" + JSON.toJSONString(report));
            MNSClient client = MessageClient.getClient();
            CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

            Message message = new Message();
            message.setMessageBody(JSON.toJSONString(report));
            Message putMsg = queue.putMessage(message);
            log.info("3.优品信发送消息Send message id is: " + putMsg.getMessageId());

            Map param = new HashMap();
            param.put("code","0000");
            param.put("msg","接收成功");
            response.getWriter().print(JSON.toJSONString(param));

    }
}
