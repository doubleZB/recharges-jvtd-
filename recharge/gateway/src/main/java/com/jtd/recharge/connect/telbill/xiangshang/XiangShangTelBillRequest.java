package com.jtd.recharge.connect.telbill.xiangshang;

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

import java.util.*;

/**
 * 向上话费充值
 */
public class XiangShangTelBillRequest implements ConnectReqest {

    private final Logger logger = LoggerFactory.getLogger(XiangShangTelBillRequest.class);

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    public static void main(String[] args) {
        String sign = "deno10macidhzxsorderidc2018020116432483856951phone15925609210time1517536926Nmd38mAhN1t4GPSHItbIkwf88HVbqEDN";
        System.out.println(DigestUtils.md5Hex(sign));
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        long start =System.currentTimeMillis();
        logger.info("1、发送流程：发送供应商---向上话费----mobile ={} orderNum={}",
                chargeRequest.getMobile(), chargeRequest.getChannelNum());

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        String userId = (String) supplyMap.get("userId");
        String key = (String) supplyMap.get("key");
        String host = (String) supplyMap.get("host");
        String phoneno = chargeRequest.getMobile(); // 手机号
        // String cardnum = chargeRequest.getPositionCode(); // 档案编码
        String orderid = chargeRequest.getChannelNum();    // 订单号
        long time = new Date().getTime() / 1000; // 精确到秒

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("deno", chargeRequest.getPackageSize().replaceAll("元",""));
        paramMap.put("macid", userId);
        // paramMap.put("method", "1");
        paramMap.put("orderid", orderid);
        paramMap.put("phone", phoneno);
        paramMap.put("time", String.valueOf(time));

        // 签名前排序
        StringBuffer sb = new StringBuffer();
        Collection<String> keyset = paramMap.keySet();
        List list = new ArrayList<>(keyset);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            sb.append(list.get(i)).append(paramMap.get(list.get(i)));
        }
        sb.append(key);
        // 签名
        logger.info("签名排序后的字符串:{}", sb.toString());
        paramMap.put("sign", DigestUtils.md5Hex(sb.toString()));
        paramMap.put("encryptType", "MD5");

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent;
        try {
            logger.info("向上话费==请求参数: {}", paramMap);
            resultContent = HttpTookit.doPost(host, paramMap);
        } catch (Exception e) {
            logger.error("2、发送异常：发送供应商---向上话费----mobile={} orderNum={} 提交到向上话费！原因：",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");

            return response;
        }

        logger.info("3、发送流程：发送供应商---向上话费----mobile ={} orderNum={} 返回数据：{}*******发送请求耗时：{}"
                ,chargeRequest.getMobile(),chargeRequest.getChannelNum(),resultContent,(System.currentTimeMillis()-start));

        Document document = XMLUtil.parseStringToXml(resultContent);
        if(document==null){
            throw new Exception("8.向上话费响应xml解析异常，返回的内容是----"+resultContent);
        }
        Element order = document.getRootElement();
        String status = order.element("errcode").getText();
        String desc  = order.element("errinfo").getText();

        if ("OrderSended".equals(status)) {
            response.setStatusCode(status);
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            logger.info("4、发送流程：发送供应商---向上话费----mobile ={} orderNum={} 提交到向上话费成功！",
                    chargeRequest.getMobile(),chargeRequest.getChannelNum());
        } else {
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(status);
            response.setStatusMsg(desc + "请咨询供应商！");
            logger.info("5、发送流程：发送供应商---向上话费----mobile ={} orderNum={} 提交到向上话费失败！原因：{}请咨询供应商！",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), desc);
        }

        return response;
    }
}
