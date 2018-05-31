package com.jtd.recharge.base.util;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @autor jipengkun
 */
public class HttpTookit {
    private static Logger logger = Logger.getLogger(HttpTookit.class);

    private static final CloseableHttpClient httpClient;
    public static final String CHARSET = "UTF-8";

    static {
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(30000)
                .setConnectTimeout(30000)
                .setSocketTimeout(30000).build();

        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    public static String doGet(String url, Map<String, String> params) throws Exception {
        return doGetParam(url, params,CHARSET);
    }
    /**
     * @author ninghui
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws Exception
     */
    public static String doGet(String url,Map<String, String> headers, Map<String, String> params) throws Exception {
        return doGetParam(url,headers, params,CHARSET);
    }
    public static String doPost(String url, Map<String, String> params) throws Exception {
        return doPostParam(url, params,CHARSET);
    }

    /**
     * HTTP Get 发送http get带参数
     * @param url  请求URL
     * @param params	请求的参数
     * @param charset	编码格式
     * @return	返回内容
     */
    public static String doGetParam(String url, Map<String,String> params, String charset) throws Exception {
        if(StringUtils.isBlank(url)){
            return null;
        }
        if(params != null && !params.isEmpty()){
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
            for(Map.Entry<String,String> entry : params.entrySet()){
                String value = entry.getValue();
                if(value != null){
                    pairs.add(new BasicNameValuePair(entry.getKey(),value));
                }
            }
            url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
        }
        HttpGet httpGet = new HttpGet(url);
        logger.info("请求完的全路径------"+url);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != 200) {
            httpGet.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode);
        }
        HttpEntity entity = response.getEntity();
        String result = null;

        if (entity != null){
            result = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        response.close();
        return result;
    }

    public static String doGetParams(String url, Map<String,String> params,String Token,String Signature ,String charset) throws Exception {
        if(StringUtils.isBlank(url)){
            return null;
        }
        if(params != null && !params.isEmpty()){
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
            for(Map.Entry<String,String> entry : params.entrySet()){
                String value = entry.getValue();
                if(value != null){
                    pairs.add(new BasicNameValuePair(entry.getKey(),value));
                }
            }
            url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
        }
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type","application/xml");
        httpGet.setHeader("4GGOGO-Auth-Token", Token);
        httpGet.setHeader("HTTP-X-4GGOGO-Signature",Signature);
        logger.info("请求完的全路径------"+url);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != 200) {
            httpGet.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode);
        }
        HttpEntity entity = response.getEntity();
        String result = null;

        if (entity != null){
            result = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        response.close();
        return result;
    }
    /**
     * HTTP Get 发送http get带参数和header
     * @author ninghui
     * @param url  请求URL
     * @param header	请求头
     * @param params	请求的参数
     * @param charset	编码格式
     * @return	返回内容
     */
    public static String doGetParam(String url,Map<String,String> headers, Map<String,String> params, String charset) throws Exception {
        if(StringUtils.isBlank(url)){
            return null;
        }
        
        if(params != null && !params.isEmpty()){
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
            for(Map.Entry<String,String> entry : params.entrySet()){
                String value = entry.getValue();
                if(value != null){
                    pairs.add(new BasicNameValuePair(entry.getKey(),value));
                }
            }
            url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
        }
        HttpGet httpGet = new HttpGet(url);
        
        for (Map.Entry<String, String> item : headers.entrySet()) {
        	httpGet.setHeader(item.getKey(),item.getValue());
  	   }
        
        CloseableHttpResponse response = httpClient.execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != 200) {
            httpGet.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode);
        }
        HttpEntity entity = response.getEntity();
        String result = null;

        if (entity != null){
            result = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        response.close();
        return result;
    }
    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params	请求的参数
     * @param charset	编码格式
     * @return	页面内容
     */
    public static String doPostParam(String url,Map<String,String> params,String charset) throws Exception {
        if (StringUtils.isBlank(url)) {
            return null;
        }

        List<NameValuePair> pairs = null;
        if (params != null && !params.isEmpty()) {
            pairs = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
        }
        HttpPost httpPost = new HttpPost(url);
        if (pairs != null && pairs.size() > 0) {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
        }
        CloseableHttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            httpPost.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode);
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        response.close();
        return result;
    }


    /**
     * post无参数内容到指定url
     * @param url       url地址
     * @param content 参数
     * @return
     */
    public static String doPost(String url, String content, String charset) throws Exception {
        String res = null;
        HttpPost httpPost = new HttpPost(url);

        //解决中文乱码问题
        StringEntity entity = new StringEntity(content, charset);
        entity.setContentEncoding(charset);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            httpPost.abort();
            throw new Exception("HttpClient,error status code :" + statusCode);
        }

        HttpEntity respentity = response.getEntity();
        String result = null;
        if (respentity != null) {
            result = EntityUtils.toString(respentity, charset);
        }
        EntityUtils.consume(entity);
        response.close();
        return result;
    }

    /**
     * 重载增加编码格式
     * @param url url
     * @param content 请求参数
     * @return 结果
     * @throws Exception 异常
     */
    public static String doPost(String url, String content) throws Exception {
        return doPost(url,  content, "utf-8");
    }

    /**
     * post无参数内容到指定url
     * @param url       url地址
     * @param content 参数
     * @return
     */
    public static String doPost(String url, String content,String header ,String Token,String Signature) throws Exception {
        String res = null;
        HttpPost httpPost = new HttpPost(url);

//        logger.info("地址：00000"+url+content.toString());
        //解决中文乱码问题
        StringEntity entity = new StringEntity(content.toString(), "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType(header);
        httpPost.setEntity(entity);
        httpPost.setHeader("4GGOGO-Auth-Token", Token);
        httpPost.setHeader("HTTP-X-4GGOGO-Signature",Signature);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            httpPost.abort();
            throw new Exception("HttpClient,error status code :" + statusCode);
        }
        HttpEntity respentity = response.getEntity();
        String result = null;
        if (respentity != null) {
            result = EntityUtils.toString(respentity, "utf-8");
        }
        EntityUtils.consume(entity);
        response.close();
        return result;
    }

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params	请求的参数
     * @return	页面内容
     */
    public static String doPostParamLinBo(String url,Map<String,String> params,String Authorization) throws Exception {
        if (StringUtils.isBlank(url)) {
            return null;
        }

        List<NameValuePair> pairs = null;
        if (params != null && !params.isEmpty()) {
            pairs = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
        }
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Authorization",Authorization);
        if (pairs != null && pairs.size() > 0) {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
        }
        CloseableHttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            httpPost.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode);
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        response.close();
        return result;
    }
}
