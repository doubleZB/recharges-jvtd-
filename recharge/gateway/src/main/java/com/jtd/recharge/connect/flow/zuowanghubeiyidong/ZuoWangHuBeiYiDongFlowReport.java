package com.jtd.recharge.connect.flow.zuowanghubeiyidong;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.cache.redis.RedisTemplate;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import com.jtd.recharge.dao.mapper.ChargeOrderDetailMapper;
import com.jtd.recharge.dao.po.ChargeOrderDetail;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lhm on 2017/8/28.
 * 卓望湖北移动
 */
public class ZuoWangHuBeiYiDongFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    protected  String report(String  bodyxml,String xmlhead,ChargeOrderDetailMapper chargeOrderDetailMapper) throws IOException,DocumentException {

        log.info("-----------------------------------------------------");
        log.info("变更 回调的参数body:" + bodyxml.toString());
        log.info("变更 回调的参数header:" + xmlhead.toString());

        String TransIDHTime = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date(System.currentTimeMillis()));
        long req_time = System.currentTimeMillis();//请求时间（毫秒数）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = sdf.format(new Date());
        ChargeReport report = new ChargeReport();
        String result="";
        try {
            String header="<"+xmlhead.substring(xmlhead.indexOf("<InterBOSS>")+1,xmlhead.indexOf("</InterBOSS>"))+"</InterBOSS>";
            String body = "<" + bodyxml.substring(bodyxml.indexOf("<UserResult>") + 1, bodyxml.indexOf("</UserResult>")) + "</UserResult>";
            Document documentHeader = DocumentHelper.parseText(header);
            Document documentBody = DocumentHelper.parseText(body);
            Element rootHeader = documentHeader.getRootElement();
            Element rootBody = documentBody.getRootElement();
            String TransIDO = rootHeader.element("TransInfo").elementText("TransIDO");
            String SessionID = rootHeader.element("TransInfo").elementText("SessionID");
            String TransIDOTime = rootHeader.element("TransInfo").elementText("TransIDOTime");
            String orderNo = rootBody.elementText("TransIDO");
            String Status =rootBody.elementText("Status");

            List<Element> SuccInfolist=rootBody.elements("SuccInfo");

            log.info("1.卓望湖北移动成功接收到回调，订单ID:===" + orderNo);
            if("00".equals(Status)) {
                int SuccNum = Integer.valueOf(rootBody.elementText("SuccNum"));
                int FailNum = Integer.valueOf(rootBody.elementText("FailNum"));
                    for (int i = 0; i < SuccNum; i++) {
                        Element SuccNumem = (Element) SuccInfolist.get(i);
                        String mobile = SuccNumem.elementText("SuccTel");
                        ChargeOrderDetail chargeOrderDetail = chargeOrderDetailMapper.selectOrderNumBySupplyChannelNumMobile(orderNo, mobile);
                        report.setChannelNum(chargeOrderDetail.getChannelNum());

                        report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                        MNSClient client = MessageClient.getClient();
                        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

                        Message message = new Message();
                        message.setMessageBody(JSON.toJSONString(report));
                        Message putMsg = queue.putMessage(message);
                        log.info("2.卓望湖北移动成功添加回执消息队列" + JSON.toJSONString(report));
                    }
                    List FailInfolist = rootBody.elements("FailInfo");
                    for (int i = 0; i < FailNum; i++) {
                        Element FailInfoem = (Element) FailInfolist.get(i);
                        String mobile = FailInfoem.elementText("MobNum");
                        String RspCode = FailInfoem.elementText("Rsp");
                        String RspDesc = FailInfoem.elementText("RspDesc");
                        log.info("1.卓望湖北移动成功接收到回调，订单状态:===" + RspCode + "===成功接收到订单号:===" + orderNo);
                        ChargeOrderDetail chargeOrderDetail = chargeOrderDetailMapper.selectOrderNumBySupplyChannelNumMobile(orderNo, mobile);
                        report.setChannelNum(chargeOrderDetail.getChannelNum());
                        report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                        report.setMessage(RspDesc + "请咨询供应商！");
                        MNSClient client = MessageClient.getClient();
                        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

                        Message message = new Message();
                        message.setMessageBody(JSON.toJSONString(report));
                        Message putMsg = queue.putMessage(message);
                        log.info("2.卓望湖北移动成功添加回执消息队列" + JSON.toJSONString(report));
                    }
            }else{
                List<ChargeOrderDetail> chargeOrderDetailList = chargeOrderDetailMapper.selectOrderNumBySupplyChannelNum(orderNo);
                if(chargeOrderDetailList!=null&&chargeOrderDetailList.size()>0) {
                    for (int i = 0; i < chargeOrderDetailList.size(); i++) {
                        report.setChannelNum(chargeOrderDetailList.get(i).getChannelNum());
                        report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                        MNSClient client = MessageClient.getClient();
                        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);
                        Message message = new Message();
                        message.setMessageBody(JSON.toJSONString(report));
                        Message putMsg = queue.putMessage(message);
                        log.info("2.卓望湖北移动成功添加回执消息队列" + JSON.toJSONString(report));
                    }
                }
            }

         result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<InterBOSS>" +
                "<Version>0100</Version>" +
                "<TestFlag>0</TestFlag>" +
                "<BIPType>" +
                "<BIPCode>BIP4B875</BIPCode>" +
                "<ActivityCode>T4011136</ActivityCode>" +
                "<ActionCode>1</ActionCode>" +
                "</BIPType>" +
                "<RoutingInfo>" +
                "<OrigDomain>DOMS</OrigDomain>" +
                "<RouteType>00</RouteType>" +
                "<Routing>" +
                "<HomeDomain>BBSS</HomeDomain>" +
                "<RouteValue>998</RouteValue>" +
                "</Routing>" +
                "</RoutingInfo>" +
                "<TransInfo>" +
                    "<SessionID>"+SessionID+"</SessionID>" +
                    "<TransIDO>"+TransIDO+"</TransIDO>" +
                    "<TransIDOTime>"+TransIDOTime+"</TransIDOTime>" +
                    "<TransIDH>"+orderNo+"</TransIDH>" +
                    "<TransIDHTime>"+dateString+"</TransIDHTime>" +
                "</TransInfo>" +
                "<Response>" +
                "<RspType>0</RspType>" +
                "<RspCode>0000</RspCode>" +
                "<RspDesc>Success</RspDesc>" +
                "</Response>" +
                "</InterBOSS>";
        log.info("返回内容：--"+result);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
