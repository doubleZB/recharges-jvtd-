package com.jtd.recharge.schedule.activeQuery;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.bmcc.service.pub.util.Tea;

import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.dao.mapper.ChargeOrderMapper;
import com.jtd.recharge.dao.po.ChargeOrder;
import com.jtd.recharge.schedule.bean.ChargeReport;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lyp on 2017/4/13.
 */
public class BJYDActiveSelect {
    private Log log = LogFactory.getLog(this.getClass());
    private static  String ChannelIDs ="QMKNFUEV";
    private static  String CAs="19XWBQP7";
    private static  String AdminNames="admin";
    private static  String Passwords="zM7KKA3Q";
    private static  String endpoint = "http://221.179.129.226/test_servicepacket/services/TfllbService";
    @Resource
    private ChargeOrderMapper chargeOrderMapper;

    public void activeQuery(){
        selectBJYDOrder(11);
    }

    public void selectBJYDOrder(int supplyId){

        ChargeOrder chargeOrder=new ChargeOrder();
        chargeOrder.setSupplyId(supplyId);
        chargeOrder.setStatus(ChargeOrder.OrderStatus.CHARGEING);
        chargeOrder.setTable("charge_order");
        List<ChargeOrder> list= chargeOrderMapper.getUserOrder(chargeOrder);
        log.info("统计次数："+list.size());
        if(list.size()>0){
            for(ChargeOrder order:list) {
                log.info("查询出的订单号："+order.getOrderNum()+"手机号："+order.getRechargeMobile());
                String OrderId = order.getOrderNum();
                String PhoneNumber = order.getRechargeMobile();
                String[] resultStr = sendSelectBJYDOrder(OrderId,PhoneNumber);
                ChargeReport report = new ChargeReport();
                report.setMobile(PhoneNumber);
                report.setChannelNum(OrderId);
                if("3".equals(resultStr[1])) {
                    report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                    addMNSClient(report);
                } else if("4".equals(resultStr[1])) {
                    report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                    addMNSClient(report);
                }
            }
        }
    }



    public String[] sendSelectBJYDOrder(String OrderId,String PhoneNumber ){
        String result[]=new String[2];
        log.info("北京移动主动查询开始===========");
        Tea tea = new Tea();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String CurrentPage = "1";
        String Number = "1";
        String ChannelID = tea.encryptByTea(ChannelIDs);
        String CA = tea.encryptByTea(CAs);
        String AdminName = tea.encryptByTea(AdminNames);
        String Password = tea.encryptByTea(Passwords);
        String RequestTime = tea.encryptByTea(sdf.format(new Date()));
        String xmlParams = "<WebRequest>\n" +
                "<Header>\n" +
                "<ChannelID>" + ChannelID + "</ChannelID>\n" +
                "<RequestTime>" + RequestTime + "</RequestTime>\n" +
                "</Header>\n" +
                "<WebBody>\n" +
                "<Params>\n" +
                "<CA>" + CA + "</CA>\n" +
                "<AdminName>" + AdminName + "</AdminName>\n" +
                "<Password>" + Password + "</Password>\n" +
                "<PhoneNumber>" + PhoneNumber + "</PhoneNumber>\n" +
                "<OrderId>" + OrderId + "</OrderId>\n" +
                "<CurrentPage>" + CurrentPage + "</CurrentPage>\n" +
                "<Number>" + Number + "</Number>\n" +
                "</Params>\n" +
                "</WebBody>\n" +
                "</WebRequest>";
        Service service = new Service();
        Call call;
        String RetCode="";
        String RetDesc="";
        String State="";
        try {
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);

            call.setOperationName("tf_ddmxcx");

            String res = (String) call.invoke(new Object[]{xmlParams}); // 返回String，传入参数
            System.out.println("----" + res);
            Document document = DocumentHelper.parseText(res);
            Element root = document.getRootElement();
            RetCode= root.element("Header").elementText("RetCode");
            RetDesc= root.element("Header").elementText("RetDesc");
            State= root.element("WebBody").element("RetInfo").element("PhoneList").elementText("State");
            result[0]=RetCode;
            result[1]=State;
        } catch (Exception e) {
            log.error("北京移动查询问题"+e);
        }
        return result;
    }

    public void addMNSClient(ChargeReport report){
        log.info("BJYD添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("BJYD Send message id is: " + putMsg.getMessageId());

    }


}
