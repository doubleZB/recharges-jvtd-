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
 * @autor jipengkun
 */
public class BJYiDong {
    private static  String ChannelIDs ="QMKNFUEV";
    private static  String CAs="19XWBQP7";
    private static  String AdminNames="admin";
    private static  String Passwords="zM7KKA3Q";
    public static void main(String[] args) {
        String endpoint = "http://221.179.129.226/test_servicepacket/services/TfllbService";
        Tea tea=new Tea();
        String ChannelID = tea.encryptByTea(ChannelIDs);
        String CA = tea.encryptByTea(CAs);
        String AdminName = tea.encryptByTea(AdminNames);
        String Password = tea.encryptByTea(Passwords);
        String PhoneList = tea.encryptByTea("13810810155");//提交手机号
        String ResName =tea.encryptByTea("130YDJB");//资源名称
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String RequestTime = tea.encryptByTea(sdf.format(new Date()));
        String xmlParams="<WebRequest>\n" +
                "<Header>\n" +
                "<ChannelID>"+ChannelID+"</ChannelID>\n" +
                "<RequestTime>"+RequestTime+"</RequestTime>\n" +
                "</Header>\n" +
                "<WebBody>\n" +
                "<Params>\n" +
                "<CA>"+CA+"</CA>\n" +
                "<AdminName>"+AdminName+"</AdminName>\n" +
                "<Password>"+Password+"</Password>\n" +
                "<ResName>"+ResName+"</ResName>\n" +
                "<PhoneList>"+PhoneList+"</PhoneList>\n" +
                "</Params>\n" +
                "</WebBody>\n" +
                "</WebRequest>";
        Service service = new Service();
        Call call;
        String result;
        try {
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);

            call.setOperationName("tf_tjzd");

            String res = (String) call.invoke(new Object[]{xmlParams}); // 返回String，传入参数
            Document document = DocumentHelper.parseText(res);
            Element root = document.getRootElement();
            String RetCode= root.element("Header").elementText("RetCode");
            String RetDesc= root.element("Header").elementText("RetDesc");
            String OrderId= root.element("WebBody").element("RetInfo").elementText("OrderId");
            System.out.println("----" + res);
            System.out.println("RetCode----" + RetCode);
            System.out.println("RetDesc----" + RetDesc);
            System.out.println("OrderId----" + OrderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
