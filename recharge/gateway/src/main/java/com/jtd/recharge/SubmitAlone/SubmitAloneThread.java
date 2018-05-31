package com.jtd.recharge.SubmitAlone;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ActiveMq;
import com.jtd.recharge.bean.ChargeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
public class SubmitAloneThread implements  Runnable{
    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public void run() {
        List<ChargeMessage> chargeMessageListHBJT = new ArrayList<>();
        List<ChargeMessage> chargeMessageListHBJD = new ArrayList<>();
        List<ChargeMessage> chargeMessageListHBJDXC = new ArrayList<>();
        while(1>0) {
            try {
                long start = System.currentTimeMillis();
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.SUBMIT_QUERE_ALONE);
                 int i = 0;
                while (i<200) {
                    Message popMsg = queue.popMessage();
                    if (popMsg != null) {
                        String jsonMessage = popMsg.getMessageBodyAsString();
                        queue.deleteMessage(popMsg.getReceiptHandle());
                        log.info("6、从阿里云接收数据submitAlone " + jsonMessage);
                        ChargeMessage chargeMessage = JSON.parseObject(jsonMessage, ChargeMessage.class);
                        if (chargeMessage.getSupplyList().get(0).getSupplyName().equals("flowzuowangHBJT")) {
                            chargeMessageListHBJT.add(chargeMessage);
                            //大于45就推送出去，并清空
                            if (chargeMessageListHBJT != null && chargeMessageListHBJT.size() >= 25) {
                                push(chargeMessageListHBJT);
                                chargeMessageListHBJT.clear();
                            }
                            log.info("队列收到状态报告SubmitAloneThread：" + jsonMessage +" 处理耗时：" + (System.currentTimeMillis() - start));
                        } else if (chargeMessage.getSupplyList().get(0).getSupplyName().equals("flowzuowangHBJD")) {
                            chargeMessageListHBJD.add(chargeMessage);
                            if (chargeMessageListHBJD != null && chargeMessageListHBJD.size() >= 25) {
                                push(chargeMessageListHBJD);
                                chargeMessageListHBJD.clear();
                            }
                            log.info("队列收到状态报告SubmitAloneThread：" + jsonMessage +" 处理耗时：" + (System.currentTimeMillis() - start));
                        } else if (chargeMessage.getSupplyList().get(0).getSupplyName().equals("flowzuowangHBJDXC")) {
                            chargeMessageListHBJDXC.add(chargeMessage);
                            if (chargeMessageListHBJDXC != null && chargeMessageListHBJDXC.size() >= 25) {
                                push(chargeMessageListHBJDXC);
                                chargeMessageListHBJDXC.clear();
                            }
                            log.info("队列收到状态报告SubmitAloneThread：" + jsonMessage +" 处理耗时：" + (System.currentTimeMillis() - start));
                        }
                    }
                    i++;
             }
          /*  log.info("队列收到状态报告submitAlone：处理耗时："+(System.currentTimeMillis()-start));*/
            push(chargeMessageListHBJT);
                chargeMessageListHBJT.clear();
            push(chargeMessageListHBJD);
                chargeMessageListHBJD.clear();
            push(chargeMessageListHBJDXC);
                chargeMessageListHBJDXC.clear();
            } catch (Exception e) {
                log.error("轮询线程异常"+e.getMessage(),e);
                continue;
            }
        }
    }


   public void push(List<ChargeMessage> chargeMessageList){
        if(chargeMessageList!=null&&chargeMessageList.size()>0) {
            log.info("队列收到状态报告submitAlone-chargeMessageList：" + JSON.toJSON(chargeMessageList));
            sendMessage(SysConstants.Queue.SUBMIT_QUERE_ALONE_LIST,JSON.toJSONString(chargeMessageList));
        }
    }

    public void sendMessage(String destination, final String message) {
        ActiveMq.start("admin","admin","tcp://121.43.39.80:61616",destination,message);
    }
}
