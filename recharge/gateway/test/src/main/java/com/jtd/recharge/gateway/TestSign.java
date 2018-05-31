package com.jtd.recharge.gateway;

import com.asiainfo.openplatform.common.util.SecurityUtils;
import com.asiainfo.openplatform.common.util.SignUtil;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.SmsUtil;
import com.jtd.recharge.connect.flow.henanyidong.TokenSingleton;
import com.jtd.recharge.connect.video.SupplyConfig;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TestSign {

    private final Logger logger = LoggerFactory.getLogger(TestSign.class);

    @Test
    public void testWanhuichong(){
        Map supplyMap = (Map) SupplyConfig.getConfig().get("telbillwanhuichong");
        // 请求参数
        Map<String, String> param = new HashMap<>();
        param.put("name", String.valueOf(supplyMap.get("userId"))); // 用户名
        param.put("nonceStr", String.valueOf(SmsUtil.getRandNum(1,999999)));  // 随机字符串
        param.put("timeStamp", String.valueOf(new Date().getTime())); // 时间戳
        param.put("phoneNumber", "18140020271");    // 充值手机号
        // param.put("faceValue",  "0");  //商品面值
        // todo 签名
        StringBuffer sb = new StringBuffer();
        Collection<String> keyset = param.keySet();
        List list = new ArrayList<>(keyset);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            if (i > 0) sb.append("&");
            sb.append(list.get(i)).append("=").append(param.get(list.get(i)));
        }
        sb.append("&key=").append(String.valueOf(supplyMap.get("key")));
        logger.info("待签名字符串: {}", sb.toString());
        param.put("sign", DigestUtils.md5Hex(sb.toString()).toUpperCase());
        logger.info("万惠充请求参数：{}", param);

        try {
            logger.info(HttpTookit.doPost("http://www.wanhuichong.com/api/order/rechargelist", param));
        } catch (Exception e) {
            logger.error("-失败原因{}", e.getMessage());

        }
    }


    @Test
    public void testJusutong() {
        Map supplyMap = (Map) SupplyConfig.getConfig().get("telbilljusutong");

        Map<String, String> param = new HashMap<>();
        param.put("userId",String.valueOf(supplyMap.get("userId")));
        param.put("itemId","16815");
        param.put("uid","18610068465");
        param.put("serialno","c2018030711244549043746");
        param.put("dtCreate", DateUtil.Date2String(new Date(), "yyyyMMddHHmmss"));

        StringBuffer sb = new StringBuffer();
        Collection<String> keyset = param.keySet();
        List list = new ArrayList<>(keyset);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            sb.append(param.get(list.get(i)));
        }

        logger.info("签名字符串: {}", sb.append(supplyMap.get("key")));
        param.put("sign", DigestUtils.md5Hex(sb.toString()));

        try {
            String resultContent = HttpTookit.doPost(String.valueOf(supplyMap.get("host")) ,param);
            logger.info("请求成功:响应 {}", resultContent);
        } catch (Exception e) {

            logger.error(e.getMessage());
        }
    }

    @Test
    public void testXinyou() {
        // 请求参数
        Map<String, String> param = new HashMap<>();
        param.put("userId", "369");
        param.put("itemId", "10045");
        // param.put("itemPrice", "10");
        param.put("uid", "18310037758");
        param.put("serialno", "c2018031411400524875575");
        param.put("dtCreate", DateUtil.Date2String(new Date(), "yyyyMMddHHmmss"));

        // 签名
        StringBuffer sb = new StringBuffer();
        Collection<String> keyset = param.keySet();
        List list = new ArrayList<>(keyset);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            sb.append(param.get(list.get(i)));
        }
        sb.append("de8160f6740cd670e0a35fb10f0088ca80d4bfea0a23f807efbc397f597c694f");
        param.put("sign", DigestUtils.md5Hex(sb.toString()));

        String resultContent;
        // 充值请求
        try {
            logger.info("信游流量充值请求参数：{}", param);
            resultContent = HttpTookit.doGet("http://59.110.154.15:6160/unicomAync/buy.do", param);
            logger.info("信游流量充值请求成功，响应数据：{} 耗时:", resultContent);
        } catch (Exception e) {
            logger.error("发送异常：发送供应商---信游流量----失败原因{}",e.getLocalizedMessage());

        }
    }

    @Test
    public void testZhuoWang() {
        // 请求参数
        Map<String, String> param = new HashMap<>();
        param.put("userId", "3100921");
        param.put("itemId", "10045");
        param.put("serialno", "c2018031411400524875575");
        param.put("dtCreate", DateUtil.Date2String(new Date(), "yyyyMMddHHmmss"));
        param.put("uid", "18310037758");

        // 签名
        StringBuffer sb = new StringBuffer();
        Collection<String> keyset = param.keySet();
        List list = new ArrayList<>(keyset);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            sb.append(param.get(list.get(i)));
        }
        sb.append("70e0a35fb10f0088ca80d4bfea0a23f807e");
        param.put("sign", DigestUtils.md5Hex(sb.toString()));

        String resultContent;
        // 充值请求
        try {
            logger.info("请求参数：{}", param);
            resultContent = HttpTookit.doPost("http://111.13.21.172/llyx-srcresult/flowCashResult/notify", param);
            logger.info("响应数据：{} 耗时:", resultContent);
        } catch (Exception e) {
            logger.error("失败原因{}",e.getLocalizedMessage());

        }
    }

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Test
    public void testHenanyidong() throws Exception {

        Map supplyMap = (Map) config.get("flowhenanyidong");
        // 业务参数
        JSONObject busiParam = new JSONObject();
        // busiParam.put("GBILL_ID", supplyMap.get("gbill_id"));
        // busiParam.put("FLAG", "2"); // 操作标识
        busiParam.put("BILL_ID", "15838023153");
        // busiParam.put("VALID_MONTH", "1"); // 赠送流量有效期 固定值 1
        // busiParam.put("VALID_TYPE", "1"); // 赠送流量有效期 填1代表立即生效，2代表次月生效。
        // busiParam.put("MEM_SRVPKG", "H"); // 成员资费包。
        // busiParam.put("CUST_ORDER_ID", "o2018032116450319998994"); // 订单流水。
        // busiParam.put("SMS_TEMPLATE", "0"); // 短信模板。

        logger.info("河南移动流量充值，签名钱字符串：{}", busiParam.toString());
        // 签名
        String entity = SecurityUtils.encodeAES256HexUpper(busiParam.toString(),
                SecurityUtils.decodeHexUpper(String.valueOf(supplyMap.get("app_key"))));
        String accessToken = TokenSingleton
                .getInstance(String.valueOf(supplyMap.get("access_token_url"))).getMap().get("access_token");

        // 系统参数
        Map<String, String> sysParam = new HashMap<>();
        sysParam.put("method", "QRY_GRP_BALANCE");
        sysParam.put("format", "json");
        sysParam.put("appId", String.valueOf(supplyMap.get("app_id")));
        sysParam.put("version", "1.0");
        sysParam.put("accessToken", accessToken);
        sysParam.put("timestamp", DateUtil.Date2String(new Date(), "yyyyMMddHHmmss"));
        sysParam.put("busiSerial", "1");

        String sign = SignUtil.sign(sysParam, entity, "HmacSHA256", String.valueOf(supplyMap.get("app_key")));
        logger.info("签名后的字符串:{}", sign);

        StringBuffer host = new StringBuffer(supplyMap.get("host").toString());
        host.append("?method=QRY_GRP_BALANCE").append("&format=json").append("&appId=")
                .append(String.valueOf(supplyMap.get("app_id"))).append("&version=1.0").append("&accessToken=")
                .append(accessToken).append("&sign=").append(sign).append("&timestamp=")
                .append(sysParam.get("timestamp")).append("&busiSerial=1");
        logger.info("请求地址host值: {}", host);

        String resultContent;
        try {
            resultContent = HttpTookit.doPost(host.toString(), entity, "utf-8");
            logger.info("请求响应{}", resultContent);
        }catch (Exception e) {
            logger.error("原因：{}请咨询供应商！", e.getMessage());
        }
    }

}
