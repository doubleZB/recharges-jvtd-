package src.main.java.com.jtd.recharge.queue;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;

/**
 * @autor jipengkun
 */
public class TestMnsConsumer {

    public static void main(String[] args) {
        String endpoint = "http://35048883.mns.cn-hangzhou.aliyuncs.com/";

        CloudAccount account = new CloudAccount("voReRBgHCHRM77Yq", "NskojIP12N2edavVuta9Nec0ndBrb4", endpoint);
        MNSClient client = account.getMNSClient(); // 在程序中，CloudAccount以及MNSClient单例实现即可，多线程安全

        CloudQueue queue = client.getQueueRef("flow-submit");

        Message popMsg = queue.popMessage();
        if (popMsg != null){
            System.out.println("message handle: " + popMsg.getReceiptHandle());
            System.out.println("message body: " + popMsg.getMessageBodyAsString());
            System.out.println("message id: " + popMsg.getMessageId());
            System.out.println("message dequeue count:" + popMsg.getDequeueCount());
            //<<to add your special logic.>>

            //remember to  delete message when consume message successfully.
            queue.deleteMessage(popMsg.getReceiptHandle());
            System.out.println("delete message successfully.\n");
        }
    }
}
