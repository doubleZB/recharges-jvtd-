package com.jtd.recharge.connect.flow.zuowanghubeiyidong;

import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.SmsUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/8/28.
 */
public class Test {

    /*public static void main(String[] args) throws Exception {
            for(int i=0;i<1000;i++){
                push();
                System.out.print("循环第"+i+"次");
                Thread.sleep(3000);
            }
    }*/

    public static void main(String[] args) throws InterruptedException,
            ExecutionException {

        final ExecutorService exec = Executors.newFixedThreadPool(1);

        Callable<String> call = new Callable<String>() {
            @Override
            public String call() throws Exception {
                //开始执行耗时操作
                Thread.sleep(100 * 5);
                return "线程执行完成.";
            }
        };

        try {
            Future<String> future = exec.submit(call);
            String obj = future.get(1000 * 1, TimeUnit.MILLISECONDS); //任务处理超时时间设为 1 秒
            System.out.println("任务成功返回:" + obj);
        } catch (TimeoutException ex) {
            System.out.println("处理超时啦....");
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println("处理失败.");
            e.printStackTrace();
        }
        // 关闭线程池
        exec.shutdown();
    }

    public static  void push() throws IOException, DocumentException {
        String urlStr = "http://111.13.21.128:80/ec_serv_intf/forec";

        String Version = "0100";
        String TestFlag = "1"; // 测试为1,生产为0
        String BIPCode = "BIP4B876"; // 业务编码
        String MobNum = "13562063926";// 手机号码
        String MobNums = "13562063927";// 手机号码
        String ActivityCode = "T4011137"; // 交易编码
        String ActionCode = "0"; // 0 请求 1 应答

        String RouteType = "00";
        String OrigDomain = "DOMS"; // 发起方应用域代码 由BBOSS分配
        String HomeDomain = "BBSS"; // 归属方应用域代码 由BBOSS分配
        String RouteValue = "998"; // 路由类型对应的关键值，由BBOSS分配
        String SessionID = String.valueOf(System.currentTimeMillis());
        String TransIDOTime = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        // 服务代码
        String TransIDO = "HBJT" + TransIDOTime + System.currentTimeMillis() / 10;
        String UserPackage = "10"; // 流量

        String appKey = "JUTDCYKJYXG@HB";
        String appSecret = "eSrxtEOp98QhmGjoRj86Kcvs";
        String ProductID = "9003776378";

        int nponce = SmsUtil.getRandNum(0, 4);
        String created = DateUtil.dateTz();
        String passwordDigest = Base64.encodeBase64String(getSHA256(nponce + created + appSecret).getBytes());
        String nponces = Base64.encodeBase64String(String.valueOf(nponce).getBytes());
        //鉴权
        String auth = "WSSE realm=\"DOMS\", profile=\"UsernameToken\", type=\"AppKey\"";
        String wsse = "UsernameToken Username=\"" + appKey + "\", PasswordDigest=\"" + passwordDigest + "\", Nonce=\"" + nponces
                + "\", Created=\"" + created + "\"";


        System.out.println("Authorization:" + auth + " X-WSSE: " + wsse);
        // String agent_serial_number = req_trans_no;

        CloseableHttpClient httpclient = HttpClients.createDefault();

        StringBuffer xmlHeaderBuffer = new StringBuffer();
        StringBuffer xmlDateBuffer = new StringBuffer();

        xmlHeaderBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlHeaderBuffer.append("<InterBOSS>");
        xmlHeaderBuffer.append("<Version>").append(Version).append("</Version>");
        xmlHeaderBuffer.append("<TestFlag>").append(TestFlag).append("</TestFlag>");
        xmlHeaderBuffer.append("<BIPType>");
        xmlHeaderBuffer.append("<BIPCode>").append(BIPCode).append("</BIPCode>");
        xmlHeaderBuffer.append("<ActivityCode>").append(ActivityCode).append("</ActivityCode>");
        xmlHeaderBuffer.append("<ActionCode>").append(ActionCode).append("</ActionCode>");
        xmlHeaderBuffer.append("</BIPType>");
        xmlHeaderBuffer.append("<RoutingInfo>");
        xmlHeaderBuffer.append("<OrigDomain>").append(OrigDomain).append("</OrigDomain>");
        xmlHeaderBuffer.append("<RouteType>").append(RouteType).append("</RouteType>");
        xmlHeaderBuffer.append("<Routing>");
        xmlHeaderBuffer.append("<HomeDomain>").append(HomeDomain).append("</HomeDomain>");
        xmlHeaderBuffer.append("<RouteValue>").append(RouteValue).append("</RouteValue>");
        xmlHeaderBuffer.append("</Routing>");
        xmlHeaderBuffer.append("</RoutingInfo>");
        xmlHeaderBuffer.append("<TransInfo>");
        xmlHeaderBuffer.append("<SessionID>").append(SessionID).append("</SessionID>");
        xmlHeaderBuffer.append("<TransIDO>").append(TransIDO).append("</TransIDO>");
        xmlHeaderBuffer.append("<TransIDOTime>").append(TransIDOTime).append("</TransIDOTime>");
        xmlHeaderBuffer.append("</TransInfo>");
        xmlHeaderBuffer.append("</InterBOSS>");

        xmlDateBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlDateBuffer.append("<InterBOSS>").append("<SvcCont>").append("<![CDATA[");
        xmlDateBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlDateBuffer.append("<AdditionInfo>");
        xmlDateBuffer.append("<ProductID>").append(UserPackage).append("</ProductID>");
        xmlDateBuffer.append("<UserData>");
        xmlDateBuffer.append("<MobNum>").append(MobNum).append("</MobNum>");
        xmlDateBuffer.append("<UserPackage>").append(UserPackage).append("</UserPackage>");
        xmlDateBuffer.append("</UserData>");
        xmlDateBuffer.append("<UserData>");
        xmlDateBuffer.append("<MobNum>").append(MobNums).append("</MobNum>");
        xmlDateBuffer.append("<UserPackage>").append(UserPackage).append("</UserPackage>");
        xmlDateBuffer.append("</UserData>");
        xmlDateBuffer.append("</AdditionInfo>").append("]]></SvcCont>");
        xmlDateBuffer.append("</InterBOSS>");

        String headerStr = xmlHeaderBuffer.toString();
        String bodyStr = xmlDateBuffer.toString();

        System.out.println("REQUEST to " + urlStr);
        System.out.println("request header is: \n" + headerStr);
        System.out.println("request body is: \n" + bodyStr);
        System.out.println("==============================");

        // 应答内容
        String inContent = "";
        HttpPost httppost = new HttpPost(urlStr);
        StringBody header = new StringBody(headerStr);
        StringBody body = new StringBody(bodyStr);

        HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("xmlhead", header).addPart("xmlbody", body)
                .build();

        httppost.setEntity(reqEntity);
        httppost.setHeader("Authorization", auth);
        httppost.setHeader("X-WSSE", wsse);
        httppost.setHeader("PRODID", DigestUtils.md5Hex(ProductID));
        httppost.setHeader("ACTCODE", ActivityCode);
        System.out.println("executing request " + httppost.getRequestLine());
        CloseableHttpResponse response = httpclient.execute(httppost);
        System.out.println("--------------server response headers------------");
        HeaderIterator hi = response.headerIterator();
        while (hi.hasNext()) {
            Header h = hi.nextHeader();
            System.out.println("name=" + h.getName() + "; value=" + h.getValue());
        }
        System.out.println("-------------end of response headers-------------");

        System.out.println("--------------server response entity------------");
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
            inContent = new String(EntityUtils.toString(resEntity).getBytes("iso8859-1"), "GBK");
            System.out.println("inContent==" + inContent);


        }

        Document document = DocumentHelper.parseText(inContent);
        Element root = document.getRootElement();
        String resCode = root.element("Response").elementText("RspDesc");
        String RspCode = root.element("Response").elementText("RspCode");
        System.out.print("--resCode---" + resCode + "--RspCode--" + RspCode);


        System.out.println("-------------end of response entity-------------");
        EntityUtils.consume(resEntity);
    }

    public static String getSHA256(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}
