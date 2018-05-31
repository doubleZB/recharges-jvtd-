package com.jtd.recharge.gateway;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeMessage;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.bean.MqReceive;
import com.jtd.recharge.connect.video.SupplyConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {"classpath*:spring/spring*.xml"})
public class TestVide {

    // @Resource
    // private XuanJieVideoRequest xuanJieVideoRequest;

    private final Logger logger = LoggerFactory.getLogger(TestVide.class);

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Test
    public void testXuanJieVideo() throws Exception {

        ChargeRequest chargeRequest = new ChargeRequest();
        chargeRequest.setChannelNum("c2018028317285441");
        chargeRequest.setSupplyId(136);
        chargeRequest.setMobile("zou_shuanglin@163.com");
        chargeRequest.setSupplyName("videoxuanjie");
        chargeRequest.setPositionCode("7");
        chargeRequest.setOperator(1002);
        chargeRequest.setPackageSize("7");
        chargeRequest.setCostDiscount(new BigDecimal("1"));


        // xuanJieVideoRequest.chargeRequest(chargeRequest);

        logger.info("炫捷视屏会员");
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        long start =System.currentTimeMillis();

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("szOrderId", chargeRequest.getChannelNum());
        paramMap.put("szAgentId", String.valueOf(supplyMap.get("szAgentId")));
        paramMap.put("szPhoneNum", chargeRequest.getMobile());
        paramMap.put("nMoney", chargeRequest.getPackageSize()); // 充值金额
        paramMap.put("nSortType", String.valueOf(chargeRequest.getOperator())); // 运营商
        paramMap.put("nProductClass", "1"); //
        paramMap.put("nProductType", "302"); // 视频会员
        paramMap.put("szTimeStamp", DateUtil.Date2String(new Date(), DateUtil.SQL_TIME_FMT)); // 时间戳
        paramMap.put("szVerifyString",
                DigestUtils.md5Hex(getUrlParamsByMap(paramMap,String.valueOf(supplyMap.get("key")))));
        paramMap.put("szNotifyUrl", String.valueOf(supplyMap.get("callback_url"))); //
        paramMap.put("szFormat", String.valueOf("JSON")); //
        // paramMap.put("ip", String.valueOf("192.168.4.167")); //

        logger.info("8、发送流程：发送供应商---炫捷视频充值参数--{}", paramMap);
        String resultContent = "";
        // 充值请求
        try {
            resultContent = HttpTookit.doPost(String.valueOf(supplyMap.get("host")), paramMap);
            logger.info("请求成功，响应数据：{} 耗时: {}", resultContent, (System.currentTimeMillis()-start));
            JSONObject object = JSON.parseObject(resultContent);
            logger.info("JSON格式化请求参数:{}", object);
        } catch (Exception e) {
            logger.error("发送异常：发送供应商---炫捷视频会员---充值号码{}--, 订单号:{}-失败原因{}",
                    chargeRequest.getMobile(), e.getLocalizedMessage(), chargeRequest.getChannelNum());
        }

    }

    /**
     * 将map转换成url
     * @param map map参数
     * @return url参数串
     */
    private String getUrlParamsByMap(Map<String, String> map, String key) {

        StringBuffer sb = new StringBuffer();
        sb.append("szAgentId=").append(map.get("szAgentId"))
                .append("&szOrderId=").append(map.get("szOrderId"))
                .append("&szPhoneNum=").append(map.get("szPhoneNum"))
                .append("&nMoney=").append(map.get("nMoney"))
                .append("&nSortType=").append(map.get("nSortType"))
                .append("&nProductClass=").append(map.get("nProductClass"))
                .append("&nProductType=").append(map.get("nProductType"))
                .append("&szTimeStamp=").append(map.get("szTimeStamp"))
                .append("&szKey=").append(key);

        return sb.toString();
    }

    @Test
    public void test(){
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("szAgentId", "17610088085");
        paramMap.put("szOrderId", "c201802091428312");
        paramMap.put("szPhoneNum", "30");
        paramMap.put("nMoney", "1"); // 充值金额
        paramMap.put("nSortType", "1"); // 运营商
        paramMap.put("nProductClass", "1"); //
        paramMap.put("nProductType", "302"); // 视频会员
        paramMap.put("szTimeStamp", DateUtil.Date2String(new Date(), DateUtil.SQL_TIME_FMT)); // 时间戳
        paramMap.put("szVerifyString",
                DigestUtils.md5Hex(getUrlParamsByMap(paramMap,"&szKey=sdfgggf2")));
        paramMap.put("szFormat", String.valueOf("JSON")); //

    }


    @Test
    public void testShiLi() {

        Map supplyMap = (Map) SupplyConfig.getConfig().get("videoshili");
        long start =System.currentTimeMillis();
        // 请求参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("agentId", String.valueOf(supplyMap.get("agentId")));
        paramMap.put("agentOrderNo", "c2018022815170014");
        paramMap.put("phoneNo", "zou_shuanglin@163.com");
        paramMap.put("skuId", "100057");
        paramMap.put("notifyUrl", String.valueOf(supplyMap.get("notifyUrl")));
        paramMap.put("timestamp", DateUtil.Date2String(new Date(), "yyyyMMddHHmmss"));
        // 签名
        StringBuffer sb = new StringBuffer();
        Collection<String> keyset = paramMap.keySet();
        List list = new ArrayList<>(keyset);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            sb.append(list.get(i)).append(paramMap.get(list.get(i)));
        }
        sb.append(String.valueOf(supplyMap.get("key")));
        logger.info("签名排序后的字符串:{}", sb.toString());
        paramMap.put("sign", DigestUtils.md5Hex(sb.toString()));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        // 充值请求
        try {
            logger.info("实力视频会员请求参数：{}", paramMap);
            resultContent = HttpTookit.doPost(String.valueOf(supplyMap.get("host")), paramMap);
        } catch (Exception e) {
            logger.error("发送异常：发送供应商---实力视频会员---充值号码{}--, 订单号:{}-失败原因{}",
                    "18610068465", "", e.getLocalizedMessage());

            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
        }
        logger.info("实力视频会员充值请求成功，响应数据：{} 耗时:", resultContent, (System.currentTimeMillis()-start));
    }

    @Test
    public void t2(){
        Map<String, Object> map = new HashMap<>();
        map.put("key1", new String[]{"1","2"});
        map.put("key2", new String[]{"3","4"});
        map.put("key3", new String[]{"5"});
        logger.info(": {}", net.sf.json.JSONObject.fromObject(map));

        logger.info(String.valueOf(com.alibaba.fastjson.JSONArray.parseArray("[]", ChargeMessage.class)));
    }

    @Test
    public void jms() {
        String jsonMessage= MqReceive.receive(SysConstants.Queue.SUBMIT_QUERE_ALONE_LIST);
        logger.info("jsonMessage : {}", jsonMessage);
    }

    // @Test
    // public void testAccess_token() {
    //     logger.info("token值为: {}", TokenSingleton.getInstance("http://211.138.30.208:20200/aopoauth/oauth/token?app_id=501295&app_key=3888bd16498334490f5eaf4355be982f&grant_type=client_credentials").getMap());
    // }

    @Test
    public void testSoouu() {

        // 请求参数
        Map<String, String> param = new HashMap<>();
        param.put("method", "kamenwang.goods.get");
        param.put("timestamp", DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
        param.put("format", "json");
        param.put("v", "1.0");
        param.put("customerid", "803903");

        // 获取商品接口
        /*param.put("goodscatalogid", "8529");*/

        // 网游直充
        // param.put("customerorderno", "c20180309118801");
        // param.put("productid", "1112991");
        // param.put("buynum", "1");
        // param.put("chargeaccount", "18628311290");
        // param.put("notifyurl", "http://116.62.28.7:8090/test/callback");


        // 签名
        StringBuffer sb = new StringBuffer();
        Collection<String> keyset = param.keySet();
        List list = new ArrayList<>(keyset);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            if (i>0) sb.append("&");
            sb.append(list.get(i)).append("=").append(param.get(list.get(i)));
        }
        sb.append("303AFA6CC5B6DE187E9EC87AA5BC6A72");
        logger.info("待签名字符串");
        param.put("sign", DigestUtils.md5Hex(sb.toString()));

        // 充值请求
        try {
            logger.info("请求参数：{}", param);
            String resultContent = HttpTookit.doPost("http://ccapi.soouu.cn/Interface/Method", param);
            logger.info("请求回调: {}", resultContent);
        } catch (Exception e) {
            logger.error("失败原因{}", e.getLocalizedMessage());
        }
    }

    @Test
    public void tt(){
        System.out.println(null == JSON.parseObject("{\"key\":\"sdf\"}").get("MessageCode"));
    }

    @Test
    public void createSign() {
        String token = "90b5eaa389d940e48cb78dc870c21c7e";
        String mobile = "17610783583";
        String customId = "zy20180419140119";
        String code = "700001";
        // String operator = "4";
        String callbackUrl = "http://116.62.28.7:8090/test/callback";

        logger.info("生成的签名是: {}", DigestUtils.md5Hex(token+mobile+customId+code+callbackUrl));
    }
}
