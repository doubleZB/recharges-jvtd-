package com.jtd.recharge.connect.flow.feineng;

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
import java.util.List;

/**
 * Created by lihuimin on 2017/9/11.
 * 飞能流量
 */
@Controller
@RequestMapping("/return")
public class FeiNengFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    ChargeOrderDetailMapper chargeOrderDetailMapper;

    @RequestMapping("/flow/feineng")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String result = null;
        try {
            // 以流的方式接受数据
            InputStream in = request.getInputStream();
            String json = IOUtils.toString(in);
            log.info("1.飞能流量成功接收到回调，内容："+json);

            JSONObject jsonObject = JSON.parseObject(json);

            String status=jsonObject.getString("status");
            String massage=jsonObject.getString("errMsg");
            String channelNum=jsonObject.getString("orderId");
            log.info("1.飞能流量成功接收到回调，订单状态:==="+ status+"===成功接收到订单号:==="+channelNum);
            ChargeReport chargeReport = new ChargeReport();
            List<ChargeOrderDetail> chargeOrderDetail= chargeOrderDetailMapper.selectOrderNumBySupplyChannelNum(channelNum);
            chargeReport.setChannelNum(chargeOrderDetail.get(0).getChannelNum());


            if("0".equals(status)) {
                chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                log.info("1.飞能流量回执成功===订单号:==="+channelNum+"订单状态:==="+ status);
                result="result=0";
            }else{
                chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                chargeReport.setMessage(massage);
                log.info("1.飞能流量回执失败==订单号:==="+channelNum+"订单状态:==="+ massage);
                result="result=0";
            }

            log.info("2.飞能流量成功添加回执消息队列" + JSON.toJSONString(chargeReport));
            MNSClient client = MessageClient.getClient();
            CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

            Message message = new Message();
            message.setMessageBody(JSON.toJSONString(chargeReport));
            Message putMsg = queue.putMessage(message);
            log.info("3.飞能流量成功发送消息：Send message id is: " + putMsg.getMessageId());
        }catch (Exception e){
            result="result=1";
        }
        response.getWriter().print(result);
    }
}
