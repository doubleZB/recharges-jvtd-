package com.jtd.recharge.connect.flow.zongxiong;

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
 * Created by lhm on 2017/6/7.
 * 棕熊流量
 */
@Controller
@RequestMapping("/return")
public class ZongXiongFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/zongxiong")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);

        log.info("1.棕熊流量成功接收到回调，内容:==="+ json);

        JSONObject jsonObject = JSON.parseObject(json);

        JSONArray jsonArray = jsonObject.getJSONArray("orderList");
        log.info("1.棕熊流量成功接收到回调，内容:===orderList===="+ jsonArray);

        String channelNum = null;
        String status = null;
        String responseMsg  = null;
        ChargeReport chargeReport = new ChargeReport();
        String responseCode;
        if(jsonArray.size()>0) {
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject jobTwo = jsonArray.getJSONObject(j);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                status = jobTwo.getString("status");
                log.info("1.棕熊流量得到的状态---------" + status);
                channelNum = jobTwo.getString("channelOrderId");
                responseMsg = jobTwo.getString("responseMsg");
                log.info("1.棕熊流量成功接收到回调，订单状态:==="+ status+"===成功接收到订单号:==="+channelNum);


                chargeReport.setChannelNum(channelNum);

                if("4".equals(status)) {
                    chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                    responseCode="SUC11";
                    log.info("1.棕熊流量回执成功==订单状态:==="+ status+"===成功接收到订单号:==="+channelNum);

                }else{
                    chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                    responseCode="SUC01";
                    chargeReport.setMessage(responseMsg +"请咨询供应商！");
                    log.info("1.棕熊流量回执失败==订单状态:==="+ status+"===成功接收到订单号:==="+channelNum+"内容"+responseMsg+"请咨询供应商！");
                }

                log.info("2.棕熊流量成功添加回执消息队列" + JSON.toJSONString(chargeReport));
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

                Message message = new Message();
                message.setMessageBody(JSON.toJSONString(chargeReport));
                Message putMsg = queue.putMessage(message);
                log.info("3.棕熊流量成功发送消息Send message id is: " + putMsg.getMessageId());

                Map param = new HashMap();
                param.put("responseCode",responseCode);
                param.put("serialNum",channelNum);
                log.info("4.棕熊流量成功之后返回json: " + JSON.toJSONString(param));

                response.getWriter().print(JSON.toJSONString(param));
            }
        }else{
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            chargeReport.setMessage("无数据");
            responseCode="SUC01";
            log.info("1.棕熊流量回执失败==订单状态:==="+ status+"===成功接收到订单号:==="+channelNum+"无数据请咨询供应商！");
            Message message = new Message();
            message.setMessageBody(JSON.toJSONString(chargeReport));
            Map param = new HashMap();
            param.put("responseCode",responseCode);
            param.put("serialNum",channelNum);
            log.info("4.棕熊流量成功之后返回json: " + JSON.toJSONString(param));

            response.getWriter().print(JSON.toJSONString(param));
        }
    }
}
