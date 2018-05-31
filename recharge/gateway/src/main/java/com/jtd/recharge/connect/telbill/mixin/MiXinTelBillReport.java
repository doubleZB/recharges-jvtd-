package com.jtd.recharge.connect.telbill.mixin;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 米鑫话费回调接口
 * Created by Administrator on 2017-06-21.
 */
@Controller
@RequestMapping("/return")
public class MiXinTelBillReport {

    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/telbill/mixinTelBillReport")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        //合作商订单号
        String sporderid=request.getParameter("sporderid");
        //系统订单号
        String orderid=request.getParameter("orderid");
        //订单状态(success：成功；fail：失败)
        String status=request.getParameter("status");
        //完成金额，单位：元
        String finishmoney=request.getParameter("finishmoney");
        //时间戳
        String finishtime=request.getParameter("finishtime");
        //MD5(orderId+ serialId+ orderStatus+ finishMon+ finishTime+key)
        String sign=request.getParameter("sign");
        if(!DigestUtils.md5Hex(sporderid + orderid + status + finishmoney + finishtime +"afevjaewej").equals(sign)){
            log.info("1.收到米鑫话费状态报告非法数据：sporderid ="+sporderid +", orderid=" + orderid+", status=" + status +", finishmoney=" + finishmoney  +", finishtime=" + finishtime);
            response.getWriter().print("非法数据！");
            return;
        }
        log.info("1.收到米鑫话费状态报告数据：sporderid ="+sporderid +", orderid=" + orderid+", status=" + status +", finishmoney=" + finishmoney  +", finishtime=" + finishtime);
        ChargeReport report = new ChargeReport();

        report.setChannelNum(sporderid);
        if("success".equals(status)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            report.setMessage("回执成功");
            log.info("1.米鑫话费回执成功====订单号:==="+orderid+"status=" + status);
        }else{
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(status+"请咨询供应商！");
            log.info("1.米鑫话费回执失败====订单号:==="+orderid+"status=" + status+"请咨询供应商！");

        }



        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.米鑫话费添加回执消息队列" + JSON.toJSONString(report)+"  message id is: " + putMsg.getMessageId());

        response.getWriter().print("success");
    }
}
