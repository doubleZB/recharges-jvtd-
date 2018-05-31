package com.jtd.recharge.connect.telbill.ziteng;

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

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 紫藤话费 lhm on 2017/6/19.
 */
public class ZiTengBillRequest implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }


    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商---紫藤话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        String cp_order_no = ZiTengBillRequest.getChannelNum();//合作商订单号，请确保订单号的唯一性
        String mobile_num = chargeRequest.getMobile();


        String positionCode = chargeRequest.getPositionCode();
        String substring = positionCode.substring(0, 1);
        String operator = null;//号码运营商
        if(substring.equals("4")){
            operator="YD";
        }else if (substring.equals("5")){
            operator="UN";
        }else if(substring.equals("6")){
            operator="DX";
        }
        String cpid = (String) supplyMap.get("cpid");//商户编号
        String trade_type = (String) supplyMap.get("trade_type");

        String notifyUrl = (String) supplyMap.get("callback_url");
        String key = (String) supplyMap.get("key");
        String host = (String) supplyMap.get("host");
        String provinceOne = "北京";//手机号码归属地
        String province =null ;
        String provinceTwo =null ;
        try {
            province = URLEncoder.encode(Base64.encodeBytes(provinceOne.getBytes("gbk")));
            provinceTwo = Base64.encodeBytes(provinceOne.getBytes("gbk"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("8.紫藤话费加密后的编码===="+province);
        log.info("8.紫藤话费加密后的编码provinceTwo===="+provinceTwo );
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String create_time=formatter.format(new Date());

        String packageSize = chargeRequest.getPackageSize();
        String amount = packageSize+"00";
        String date = "cpid="+cpid+"&trade_type="+trade_type+"&operator="+operator+"&province="+provinceTwo+"&create_time="+create_time+
                "&mobile_num="+mobile_num+"&cp_order_no="+cp_order_no+"&amount="+amount+"&ret_para="+key;
        log.info("8.紫藤话费加密前的sign==date=="+date);

        String sign = DigestUtils.md5Hex("cpid="+cpid+"&trade_type="+trade_type+"&operator="+operator+"&province="+provinceTwo+"&create_time="+create_time+
                "&mobile_num="+mobile_num+"&cp_order_no="+cp_order_no+"&amount="+amount+"&ret_para="+key);
        log.info("8.紫藤话费加密后的sign===="+sign);

        Map param = new HashMap();
        param.put("cpid",cpid);
        param.put("trade_type",trade_type);
        param.put("operator",operator);
        param.put("province",provinceTwo);
        param.put("create_time",create_time);
        param.put("mobile_num",mobile_num);
        param.put("cp_order_no",cp_order_no);
        param.put("amount",amount);
        param.put("sign",sign);

        String resultContent = "";

        ChargeSubmitResponse response = new ChargeSubmitResponse();

        log.info("8、发送流程：发送供应商---紫藤话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));
        try {
            resultContent = HttpTookit.doGetParam(host,param,"gbk");

        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---紫藤话费----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到紫藤话费！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            return response;
        }
        log.info("8、发送流程：发送供应商---紫藤话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject json= JSON.parseObject(resultContent);

        String userOrderNoResult = (String) json.get("cp_order_no");//合作商订单号
        String orderStatus = (String) json.get("ret_result");//订单状态，10000为创建订单成功 10001创建失败


        if("0000".equals(orderStatus)) {
            log.info("提交成功");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
            log.info("8、发送流程：发送供应商---紫藤话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到紫藤话费成功！");

        } else {
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(orderStatus);
            response.setStatusMsg(orderStatus+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---紫藤话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到紫藤话费失败！原因："+orderStatus+"请咨询供应商！");
        }
        return response;
    }

    /**
     * 生成定单号
     * @return
     */
    public static String getChannelNum() {
        String orderNum = "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String datestr =formatter.format(new Date());
        orderNum = "c" + datestr;
        return orderNum;
    }
}
