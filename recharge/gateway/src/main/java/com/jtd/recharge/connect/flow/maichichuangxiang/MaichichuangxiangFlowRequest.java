package com.jtd.recharge.connect.flow.maichichuangxiang;

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
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liyabin on 2017/9/11.
 */
@Service
public class MaichichuangxiangFlowRequest implements ConnectReqest {

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        long start =System.currentTimeMillis();
        log.info("8、发送流程--发送供应商---麦驰创想--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        //准备供应商参数
        String host = (String) supplyMap.get("host");

        String CorpLoginName = (String)supplyMap.get("CorpLoginName");
        String CorpPassword = (String)supplyMap.get("CorpPassword");
        String CorpID = (String)supplyMap.get("CorpID");
        String AppDevice_ID = (String)supplyMap.get("AppDevice_ID");
        String key = (String)supplyMap.get("key");
        String ActivityName=(String)supplyMap.get("ActivityName");
        String DataType ="0";//流量包类型
        String StationType="1";//类型-本地


        String streamNO =getOrderNum();
        String userID = chargeRequest.getMobile();
        DesUtil desUtil=new DesUtil();
        String date="streamNO="+streamNO
                +"&CorpLoginName="+CorpLoginName+"&CorpPassword="+CorpPassword+"&CorpID="+CorpID+"&DataID=&DataType="+DataType+"&Sponsorid="
                +"&StationType="+StationType+"&userID="+userID
                +"&volume="+chargeRequest.getPackageSize()+"&sendtype=3"
                +"&PorCode=&MessCode=&validType=1&validMonth=1&ActivityName="+ActivityName;
        log.info("--------------date:"+date);
        String sign = desUtil.encrypt(date,key);

        //封装参数
        String param="{\"OperCode\":\"12\",\"AppDevice_ID\":\""+AppDevice_ID+"\",\"EncodeStr\":\""+sign+"\"}";
        log.info("param:"+param);
        log.info("8、发送流程---发送供应商---麦驰创想--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" +JSONObject.fromObject(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---麦驰创想----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到麦驰创想！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---麦驰创想---"+ JSONObject.fromObject(param)+"   ------返回数据：" + resultContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=  JSONObject.fromObject(resultContent);
        String result=object.getString("EncodeStr");
        result=desUtil.decrypt(result,key);
        log.info(result);
        String[]  res=result.split("&");
        Map<String,String> maps=new HashMap<String,String>();
        for(int i=0;i<res.length;i++){
            String[] mapzhi=res[i].split("=");
            if(mapzhi.length>1) {
                maps.put(mapzhi[0], mapzhi[1]);
            }else{
                maps.put(mapzhi[0],"null");
            }
        }
        String retCode=maps.get("resultCode");
        String ret_msg=maps.get("resultDesc");
        if(retCode.equals("0")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
            log.info("8、发送流程：发送供应商---麦驰创想----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到麦驰创想成功！");

            ChargeReport chargeReport = new ChargeReport();
            chargeReport.setChannelNum(chargeRequest.getChannelNum());
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("8.麦驰创想提交成功添加回执消息队列mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum() + com.alibaba.fastjson.JSON.toJSONString(chargeReport));
            MNSClient client = MessageClient.getClient();
            CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

            Message message = new Message();
            message.setMessageBody(com.alibaba.fastjson.JSON.toJSONString(chargeReport));
            Message putMsg = queue.putMessage(message);
            log.info("10.麦驰创想发送Send message id is: " + putMsg.getMessageId());
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(retCode);
            response.setStatusMsg(ret_msg+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---麦驰创想----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到麦驰创想失败！原因："+ret_msg+"请咨询供应商！");
        }
        return response;
    }

    /**
     * 生成定单号
     * @return
     */
    public static String getOrderNum() {
        String orderNum = "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String datestr =formatter.format(new Date());

        String random  = RandomStringUtils.randomNumeric(4);

        orderNum =datestr + random;
        return orderNum;
    }
}
