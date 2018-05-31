package com.jtd.recharge.connect.telbill.xunyin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.icu.text.SimpleDateFormat;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lhm on 2017/9/27
 * 迅银话费
 */
@Service
public class XunYinTelBillRequest implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws DocumentException {
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商---迅银话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");//充值地址
        String buyerIp = (String) supplyMap.get("IP");
        String cpid = (String) supplyMap.get("cpid");
        String key=(String) supplyMap.get("key");
        String gamegoodid=(String) supplyMap.get("gamegoodid");
        String returnurl = (String) supplyMap.get("callback_url");
        String account = chargeRequest.getMobile();//手机号码
        String buyvalue = chargeRequest.getPositionCode();

        String createtime  = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String orderid=chargeRequest.getChannelNum();
        String buynum="1";
        String sign = DigestUtils.md5Hex("cpid="+cpid+"&gamegoodid="+gamegoodid+"&createtime="+createtime+"&account="+account+"&orderid="+orderid+"&buynum="+buynum+key);

        Map param = new HashMap();
        param.put("cpid",cpid);
        param.put("gamegoodid",gamegoodid);
        param.put("createtime",createtime);
        param.put("account",account);
        param.put("orderid",orderid);
        param.put("buynum",buynum);
        param.put("buyvalue",buyvalue);
        param.put("buyerIp",buyerIp);
        param.put("returnurl",returnurl);
        param.put("sign",sign);

        log.info("8、发送流程：发送供应商---迅银话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---迅银话费----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到迅银话费！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---迅银话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        Document document = DocumentHelper.parseText(resultContent);
        Element root = document.getRootElement();
        String code = root.elementText("Code");
        if("0000".equals(code)){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderid);
            log.info("8、发送流程：发送供应商---迅银话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到迅银话费成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(code);
            response.setStatusMsg("请咨询供应商！");
            log.info("8、发送流程：发送供应商---迅银话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到迅银话费失败！原因："+code+"请咨询供应商！");
        }
        return response;
    }
}
