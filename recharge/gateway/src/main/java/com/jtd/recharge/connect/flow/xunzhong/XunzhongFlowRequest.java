package com.jtd.recharge.connect.flow.xunzhong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 讯众 on 2016/12/28.
 */
@Service
public class XunzhongFlowRequest implements ConnectReqest {


    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }
    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商---讯众----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        String accountSID = (String)supplyMap.get("accountSID");
        String authToken = (String)supplyMap.get("authToken");
        String version = (String)supplyMap.get("version");
        String func = (String)supplyMap.get("func");
        String funcURL = (String)supplyMap.get("funcURL");
        String action = (String)supplyMap.get("action");
        String appid = (String)supplyMap.get("appid");
        String effectStartTime = (String)supplyMap.get("effectStartTime");
        String effectTime = (String)supplyMap.get("effectTime");


        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = formatter.format(new java.util.Date());
        String Authorization = getBase64(accountSID + "|" + date);
        String Sign = DigestUtils.md5Hex(accountSID + authToken + date);
        String host = (String)supplyMap.get("host") + version + "/sid/" + accountSID
                + "/" + func + "/" + funcURL + "?Sign=" + Sign;

        String phone = chargeRequest.getMobile();
        String flowValue = chargeRequest.getPositionCode();
        String customParm = chargeRequest.getChannelNum();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("action", action);
        map.put("appid", appid);
        map.put("phone", phone);
        map.put("flowCode", flowValue);
        map.put("customParm", customParm);

        String param = JSON.toJSONString(map);
        log.info("8、发送流程：发送供应商---讯众----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(map));
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = sendPost(host, param, Authorization);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---讯众----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到讯众流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---讯众----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String statusCode = object.getString("statusCode");
        if(statusCode.equals("0")){
            String orderNo=object.getString("requestId");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderNo);
            log.info("8、发送流程：发送供应商---讯众----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到讯众成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(statusCode);
            response.setStatusMsg(statusCode+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---讯众----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到讯众失败！原因："+statusCode+"请咨询供应商！");

        }
        return response;
    }

    // 加密
    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    /**
     *讯众需要添加头部信息所以需要自己写一个Post的方法
     * @param url
     * @param param
     * @param Authorization
     * @return
     * @throws Exception
     */
    public  String sendPost(String url, String param, String Authorization) throws Exception{

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type",
                    "application/json;charset=UTF-8");
            conn.setRequestProperty("Authorization", Authorization);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(300000);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("链接异常"+e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
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
        return result;
    }
}
