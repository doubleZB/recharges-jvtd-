package com.jtd.recharge.connect.flow.xinmu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 鑫沐 on 2016/12/28.
 */
@Service
public class XinMuFlowRequest implements ConnectReqest {


    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }
    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商---鑫沐----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        String host = (String)supplyMap.get("host");
        String method = (String)supplyMap.get("method");
        String SecretKey = (String)supplyMap.get("SecretKey");
        String cp_id = (String)supplyMap.get("cpid");
        String prorange = (String)supplyMap.get("prorange");
        String belong = (String)supplyMap.get("belong");

        String phone = chargeRequest.getMobile();
        String flowValue = chargeRequest.getPositionCode();
        String customParm = chargeRequest.getChannelNum();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = formatter.format(new java.util.Date());
        JSONObject paramJson = new JSONObject();
        // 订购相关参数
        paramJson.put("cp_id", cp_id);
        paramJson.put("flow", flowValue);
        paramJson.put("prorange",prorange);//流量的使用范围0全国1省内
        paramJson.put("belong", belong);//流量包归属地0全国1省内
        paramJson.put("usermobile", phone);

        Map<String, String> signatureMap = new HashMap<String, String>();
        signatureMap.put("cp_id", cp_id); // 接口分配
        signatureMap.put("SecretKey", SecretKey); // 接口分配，不参与排序
        signatureMap.put("method", method); // 请求结果参数
        signatureMap.put("timestamp", date); // 请求结果参数
        if (host.indexOf("orderflow?") < 0) {
            host += "orderflow?";
        }
        String url = ToolsUtil.getUrl(JSONUtil.jsonToMapString(paramJson
                .toString()), signatureMap, host);
        log.info("8、发送流程：发送供应商---鑫沐----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(signatureMap)+JSON.toJSONString(paramJson)+url);
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent =reqTest(url, paramJson.toString());
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---鑫沐----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到鑫沐流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---鑫沐----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String statusCode = object.getString("status");
        if(statusCode.equals("0000")){
            JSONObject order=object.getJSONObject("result");
            String orderNo=order.getString("orderid");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(customParm);
            response.setSupplyChannelNum(orderNo);
            log.info("8、发送流程：发送供应商---鑫沐----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到鑫沐成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(statusCode);
            response.setStatusMsg(statusCode+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---鑫沐----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到鑫沐失败！原因："+statusCode+"请咨询供应商！");

        }
        return response;
    }

    /**
     * 链接接口获得返回值
     * */
    public static String reqTest(String url, String param2) {
        String bodyAsString = "";
        DefaultHttpClient HC = new DefaultHttpClient();// 新建客户端
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity reqEntity = new StringEntity(param2, "UTF-8");
            reqEntity.setContentType("text/plain; charset=utf-8;");
            reqEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(reqEntity);
            HttpResponse response = HC.execute(httpPost);
            HttpEntity entity = response.getEntity();
            bodyAsString = EntityUtils.toString(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bodyAsString;
    }
}
