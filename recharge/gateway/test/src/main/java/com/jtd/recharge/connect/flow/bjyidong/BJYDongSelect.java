package com.jtd.recharge.connect.flow.bjyidong;

import com.bmcc.service.pub.util.Tea;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/14.
 */
public class BJYDongSelect {


    private static  String ChannelIDs ="QMKNFUEV";
    private static  String CAs="19XWBQP7";
    private static  String AdminNames="admin";
    private static  String Passwords="zM7KKA3Q";
    public static void main(String[] args) {
        String endpoint = "http://221.179.129.226/test_servicepacket/services/TfllbService";
        Tea tea = new Tea();
        String OrderId = "3355";
        String PhoneNumber = "13810810155";
        String CurrentPage = "1";
        String Number = "1";
        String ChannelID = tea.encryptByTea(ChannelIDs);
        String CA = tea.encryptByTea(CAs);
        String AdminName = tea.encryptByTea(AdminNames);
        String Password = tea.encryptByTea(Passwords);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String RequestTime = tea.encryptByTea(sdf.format(new Date()));
        String xmlParams = "<WebRequest>\n" +
                "<Header>\n" +
                "<ChannelID>" + ChannelID + "</ChannelID>\n" +
                "<RequestTime>" + RequestTime + "</RequestTime>\n" +
                "</Header>\n" +
                "<WebBody>\n" +
                "<Params>\n" +
                "<CA>" + CA + "</CA>\n" +
                "<AdminName>" + AdminName + "</AdminName>\n" +
                "<Password>" + Password + "</Password>\n" +
                "<PhoneNumber>" + PhoneNumber + "</PhoneNumber>\n" +
                "<OrderId>" + OrderId + "</OrderId>\n" +
                "<CurrentPage>" + CurrentPage + "</CurrentPage>\n" +
                "<Number>" + Number + "</Number>\n" +
                "</Params>\n" +
                "</WebBody>\n" +
                "</WebRequest>";
        System.out.print("查询发送====="+xmlParams);
        Service service = new Service();
        Call call;
        try {
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);

            call.setOperationName("tf_ddmxcx");

            String res = (String) call.invoke(new Object[]{xmlParams}); // 返回String，传入参数
            System.out.println("----" + res);
            Document document = DocumentHelper.parseText(res);
            Element root = document.getRootElement();
            String RetCode= root.element("Header").elementText("RetCode");
            String RetDesc= root.element("Header").elementText("RetDesc");
            String State= root.element("WebBody").element("RetInfo").element("PhoneList").elementText("State");

            System.out.println("RetCode----" + RetCode);
            System.out.println("RetDesc----" + RetDesc);
            System.out.println("OrderId----" + State);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
