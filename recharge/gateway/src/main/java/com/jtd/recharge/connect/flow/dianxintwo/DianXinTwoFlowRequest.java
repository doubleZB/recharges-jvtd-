package com.jtd.recharge.connect.flow.dianxintwo;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @autor lyp
 * 春摇 流量 充值
 */
@Service
public class DianXinTwoFlowRequest implements ConnectReqest{

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        long start =System.currentTimeMillis();
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        log.info("8、发送流程：发送供应商---电信直连2----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String sysCode = (String) supplyMap.get("sysCode");
        String appCode = (String) supplyMap.get("appCode");
        String version = (String) supplyMap.get("version");
        String attach = (String) supplyMap.get("attach");
        String method = (String) supplyMap.get("method");
        String key = (String) supplyMap.get("key");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String reqTime = sdf.format(new Date());
        SimpleDateFormat sdf2 = new SimpleDateFormat("YYMMddHHmm");
        UUID uuid = UUID.randomUUID();
        String uuidReplace = uuid.toString().replace("-","");
        String transactionId = sysCode+appCode+sdf2.format(new Date())+ uuidReplace;//报文流水号
       // String substringId = transactionId.substring(0,54);
        String sign  = DigestUtils.md5Hex(transactionId+key);
        HashMap<String , String> head=new HashMap<String,String>();
        head.put("sysCode",sysCode);
        head.put("appCode",appCode);
        head.put("transactionId",transactionId);
        head.put("reqTime",reqTime);
        head.put("method",method);
        head.put("version",version);
        head.put("attach",attach);
        head.put("sign",sign);


        String merchantOrderId = chargeRequest.getChannelNum();
        String phoneNumber = chargeRequest.getMobile();
        String prodOfferCode = chargeRequest.getPositionCode();
        String serialnum = "12";
        String verificationCode = "";
        HashMap<String ,String > biz = new HashMap<String ,String>();
        biz.put("phoneNumber",phoneNumber);
        biz.put("prodOfferCode",prodOfferCode);
        biz.put("serialnum",serialnum);
        biz.put("verificationCode",verificationCode);
        biz.put("merchantOrderId",merchantOrderId);


        HashMap map=new HashMap();
        map.put("head",head);
        map.put("biz",biz);
        log.info("8、发送流程：发送供应商---电信直连2----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(map));

        String jsonMap=JSON.toJSONString(map);
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent =sendPost(host,jsonMap);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---电信直连2----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到电信直连2！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---电信直连2----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String headResult = object.getString("head");
        JSONObject objectHead=JSON.parseObject(headResult);
        String errorCode=objectHead.getString("code");
        if(errorCode.equals("0")){
            String bizResult = object.getString("biz");
            JSONObject objectBiz = JSON.parseObject(bizResult);
            String orderID = object.getString("orderId");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);
            log.info("8、发送流程：发送供应商---电信直连2----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到电信直连2成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(errorCode);
            response.setStatusMsg(objectHead.getString("err"));
            log.info("8、发送流程：发送供应商---电信直连2----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到电信直连2失败！原因："+objectHead.getString("err"));
        }
        return response;
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(600000);
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
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
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

}
