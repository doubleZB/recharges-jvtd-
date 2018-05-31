package com.jtd.recharge.connect.flow.zhongye;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017-06-19.
 */
@Controller
@RequestMapping("/return")
public class ZhongyeFlowReport {

    private Log log = LogFactory.getLog(this.getClass());


    /**
     * 输入	orderId	String	平台订单号
     * 输入	respCode	String	订单状态码(1成功2失败)
     * 输入	respMsg	String	订单失败原因详情（运营商返回）
     * 输入	transNo	String	渠道交易号
     * @param orderId
     * @param respCode
     * @param respMsg
     * @param transNo
     * @return
     */
    @RequestMapping("/flow/zhongyeReport")
    @ResponseBody
    public Object zhongyeReport(String orderId ,String respCode , String respMsg , String transNo){
        String info = "1.中业状态报告：orderId ="+orderId +"  respCode="+respCode+"  respMsg="+respMsg+" transNo="+transNo;
        ChargeReport chargeReport = new ChargeReport();
        chargeReport.setChannelNum(transNo);
        chargeReport.setMessage(respMsg);

        if(respCode.equals("1")){
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.中业回执成功=====订单号:==="+transNo+"订单状态:==="+ respCode);

        }else {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            log.info("1.中业回执失败=====订单号:==="+transNo+"订单状态:==="+ respCode+"请咨询供应商！");
        }

        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(chargeReport));
        Message messageReturn =queue.putMessage(message);
        log.info("3."+info+"，状态:" + respMsg+",消息队列"+
                JSON.toJSONString(message)+",返回message="+JSON.toJSONString(messageReturn));

        return "100";
    }
}
