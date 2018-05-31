package com.jtd.recharge.connect.flow.rongjing;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import com.jtd.recharge.dao.mapper.ChargeOrderDetailMapper;
import com.jtd.recharge.dao.po.ChargeOrderDetail;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 荣景流量 on 2017/10/16.
 */
@Controller
@RequestMapping("/return")
public class RongJingFlowReport {
    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    ChargeOrderDetailMapper chargeOrderDetailMapper;

    @RequestMapping("/flow/rongjing")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);

        log.info("1.荣景流量成功接收到回调，内容"+json);

        JSONObject jsonObject = JSON.parseObject(json);
        String respCode = jsonObject.getString("orderStatus");
        String mag = jsonObject.getString("message");
        String orderid = jsonObject.getString("orderid");//商户流水

        log.info("1.荣景流量 成功收到回調report---訂單狀態" + respCode + "訂單號:" + orderid);
        ChargeReport report = new ChargeReport();

        List<ChargeOrderDetail> chargeOrderDetail= chargeOrderDetailMapper.selectOrderNumBySupplyChannelNum(orderid);
        report.setChannelNum(chargeOrderDetail.get(0).getChannelNum());
        String status;
        String messages;
        if ("2".equals(respCode)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.荣景流量回执成功---訂單狀態 ：" + respCode + "訂單號:" + chargeOrderDetail.get(0).getChannelNum());
            status="0";
            messages="ok";
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(mag + "请咨询供应商！");
            log.info("1.荣景流量回执失败---訂單狀態 ：" + respCode + "訂單號:" + chargeOrderDetail.get(0).getChannelNum() + "请咨询供应商！状态：" + mag + "请咨询供应商！");
            status="0";
            messages="ok";
        }
        log.info("2.荣景流量成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.荣景流量成功发送消息Send message id is: " + putMsg.getMessageId());

        Map<String, String> map = new HashMap<String, String>();
        map.put("status", status);
        map.put("message", messages);
        response.getWriter().print(JSON.toJSONString(map));
    }

}
