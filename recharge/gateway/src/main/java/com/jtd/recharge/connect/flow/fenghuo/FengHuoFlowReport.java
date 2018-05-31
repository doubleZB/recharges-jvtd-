package com.jtd.recharge.connect.flow.fenghuo;

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
import java.util.Map;

/**
 * Created by lihuimin on 2017/9/25.
 * 烽火流量
 */
@Controller
@RequestMapping("/return")
public class FengHuoFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    ChargeOrderDetailMapper chargeOrderDetailMapper;

    @RequestMapping("/flow/fenghuo")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String rspCode = null;
        String transId = null;
        try {
            // 以流的方式接受数据
            InputStream in = request.getInputStream();
            String json = IOUtils.toString(in);
            log.info("1.烽火流量成功接收到回调，内容："+json);

            JSONObject jsonObject = JSON.parseObject(json);

            transId = jsonObject.getString("transId");

            JSONArray orderList = jsonObject.getJSONArray("orderList");
            log.info("1.烽火流量成功接收到回调 jsonArray report，内容orderList："+orderList);
            for(int i=0;i<orderList.size();i++){
                JSONObject  ObjectTwo= orderList.getJSONObject(i);
                String channelNum = ObjectTwo.getString("channelOrderId");
                String status = ObjectTwo.getString("orderStatus");
                String massage = ObjectTwo.getString("orderRspMsg");
                log.info("1.烽火流量成功接收到回调，订单状态:==="+ status+"===成功接收到订单号:==="+channelNum);
                ChargeReport chargeReport = new ChargeReport();

                chargeReport.setChannelNum(channelNum);

                if("4".equals(status)) {
                    chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                    log.info("1.烽火流量回执成功===订单号:==="+channelNum+"订单状态:==="+ status);
                    rspCode="R00";
                }else{
                    chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                    chargeReport.setMessage(massage);
                    log.info("1.烽火流量回执失败==订单号:==="+channelNum+"订单状态:==="+ massage);
                    rspCode="R01";
                }

                log.info("2.烽火流量成功添加回执消息队列" + JSON.toJSONString(chargeReport));
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

                Message message = new Message();
                message.setMessageBody(JSON.toJSONString(chargeReport));
                Message putMsg = queue.putMessage(message);
                log.info("3.烽火流量成功发送消息：Send message id is: " + putMsg.getMessageId());
            }
            Map<String, String> param = new HashMap<String, String>();
            param.put("transId",transId);
            param.put("rspCode",rspCode);

            log.info("3.烽火流量成功发送消息响应内容：" + JSON.toJSONString(param));
            response.getWriter().print(JSON.toJSONString(param));
        }catch (Exception e){
            log.error(e);
        }
    }
}
