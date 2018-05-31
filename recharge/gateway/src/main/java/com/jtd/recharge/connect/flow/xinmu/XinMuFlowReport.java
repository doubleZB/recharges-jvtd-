package com.jtd.recharge.connect.flow.xinmu;

import com.alibaba.fastjson.JSON;
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
 * @autor lyp
 * 鑫沐回执
 */
@Controller
@RequestMapping("/return")
public class XinMuFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    ChargeOrderDetailMapper chargeOrderDetailMapper;

    @RequestMapping("/flow/xinmu")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);

        log.info("1.鑫沐流量成功接收到回调，内容"+json);

        JSONObject jsonObject = JSON.parseObject(json);
        String orderid=jsonObject.getString("orderid");
        String status=jsonObject.getString("orderStatus");
        String respMsg =jsonObject.getString("message");//商户流水

        List<ChargeOrderDetail> chargeOrderDetailList = chargeOrderDetailMapper.selectOrderNumBySupplyChannelNum(orderid);
        ChargeReport report = new ChargeReport();
        report.setChannelNum(chargeOrderDetailList.get(0).getChannelNum());
        report.setMobile(chargeOrderDetailList.get(0).getMobile());
        report.setMessage(respMsg);
        if("2".equals(status)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.鑫沐流量回执成功===手机号==="+chargeOrderDetailList.get(0).getMobile()+"===订单号:==="
                    +chargeOrderDetailList.get(0).getChannelNum()+"订单状态"+status);

        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            log.info("1.鑫沐流量回执失败===手机号==="+chargeOrderDetailList.get(0).getMobile()+"===订单号:==="
                    +chargeOrderDetailList.get(0).getChannelNum()+"订单状态"+status+"请咨询供应商！");

        }

        log.info("鑫沐成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("鑫沐成功发送消息Send message id is: " + putMsg.getMessageId());

        JSONObject map=new JSONObject();
        map.put("status","0");
        map.put("message","调用成功");
        response.getWriter().print(map);
    }
}
