package com.jtd.recharge.connect.flow.hubeiyidong;

import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by lyp on 2017/4/1.
 *湖北移动测试类
 */
public class Test {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        //String timestamp=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date());

        String str = sendFlow("18511903034", "202564");
          System.out.println(str);
    }

    private static String host = "http://111.47.240.159/hbydllff_ws_outer/service/rest/llff/LLF8F002";
    private static String MemberId = "M0102";
    private static String Version = "0.1";
    private static String Secretkey = "4DDCF9903CDC41ED210DFED7935295A2";

    public static String sendFlow(String mobile, String productId) throws Exception {



        String data = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<InterBOSS>\n" +
                "<OrigDomain></OrigDomain>\n" +
                "<HomeDomain></HomeDomain>\n" +
                "<BIPCode>LLF8F002</BIPCode>\n" +
                "<BIPVer>0100</BIPVer>\n" +
                "<ActivityCode>T8012002</ActivityCode>\n" +
                "<ActionCode>0</ActionCode>\n" +
                "<Routing>\n" +
                "<RouteType>01</RouteType>\n" +
                "<RouteValue>18627712045</RouteValue>\n" +
                "</Routing>\n" +
                "<ProcID>EC123456789</ProcID>\n" +
                "<TransIDO>EC4555854449</TransIDO>\n" +
                "<TransIDH></TransIDH>\n" +
                "<ProcessTime>20170615145100</ProcessTime>\n" +
                "<Response>\n" +
                "<RspType></RspType>\n" +
                "<RspCode></RspCode>\n" +
                "<RspDesc></RspDesc>\n" +
                "</Response>\n" +
                "<SPReserve>\n" +
                "<CutOffDay></CutOffDay>\n" +
                "</SPReserve>\n" +
                "<TestFlag>1</TestFlag>\n" +
                "<MsgSender>group_0003</MsgSender>\n" +
                "<MsgReceiver>LLFF</MsgReceiver>\n" +
                "<SvcContVer>0100</SvcContVer>\n" +
                "<SvcCont><![CDATA[<SyncInfoRep><UProductInfo><SubscriberID>BOSS_ORDER_0000005</SubscriberID><OprCode>01</OprCode><MobNum>18627712045</MobNum>\n" +
                "<IncProdID>BOSS_0001</IncProdID><Effective>01</Effective><OprTime>20170615145100</OprTime></UProductInfo></SyncInfoRep>]]></SvcCont>\n" +
                "</InterBOSS>" ;
        System.out.println(data);
        String[] ref = sendPostFlow(host, data);
        Document document = DocumentHelper.parseText(ref[1]);
        Element root = document.getRootElement();
        String appId = root.elementText("ProcessTime");
        String ActivityCode = root.elementText("ActivityCode");
        String resCode = root.element("Response").elementText("RspCode");
        System.out.println(ref[0] + ref[1]);
        System.out.println(appId+"::" +ActivityCode+"::"+resCode);

        return ref[0] + ref[1];
    }





    //充值用的
    public static String[] sendPostFlow(String url, String param) {
        String[] strArr = {"200", ""};
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("Content-Type",
                    "application/xml");


            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new PrintWriter(conn.getOutputStream());

            out.print(param);

            out.flush();
            String resultCode = conn.getResponseCode() + "";
            System.out.println(conn.getResponseCode());
            strArr[0] = resultCode;
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("充值错误信息：" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        strArr[1] = result;

        return strArr;
    }


    public static String getHttpFromUrl(String myurl, String token, String SIGNATURE) {
        System.out.println(myurl);
        System.out.println(token);
        System.out.println(SIGNATURE);
        String result = "";

        URL url = null;
        InputStream in = null;
        BufferedReader breader = null;
        HttpURLConnection connection = null;

        try {

            url = new URL(myurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);

            connection.setRequestProperty("4GGOGO-Auth-Token", token);
            connection.setRequestProperty("HTTP-X-4GGOGO-Signature", SIGNATURE);

            if (connection.getResponseCode() == 200) {

                in = connection.getInputStream();
                breader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String str = breader.readLine();
                while (str != null) {
                    result += str;
                    str = breader.readLine();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                breader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            breader = null;
            in = null;
            url = null;
            connection = null;
        }

        return result;
    }


}
