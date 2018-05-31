package com.jtd.recharge.connect.telbill.jusutong;

import com.alibaba.fastjson.JSON;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.XMLUtil;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @autor lhm
 * 贝贝 话费 充值
 */
@Service
public class JusutongTellBillRequest implements ConnectReqest{

    // private Log log = LogFactory.getLog(this.getClass());
    private final Logger logger = LoggerFactory.getLogger(JusutongTellBillRequest.class);

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    /*
    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws DocumentException {
        long start =System.currentTimeMillis();
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        log.info("8、发送流程：发送供应商---聚速通----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String)supplyMap.get("host");
        String serialno = chargeRequest.getChannelNum();
        String uid = chargeRequest.getMobile();
        String userId = (String)supplyMap.get("userId");
        String privateKey = (String)supplyMap.get("privateKey");
        String itemId = chargeRequest.getPositionCode();//充值的流量套餐编码
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMddhhmmss");
        String dtCreate=df.format(new Date());
        String sign = DigestUtils.md5Hex(userId+itemId+uid+serialno+dtCreate+privateKey);

        Map param = new HashMap();
        param.put("userId",userId);
        param.put("itemId",itemId);
        param.put("uid",uid);
        param.put("serialno",serialno);
        param.put("dtCreate",dtCreate);
        param.put("sign",sign);

        log.info("8、发送流程：发送供应商---聚速通----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doGet(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---聚速通----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到聚速通！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---聚速通----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        Document documentHeader = DocumentHelper.parseText(resultContent);
        Element rootHeader = documentHeader.getRootElement();
        String respCode = rootHeader.elementText("code");
        if("00".equals(respCode)){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusCode(respCode);
            log.info("8、发送流程：发送供应商---聚速通----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到聚速通成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(respCode+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---聚速通----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到聚速通失败！原因："+respCode+"请咨询供应商！");
        }

        return response;
    }*/


    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        long start =System.currentTimeMillis();
        logger.info("8、发送流程：发送供应商---聚速通----mobile ={} orderNum={}",
                chargeRequest.getMobile(), chargeRequest.getChannelNum());

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        // String userId = (String) supplyMap.get("userId");
        // String key = (String) supplyMap.get("key");
        // String host = (String) supplyMap.get("host");
        // String phoneno = chargeRequest.getMobile(); // 手机号
        // String cardnum = chargeRequest.getPositionCode(); // 档案编码
        // String orderid = chargeRequest.getChannelNum();    // 订单号
        // 签名
        // String sign = DigestUtils.md5Hex(userId + phoneno + cardnum + orderid + key);

        Map<String, String> param = new HashMap<>();
        param.put("userId",String.valueOf(supplyMap.get("userId")));
        param.put("itemId",chargeRequest.getPositionCode());
        param.put("uid",chargeRequest.getMobile());
        param.put("serialno",chargeRequest.getChannelNum());
        param.put("dtCreate", DateUtil.Date2String(new Date(), "yyyyMMddHHmmss"));

        StringBuffer sb = new StringBuffer();
        Collection<String> keySet = param.keySet();
        List list = new ArrayList<>(keySet);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            sb.append(param.get(list.get(i)));
        }

        param.put("sign", DigestUtils.md5Hex(sb.append(supplyMap.get("key")).toString()));

        logger.info("8、发送流程：发送供应商---聚速通话费----mobile ={} orderNum={} 封装应用参数json报文请求参数:{}",
                chargeRequest.getMobile(), chargeRequest.getChannelNum(), JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();

        String resultContent;
        try {
            resultContent = HttpTookit.doPost(String.valueOf(supplyMap.get("host")) ,param);
        } catch (Exception e) {

            logger.error("8、发送异常：发送供应商---聚速通话费----mobile={} orderNum={} 提交到聚速通话费！原因：",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        logger.info("8、发送流程：发送供应商---聚速通话费----mobile ={} orderNum={} 返回数据：{}*******发送请求耗时：{}"
                ,chargeRequest.getMobile(),chargeRequest.getChannelNum(),resultContent,(System.currentTimeMillis()-start));

        Document document = XMLUtil.parseStringToXml(resultContent);
        if(document==null){
            throw new Exception("8.聚速通话费响应xml解析异常，返回的内容是----"+resultContent);
        }
        Element order = document.getRootElement();
        String status = order.element("status").getText();
        String code  = order.element("code").getText();
        String desc   = order.element("desc").getText();

        if ("00".equals(code)) {
            response.setStatusCode(status);
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            logger.info("8、发送流程：发送供应商---聚速通话费----mobile ={} orderNum={} 提交到聚速通话费成功！",
                    chargeRequest.getMobile(),chargeRequest.getChannelNum());
        } else {
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(code);
            response.setStatusMsg(desc + "请咨询供应商！");
            logger.info("8、发送流程：发送供应商---聚速通话费----mobile ={} orderNum={} 提交到聚速通话费失败！原因：{}请咨询供应商！",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), code);
        }
        return response;
    }
}
