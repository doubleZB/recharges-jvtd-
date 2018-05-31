package src.main.java.com.jtd.recharge.queue;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;

/**
 * @autor jipengkun
 */
public class TestMnsProduct {

    public static void main(String[] args) {
        String endpoint = "http://35048883.mns.cn-hangzhou.aliyuncs.com/";

        CloudAccount account = new CloudAccount("voReRBgHCHRM77Yq", "NskojIP12N2edavVuta9Nec0ndBrb4", endpoint);
        MNSClient client = account.getMNSClient(); // 在程序中，CloudAccount以及MNSClient单例实现即可，多线程安全
        try {
            CloudQueue queue = client.getQueueRef("charge-submit-rd");
            Message message = new Message();
            message.setMessageBody("I am test message ");
            Message putMsg = queue.putMessage(message);
            System.out.println("Send message id is: " + putMsg.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.close();  // 程序退出时，需主动调用client的close方法进行资源释放
    }
}
