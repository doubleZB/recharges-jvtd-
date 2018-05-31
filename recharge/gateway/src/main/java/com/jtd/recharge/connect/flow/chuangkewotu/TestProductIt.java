package com.jtd.recharge.connect.flow.chuangkewotu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/3/31.
 */
public class TestProductIt {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        //String timestamp=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date());

        String str=sendFlow("15989380345","202120");
        System.out.println(str);

    }
    public static String host="https://api.ourpaas.com/api/flowsWholesale/flowProduct";
    public static String authToken ="3ce0a5abfb8648ac8a5d0dbf8c087072";
    public static String appKey ="c221512d7fea41dd8d225d71a3c2906f";
    public static String sendFlow(String mobile ,String productId) throws Exception{

        String account=mobile;
        String productNo=productId;
        String orderSn = System.currentTimeMillis()+"";
        String callBackUrl = "www.xxxxxx.com";


        String data="authToken="+authToken+"&appKey="+appKey;
        String[] resultContent = sendPostFlow(host,data);


       return resultContent[1];
    }
    public static String[] sendPostFlow(String url, Object param) {
        String[] strArr={"200",""};
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
            String resultCode=conn.getResponseCode()+"";
            System.out.println(conn.getResponseCode());
            strArr[0]=resultCode;
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("充值错误信息："+e);
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
        strArr[1]=result;

        return strArr;
    }








}
