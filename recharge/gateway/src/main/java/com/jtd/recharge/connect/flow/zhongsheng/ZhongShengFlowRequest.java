package com.jtd.recharge.connect.flow.zhongsheng;

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
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liyabin on 2017/9/5.
 */
@Service
public class ZhongShengFlowRequest implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws DocumentException {

        log.info("8、发送流程--发送供应商---众盛---mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());
        long start =System.currentTimeMillis();

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String host = (String) supplyMap.get("host");
        String Userid = (String) supplyMap.get("Userid");
        String Flowtype=(String)supplyMap.get("Flowtype");
        String apiKey = (String) supplyMap.get("key");
        String Accountno = chargeRequest.getMobile();
        String Dockcode = chargeRequest.getPositionCode();
        String Orderno = chargeRequest.getChannelNum();
        String sign= DigestUtils.md5Hex(Userid+Orderno+Dockcode+Accountno+apiKey);

        Map param = new HashMap();
        param.put("Userid",Userid);
        param.put("Orderno",Orderno);
        param.put("Accountno",Accountno);
        param.put("Dockcode",Dockcode);
        param.put("Flowtype",Flowtype);
        param.put("Sign",sign);

        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---众盛； 封装应用参数json报文请求参数---" + JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doGet(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---众盛----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到众盛流量！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            return response;
        }
        log.info("8、发送流程：发送供应商---众盛----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String State = object.getString("State");
        String msg = object.getString("Msg");
        if("0".equals(State)){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(Orderno);
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到众盛成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(State);
            response.setStatusMsg(msg);
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到众盛失败！原因："+msg);
        }
        return response;
    }
}
