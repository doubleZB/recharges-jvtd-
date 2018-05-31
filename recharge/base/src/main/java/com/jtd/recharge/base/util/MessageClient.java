package com.jtd.recharge.base.util;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.MNSClient;

import java.util.Properties;

/**
 * @autor jipengkun
 */
public class MessageClient {

    private static MNSClient client;

    private MessageClient() {}

    static  {
        Properties properties = PropertiesUtils.loadProperties("config.properties");

        CloudAccount account = new CloudAccount
                (properties.getProperty("AccessKey"),
                properties.getProperty("AccessKeySecret"),
                properties.getProperty("Endpoint"));
        client = account.getMNSClient();
    }

    public static MNSClient getClient() {
        return client;
    }

    public static void setClient(MNSClient client) {
        MessageClient.client = client;
    }
}
