package com.jtd.recharge.connect.flow.hubeiyidong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpClients;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @autor lyp
 * 湖北移动 流量 充值
 */
@Service
public class HuBeiYiDongFlowRequest implements ConnectReqest{

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception{
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        long start =System.currentTimeMillis();
        log.info("8、发送流程---发送供应商---湖北移动--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        //准备供应商参数
        String host = (String) supplyMap.get("host");

        String msgsender = (String)supplyMap.get("msgsender");
        String SubscriberID = (String)supplyMap.get("SubscriberID");

        String BIPCode = (String)supplyMap.get("BIPCode");
        String ProcessTime = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String ProcID =chargeRequest.getChannelNum() ;
        String mobile = chargeRequest.getMobile();
        String data = chargeRequest.getPositionCode();



        //封装参数
        String param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<InterBOSS>\n" +
                "<OrigDomain></OrigDomain>\n" +
                "<HomeDomain></HomeDomain>\n" +
                "<BIPCode>"+BIPCode+"</BIPCode>\n" +
                "<BIPVer>0100</BIPVer>\n" +
                "<ActivityCode>T8012002</ActivityCode>\n" +
                "<ActionCode>0</ActionCode>\n" +
                "<Routing>\n" +
                "<RouteType>01</RouteType>\n" +
                "<RouteValue>"+mobile+"</RouteValue>\n" +
                "</Routing>\n" +
                "<ProcID>"+ProcID+"</ProcID>\n" +
                "<TransIDO>"+ProcID+"</TransIDO>\n" +
                "<TransIDH></TransIDH>\n" +
                "<ProcessTime>"+ProcessTime+"</ProcessTime>\n" +
                "<Response>\n" +
                "<RspType></RspType>\n" +
                "<RspCode></RspCode>\n" +
                "<RspDesc></RspDesc>\n" +
                "</Response>\n" +
                "<SPReserve>\n" +
                "<CutOffDay></CutOffDay>\n" +
                "</SPReserve>\n" +
                "<TestFlag>1</TestFlag>\n" +
                "<MsgSender>"+msgsender+"</MsgSender>\n" +
                "<MsgReceiver>LLFF</MsgReceiver>\n" +
                "<SvcContVer>0100</SvcContVer>\n" +
                "<SvcCont><![CDATA[<SyncInfoRep><UProductInfo><SubscriberID>"+SubscriberID+"</SubscriberID><OprCode>01</OprCode><MobNum>"+mobile+"</MobNum>\n" +
                "<IncProdID>"+data+"</IncProdID><Effective>01</Effective><OprTime>"+ProcessTime+"</OprTime></UProductInfo></SyncInfoRep>]]></SvcCont>\n" +
                "</InterBOSS>" ;
        log.info("8、发送流程---发送供应商---湖北移动--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" +JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpClients.doPost(host,param,"application/xml");
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---湖北移动----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到湖北移动！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---湖北移动封装应用参数json报文请求参数---" +JSON.toJSONString(param)+"   ------返回数据：" + resultContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));

        Document document = DocumentHelper.parseText(resultContent);
        Element root = document.getRootElement();
        String resCode = root.element("Response").elementText("RspCode");
        if(resCode.equals("00")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(ProcID);
            log.info("8、发送流程：发送供应商---湖北移动----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到湖北移动成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(resCode);
            response.setStatusMsg(resCode+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---湖北移动----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到湖北移动失败！原因："+resCode+"请咨询供应商！");

        }
        return response;
    }
}
