package com.jtd.recharge.connect.flow.henanyidong;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 河南移动
 */
@Controller
@RequestMapping(value = "/return")
public class HeNanYiDongReport {

    private final Logger logger = LoggerFactory.getLogger(HeNanYiDongReport.class);

    @RequestMapping("/flow/henanMobile")
    public void report(HttpServletRequest request,
                       HttpServletResponse response, @RequestBody JSONObject jsonObject) throws IOException {
        logger.info("河南移动结果通知参数: {}", JSONObject.fromObject(request.getParameterMap()));
        logger.info("河南移动结果通知参数: {}", jsonObject);

        // String json = org.apache.commons.io.IOUtils.toString(request.getInputStream());
        // JSONObject jsonObject = JSONObject.fromObject(json);

        // logger.info("接受到的参数解析后: {}", jsonObject);

        // 获取业务参数
        JSONObject parasJson = jsonObject.getJSONObject("jd_json_paras");
        ChargeReport report = new ChargeReport();
        report.setChannelNum(parasJson.getString("CUST_ORDER_ID"));
        // 判断充值结果
        if ("Y".equals(parasJson.getString("DEAL_RESULT"))) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            logger.info("充值成功，状态：{}，订单号：{}", parasJson.get("DEAL_RESULT"), parasJson.get("CUST_ORDER_ID"));
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(parasJson.getString("DEAL_MESSAGE")+"请咨询供应商！");
            logger.info("充值失败，状态：{}，订单号：{}, 错误描述:{}",
                    parasJson.get("DEAL_RESULT"), parasJson.get("CUST_ORDER_ID"), parasJson.get("DEAL_MESSAGE"));
        }

        logger.info("2.河南移动成功添加到回执消息队列======" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        logger.info("3.河南移动成功发送消息，Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("{\"respCode\":\"00000\",\"respDesc\":\"调用成功!\"}");
    }
}
