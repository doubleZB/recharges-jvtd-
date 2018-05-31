package com.jtd.recharge.connect.flow.dianxin;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.huateng.util.MacUtil;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @autor jipengkun
 * 电信回执
 */
@Controller
@RequestMapping("/return")
public class DianXinFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/dianxin")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String C0_OrderId = request.getParameter("C0_OrderId");
        String C1_SerNum = request.getParameter("C1_SerNum");
        String C2_Code = request.getParameter("C2_Code");
        String C3_InOrderNo = request.getParameter("C3_InOrderNo");
        String C4_Mobile = request.getParameter("C4_Mobile");
        String C5_TxnAmt = request.getParameter("C5_TxnAmt");
        String C6_ReturnCode = request.getParameter("C6_ReturnCode");
        String C7_ResponseCode = request.getParameter("C7_ResponseCode");
        String C8_RechargeFlowAmount = request.getParameter("C8_RechargeFlowAmount");
        String hmac = request.getParameter("hmac");

        log.info("1.电信直连成功接收到回调，订单状态:==="+ C6_ReturnCode+"===成功接收到订单号:==="+C0_OrderId+"===手机号==="+C4_Mobile+
                " C1_SerNum------"+C1_SerNum+"   C2_Code------"+C2_Code+"  C3_InOrderNo------"+C3_InOrderNo+" C5_TxnAmt------"+C5_TxnAmt+
                " C6_ReturnCode------"+C6_ReturnCode+"  C7_ResponseCode------"+C7_ResponseCode+"  C8_RechargeFlowAmount------"+C8_RechargeFlowAmount+"  hmac------"+hmac);

        String macContent = C0_OrderId+"|"+C1_SerNum+"|"+C2_Code+"|"+C3_InOrderNo
                +"|"+C4_Mobile+"|"+C5_TxnAmt+"|"+C6_ReturnCode+"|"+C7_ResponseCode+"|"+C8_RechargeFlowAmount;
        String key = "3135383838303334";
        try {
            String mackey = MacUtil.genMac(macContent,key);
            if(!mackey.equals(hmac)) {
                response.getWriter().print("fail");
            }else {
                response.getWriter().print("success");
            }
        } catch (Exception e) {
            log.error("1.电信直连 genmac fail",e);
        }


        ChargeReport report = new ChargeReport();

        report.setMobile(C4_Mobile);
        report.setChannelNum(C0_OrderId);

        if("success".equals(C6_ReturnCode)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.电信直连回执成功===手机号==="+C4_Mobile+"===订单号:==="+C0_OrderId+"状态："+C6_ReturnCode);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(C6_ReturnCode+"请咨询供应商");
            log.info("1.电信直连回执失败===手机号==="+C4_Mobile+"===订单号:==="+C0_OrderId+"状态："+C6_ReturnCode+"请咨询供应商");

        }

        log.info("2.电信直连成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.电信直连成功发送消息Send message id is: " + putMsg.getMessageId());
    }
}
