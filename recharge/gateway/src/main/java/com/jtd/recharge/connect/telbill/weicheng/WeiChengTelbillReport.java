package com.jtd.recharge.connect.telbill.weicheng;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 唯诚话费充值--回执
 */
@Controller
@RequestMapping(value = "return")
public class WeiChengTelbillReport {

    private final Logger logger = LoggerFactory.getLogger(WeiChengTelbillReport.class);

    @RequestMapping("/telbill/weicheng")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);
        logger.info("唯诚话费充值-回执参数: {}", json);
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(json);
        //充值结果错误码
        String state=jsonObject.getString("order_is_succeed");
        String msg=jsonObject.getString("order_system_msg");
        //充值结果状态描述
        String orderID =jsonObject.getString("order_agent_bill");//商户流水
        String phoneNo=jsonObject.getString("order_tel");//充值手机

        ChargeReport chargeReport = new ChargeReport();
        chargeReport.setChannelNum(orderID);
        chargeReport.setMobile(phoneNo);

        if ("1".equals(state)) {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            logger.info("1.唯诚话费充值回执充值成功===状态:==={}", state);
        } else {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            chargeReport.setMessage(msg +" 请咨询供应商！");
            logger.info("1.唯诚话费充值回执--充值失败===订单状态:==={}请咨询供应商！", state);
        }

        logger.info("2.唯诚话费成功添加回执消息队列{}", JSON.toJSONString(chargeReport));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(chargeReport));
        Message putMsg = queue.putMessage(message);
        logger.info("3唯诚话费成功发送消息Send message id is: {}", putMsg.getMessageId());
        response.getWriter().print("success");
    }
}
