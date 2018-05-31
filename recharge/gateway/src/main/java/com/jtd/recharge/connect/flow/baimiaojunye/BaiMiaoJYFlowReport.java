package com.jtd.recharge.connect.flow.baimiaojunye;

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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyabin on 2017/9/27.
 */
@Controller
@RequestMapping("/return")
public class BaiMiaoJYFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/baimiaojunye")
    public void flowRecharge(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);
        String   platformid="jtongda";
        try {
            log.info("1.百妙骏业 成功收到回調report---" + json);
            JSONObject job = JSONObject.parseObject(json);
            log.info("百妙骏业收到回調----" + job);
            String respCode = job.getString("resultcode");
            String Mobile = job.getString("mobile");
            String meg = job.getString("resultdescription");
            //返回的消息体
            String orderID = job.getString("userorderno").replaceAll(platformid,"");;//商户流水

            log.info("1.百妙骏业 report---订单状态 ：订单号" + respCode + ":" + orderID);
            ChargeReport report = new ChargeReport();
            report.setChannelNum(orderID.replace("jtongda",""));
            report.setMobile(Mobile);

            if ("00000".equals(respCode)) {
                report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                log.info("1.百妙骏业回执成功---订单状态 ：" + respCode + "订单号:" + orderID);
            } else {
                report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                report.setMessage(meg + "请咨询供应商！");
                log.info("1.百妙骏业回执失败---订单状态 ：" + respCode + "订单号:" + orderID + "请咨询供应商！");
            }
            log.info("2.百妙骏业成功添加回执消息队列" + JSON.toJSONString(report));
            MNSClient client = MessageClient.getClient();
            CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

            Message message = new Message();
            message.setMessageBody(JSON.toJSONString(report));
            Message putMsg = queue.putMessage(message);
            log.info("3.百妙骏业发送消息Send message id is: " + putMsg.getMessageId());
            Map map=new HashMap<>();
            map.put("resultcode","0");
            map.put("resultdescription","succeed");
            String jsonMap = JSON.toJSONString(map);
            response.getWriter().print(jsonMap);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
