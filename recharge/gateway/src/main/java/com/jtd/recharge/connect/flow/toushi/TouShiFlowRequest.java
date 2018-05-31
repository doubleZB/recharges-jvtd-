package com.jtd.recharge.connect.flow.toushi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
/**
 * Created by lyp on 2017/3/30.
 * 投石流量充值
 */
@Service
public class TouShiFlowRequest implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商---投石流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");//充值地址
        String userid = (String) supplyMap.get("userid");
        String pwd = (String) supplyMap.get("pwd");
        String key = (String) supplyMap.get("key");
        String area = (String) supplyMap.get("area");
        String effecttime = (String) supplyMap.get("effecttime");
        String validity = (String) supplyMap.get("validity");
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMddhhmmss");
        String times=df.format(new Date());
        String orderid = chargeRequest.getChannelNum();//订单号
        String account = chargeRequest.getMobile();//手机号码
        String gprs = chargeRequest.getPositionCode();//流量大小
        String userkey=DigestUtils.md5Hex("userid"+userid+"pwd"+pwd+"orderid"
                +orderid+"account"+account+"gprs"+gprs+"area"+area+"effecttime"+effecttime
                +"validity"+validity+"times"+times+key);

        String data="?userid="+userid+"&pwd="+pwd+"&orderid="+orderid+"&account="+account
                +"&gprs="+gprs+"&area="+area+"&effecttime="+effecttime+
                "&validity="+validity+"&times="+times+"&userkey="+userkey;

        log.info("8、发送流程：发送供应商---投石流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+data);

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = getHttpFromUrl(host+data);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---投石流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到投石流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---投石流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        Element root=null;
        try {
            Document document = DocumentHelper.parseText(resultContent);
            root = document.getRootElement();
        } catch (Exception e) {
            log.error("投石流量异常提交",e);
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            return response;
        }
        String error = root.elementText("error");
        String state = root.elementText("state");
        if(error.equals("0")&&state.equals("0")){
            String orderID=root.elementText("orderid");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);
            log.info("8、发送流程：发送供应商---投石流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到投石流量成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(error);
            response.setStatusMsg(error+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---投石流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到投石流量失败！原因："+error+"请咨询供应商！");
        }
        return response;
    }

    public static String getHttpFromUrl(String myUrl) {
        System.out.println(myUrl);
        StringBuffer sb = new StringBuffer();

        URL url = null;
        InputStream in = null;
        BufferedReader breader = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(myUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);

            if (connection.getResponseCode() == 200) {
                in = connection.getInputStream();
                breader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String str = breader.readLine();
                while (str != null) {
                    sb.append(str);
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

        return sb.toString();
    }

}
