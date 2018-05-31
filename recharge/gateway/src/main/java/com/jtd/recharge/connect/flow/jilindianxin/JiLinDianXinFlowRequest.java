package com.jtd.recharge.connect.flow.jilindianxin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @autor lyp
 * jilindianxin 流量 充值 该接口为同步接口提交成功就代表充值成功
 */
@Service
public class JiLinDianXinFlowRequest implements ConnectReqest{

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
        log.info("8、发送流程---发送供应商---吉林电信--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String staffCode = (String) supplyMap.get("staffCode");
        String IV = (String) supplyMap.get("IV");
        IVSTR =IV;
        String sign = (String) supplyMap.get("token");

        String userid = chargeRequest.getMobile();
        String pricePlanCd = chargeRequest.getPositionCode();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String transactionId = JiLinDianXinFlowRequest.getOrderNum();

        JSONObject reqJson=new JSONObject();
        reqJson.put("reqTime", sdf.format(new Date()));
        reqJson.put("staffCode",staffCode);
        reqJson.put("transactionId",transactionId);
        reqJson.put("userid",userid);
        reqJson.put("pricePlanCd",pricePlanCd);
        reqJson.put("sign",Encrypt(DigestUtils.md5Hex((userid+staffCode+sign).toUpperCase()),sign));


        log.info("8、发送流程---发送供应商---吉林电信--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" +reqJson.toString());

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,reqJson.toString());
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---吉林电信----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到吉林电信！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程--发送供应商---吉林电信--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  封装应用参数json报文请求参数---" +reqJson.toString()+"   ------返回数据：" + resultContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("result");
        String resultMsg=object.getString("resultMsg");
        if(respCode.equals("0")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
            ChargeReport chargeReport = new ChargeReport();
            chargeReport.setChannelNum(chargeRequest.getChannelNum());
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("8.吉林电信提交成功添加回执消息队列mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum() + JSON.toJSONString(chargeReport));
            MNSClient client = MessageClient.getClient();
            CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

            Message message = new Message();
            message.setMessageBody(JSON.toJSONString(chargeReport));
            Message putMsg = queue.putMessage(message);
            log.info("10.吉林电信发送Send message id is: " + putMsg.getMessageId());
        }else{
            log.info("8.吉林电信提交失败直接回调失败请咨询供应商！"+resultMsg);
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(resultMsg);
            response.setStatusCode(respCode);
        }

        return response;
    }
    private static  String IVSTR = "4350846580396358";
    // AES加密
    public static String Encrypt( String sSrc ,String Skey){
        try{
            if(sSrc==null || sSrc.length()<2){
                return null;
            }
            if( Skey == null ) {
                System.out.print( "Key为空null" );
                return null;
            }
            // 判断Key是否为16位
            if( Skey.length() != 16 ) {
                System.out.print( "Key长度不是16位" );
                return null;
            }
            byte[] raw = Skey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec( raw, "AES" );
            Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5Padding" );// "算法/模式/补码方式"
            IvParameterSpec iv = new IvParameterSpec( IVSTR.getBytes() );// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init( Cipher.ENCRYPT_MODE, skeySpec, iv );
            byte[] encrypted = cipher.doFinal( sSrc.getBytes() );

            return encodeBytes( encrypted );
        }catch(Exception ex){
            return null;
        }

    }

    public static String encodeBytes(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
            strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
        }

        return strBuf.toString();
    }

    /**
     * 生成定单号
     * @return
     */
    public static String getOrderNum() {
        String orderNum = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String datestr =formatter.format(new Date());
        String random  = RandomStringUtils.randomNumeric(10);
        orderNum = datestr + random;
        return orderNum;
    }
}
