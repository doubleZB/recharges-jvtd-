package com.jtd.recharge.connect.flow.yuntong;

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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lyp on 2017/4/1.
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
//    private static String host = "http://120.26.67.18:8081/Submit.php";
//    private static String account = "QMKNFUEV";
//    private static String action = "Charge";
//    private static String range = "0";
//    private static String apiKey = "EirSJ8wXWk0qYELZrd2G6m4f9nNXbjYu";

    private static String host = "http://api.xiaodongchongzhi.188169.zonghejiaofei.net/api/FlowOrder/Recharge";
    private static String MemberId = "M0102";
    private static String Version = "0.1";
    private static String Secretkey = "4DDCF9903CDC41ED210DFED7935295A2";

    public static String sendFlow(String mobile, String productId) throws Exception {


        String MemberOrderId = "20150810000001";
        String GoodsId = productId;
        String Hmac = "Account" + mobile + "GoodsId" + GoodsId + "MemberId" + MemberId + "MemberOrderId" + MemberOrderId + "Secretkey" + Secretkey ;
        System.out.println(Hmac);
        String sign = DigestUtils.md5Hex(Hmac);
        System.out.println(sign);
        String data = "MemberId=" + MemberId + "&MemberOrderId=" + MemberOrderId + "&GoodsId=" + GoodsId+ "&account=" + mobile + "&Hmac=" + sign + "&Version=" + Version ;
        System.out.println(data);
        String[] ref = sendPostFlow(host, data);
        System.out.println(ref[0] + ref[1]);
        return ref[0] + ref[1];
    }


    //获取token用的
    public static String[] postHttps(String myurl, String data) {
        class TrustAnyTrustManager implements X509TrustManager {

            public void checkClientTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

        }
        class TrustAnyHostnameVerifier implements HostnameVerifier {


            public boolean verify(String hostname, SSLSession session) {
                // TODO Auto-generated method stub
                return true;
            }
        }
        String[] return2String = {"success", ""};
        OutputStreamWriter outputStreamWriter = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            long myStart = System.currentTimeMillis();
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()},
                    new SecureRandom());
            URL url = new URL(myurl);
            HttpsURLConnection connection = (HttpsURLConnection) url
                    .openConnection();
            connection.setSSLSocketFactory(sc.getSocketFactory());
            connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setRequestProperty("Content-Type", "application/xml;charset=UTF-8");

            outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");

            // write parameters
            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            // Get the response
            StringBuffer answer = new StringBuffer();
            inputStreamReader = new InputStreamReader(
                    connection.getInputStream(), "utf-8");

            bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                answer.append(line);
            }
            outputStreamWriter.close();
            bufferedReader.close();

            connection.disconnect();
            url = null;

            System.out.println("--debug get myurl used:"
                    + (System.currentTimeMillis() - myStart));
            return2String[1] = answer.toString();
            //System.out.println("return2String是:"+return2String[1].toString());
            return return2String;

        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);

            sw = null;
            pw = null;

            ex.printStackTrace();
            return2String[0] = "failed";
            return2String[1] = ex.toString();
            return return2String;
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
                    "application/x-www-form-urlencoded");


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
