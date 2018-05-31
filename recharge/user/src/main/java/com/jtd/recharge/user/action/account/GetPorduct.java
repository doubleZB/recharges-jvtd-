package com.jtd.recharge.user.action.account;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
/**
 * Created by lyp on 2017/3/31.
 */
@Controller
@RequestMapping("/get")
public class GetPorduct {
    private Log log = LogFactory.getLog(this.getClass());
    private static String hsot="https://tj.4ggogo.com/web-in/auth.html";
    private static String AppKey ="b2d5ceed54f84611a8e9a83b6805b76d";
    private static String AppSecret="86a6952fc74a482f8b937e75bfb2edac";
    private static  String sendHost="https://tj.4ggogo.com/web-in/products.html";
    @RequestMapping("/Product")
    @ResponseBody
    public String getProduct ()throws Exception{
        String token = getToken();
        String signs =DigestUtils.sha256Hex(AppSecret);
        String str=getHttpFromUrl(sendHost,token,signs);
        log.info("获取的产品编码："+str);
        return str;
    }

    public static String getToken() throws Exception{

        String timestamp=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date());


        String sign= DigestUtils.sha256Hex(AppKey+timestamp+AppSecret);

        String xml="<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
                "<Request>" +
                "<Datetime>"+timestamp+"</Datetime>" +
                "<Authorization>" +
                "<AppKey>"+AppKey+"</AppKey>" +
                "<Sign>"+sign+"</Sign>" +
                "</Authorization>" +
                "</Request>";


        String res=sendPost(hsot, xml);
        Document document = DocumentHelper.parseText(res);
        Element root = document.getRootElement();

        String Token = root.element("Authorization").elementText("Token");;

        return Token;
    }


    //获取token用的
    public static String sendPost(String url, Object param) {
        System.out.println(param);
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);

            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("Content-Type",
                    "application/xml");

            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new PrintWriter(conn.getOutputStream());

            out.print(param);

            out.flush();

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("错误信息："+e);
            e.printStackTrace();
        }
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static String getHttpFromUrl(String myurl,String token ,String SIGNATURE) throws Exception{

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
            connection.setRequestProperty("Content-Type",
                    "application/xml");
            connection.setRequestProperty("4GGOGO-Auth-Token",token);
            connection.setRequestProperty("HTTP-X-4GGOGO-Signature",SIGNATURE);

            if (connection.getResponseCode() == 200) {

                in = connection.getInputStream();
                breader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                String str = breader.readLine();
                while (str != null) {
                    result+=str;
                    str = breader.readLine();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if(breader!=null){breader.close();}

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if( in!=null){ in.close();}

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
