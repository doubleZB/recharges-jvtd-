package com.jtd.recharge.gateway.action;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.cache.redis.RedisTemplate;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.CommonUtil;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeMessage;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.GateWayResp;
import com.jtd.recharge.dao.po.*;
import com.jtd.recharge.gateway.service.GateWayService;
import com.jtd.recharge.service.charge.cache.CacheOrderService;
import com.jtd.recharge.service.charge.common.NumSectionService;
import com.jtd.recharge.service.charge.position.ChargePostionService;
import com.jtd.recharge.service.user.BalanceService;
import com.jtd.recharge.service.user.UserService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @autor jipengkun
 * 流量接口
 */
@Controller
@RequestMapping("/gateway")
public class GateWaySubmit {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    public UserService userService;

    @Autowired
    public BalanceService balanceService;

    @Autowired
    public NumSectionService numSectionService;

    @Autowired
    public ChargePostionService chargePostionService;
    @Autowired
    public GateWayService gateWayService;
    @Autowired
    public CacheOrderService cacheOrderService;

    @Resource(name="commRedisTemplate")
    RedisTemplate redisTemplate;

    /**
     * 流量充值
     * @param request
     * @param business 业务类型 flow 流量 telbill 话费 video视频会员
     * @return
     */
    @RequestMapping("/{business}/charge")
    @ResponseBody
    public Object charge(@PathVariable String business, HttpServletRequest request) {
        log.info("business---" + business);
        long startChargeTime=System.currentTimeMillis();//程序开始时间

        String sign = request.getParameter("sign");
        String mobile = request.getParameter("mobile");//手机号
        String code = request.getParameter("code");//档位编码
        String customId = request.getParameter("customId");//客户订单号
        String callbackUrl = request.getParameter("callbackUrl");
        String userToken = request.getParameter("token");
        String source = request.getParameter("source");
        String remark = request.getParameter("remark");

        log.info("1、接口收到充值数据sign---" + sign+"  mobile---" + mobile+"  code---" + code+
                "  customId---" + customId+"  callbackUrl---" + callbackUrl+"  userToken---" + userToken+"  remark---" + remark);

        if(validParam(request,business).getStatusCode() !=  null) {
            return validParam(request,business);
        }


        GateWayResp resp = new GateWayResp();
        String operatorStr = request.getParameter("operator");
        // 视频充值运营商不能为空
        if ("video".equals(business) && !(operatorStr != null && operatorStr.matches("[0-9]+"))){
            resp.setStatusCode("1003");
            resp.setStatusMsg("视频充值运营商不能为空");
            resp.setCustomId(customId);
            return resp;
        }
        Integer operator = Integer.parseInt(StringUtils.isBlank(operatorStr) ? "0" : operatorStr);

        //区分业务类型
        int businessType = -1;
        if("flow".equals(business)) {
            businessType = new Integer(SysConstants.BusinessType.flow);
        } else if("telbill".equals(business)){
            businessType = new Integer(SysConstants.BusinessType.telbill);
        } else if("video".equals(business)) {
            businessType = new Integer(SysConstants.BusinessType.videoVip);
        } else if ("video".equals(business) && operator == null) {
            resp.setStatusCode("1003");
            resp.setStatusMsg("视频充值运营商不能为空");
            resp.setCustomId(customId);
            return resp;
        } else {
            log.info("2、接口收到充值数据 mobile---" + mobile+" 接口地址不合法，传入的business="+business+" 实际要求business=flow或者telbill或者video , 订单到此结束！");
            resp.setStatusCode("1003");
            resp.setStatusMsg("地址不合法");
            resp.setCustomId(customId);
            return resp;
        }

        //根据手机号查询省份 运营商
        NumSection numSection = numSectionService.findNumSectionByMobile(mobile);
        if(numSection==null){
            if(businessType!=new Integer(SysConstants.BusinessType.videoVip)){
                log.info("2、接口收到充值数据 mobile---" + mobile + " 不存在该号段" + mobile + " 订单到此结束！");
                resp.setStatusCode("1002");
                resp.setStatusMsg("手机号不合法");
                resp.setCustomId(customId);
                return resp;
            }
        }
        Integer provinceId;
        if(numSection!=null&&businessType!=new Integer(SysConstants.BusinessType.videoVip)) {
            provinceId = numSection.getProvinceId();//省份id
            if (!"video".equals(business)) {
                operator = numSection.getMobileType();//运营商
            } else {
                // 视频会员全部为全国
                provinceId = 32;
            }
        }else{
            // 视频会员全部为全国
            provinceId = 32;
        }

        try {

            User user = gateWayService.findUserByToken(userToken);

            /**
             * 3 验证用户token
             */
            if(user == null) {
                log.info("2、接口收到充值数据 mobile---"+mobile+" token不正确，userToken="+userToken+" 订单到此结束！");
                resp.setStatusCode("1004");
                resp.setStatusMsg("token不正确");
                resp.setCustomId(customId);
                return resp;
            }

            /**
             * 验证ip
             */
            String userIp = CommonUtil.getRemortIP(request);
            if(user.getIpAddress() != null && !("".equals(user.getIpAddress()))) {
                if(user.getIpAddress().indexOf(userIp) == -1) {
                    log.info("2、接口收到充值数据 mobile---"+mobile+" userIp不合法，收到userIp="+userIp+"   白名单 ip---" + user.getIpAddress()+" 订单到此结束！");
                    resp.setStatusCode("1012");
                    resp.setStatusMsg("ip not in white list");
                    resp.setCustomId(customId);
                    return resp;
                }
            }


            Integer userStatus = user.getStatus();//用户状态
            String token = user.getToken();//用户token
            int userId = user.getId();//用户id

            String validSign = DigestUtils.md5Hex(token + mobile + customId + code + callbackUrl);
            log.info("加密"+validSign);
            if(!validSign.equals(sign)) {
                log.info("2、接口收到充值数据 mobile---"+mobile+" sign错误 订单到此结束！");
                resp.setStatusCode("1005");
                resp.setStatusMsg("商户sign不匹配");
                resp.setCustomId(customId);
                return resp;
            }

            /**
             * 5 验证用户状态
             */
            if(SysConstants.UserStatus.close.equals(userStatus.toString())){
                log.info("2、接口收到充值数据 mobile---"+mobile+" 帐户已经关闭 订单到此结束！");
                resp.setStatusCode("1006");
                resp.setStatusMsg("帐户已经关闭");
                resp.setCustomId(customId);
                return resp;
            }

            /**
             * 订单去重
             */
            ChargeOrder  chargeOrderTwo = gateWayService.selectOrderByMobileAndCustomId(mobile,customId);
            if(chargeOrderTwo!=null){
                log.info("2、接口收到充值数据 mobile---"+mobile+" 订单重复, 订单到此结束！");
                resp.setStatusCode("1014");
                resp.setStatusMsg("订单重复");
                resp.setCustomId(customId);
                return resp;
            }
            /**
             * 9 档位编码不正确
             */
            ChargePosition position = gateWayService.findPosition(businessType,code,operator);
            if(position == null) {
                resp.setStatusCode("1007");
                resp.setStatusMsg("档位编码不正确");
                resp.setCustomId(customId);
                log.info("2、接口收到充值数据 mobile---"+mobile+" 档位编码不正确 订单到此结束！");
                return resp;
            }
            //商品价格
            BigDecimal positionAmount = position.getAmount();
            String positionCode = position.getCode();//平台档位编码

            //找到平台产品
            ChargeProduct chargeProduct = gateWayService.findProduct(businessType,operator,provinceId,code);
            int productId = chargeProduct.getId();


            //获取业务对应的应用
            UserApp userApp = gateWayService
                    .findUserApp(userId,businessType == SysConstants.BusinessType.videoVip ? 6 : businessType);
            int groupId = userApp.getGroupId();
            //根据货架组和产品找到货架
            ChargeProductStore productStore = gateWayService.findProductStore(groupId,productId);


            if(productStore == null) {
                resp.setStatusCode("1008");
                resp.setStatusMsg("无可充货架");
                resp.setCustomId(customId);
                log.info("2、接口收到充值数据 mobile---"+mobile+" 无可充货架 订单到此结束！");
                return resp;
            }


            String orderNum = CommonUtil.getOrderNum();
            log.info("2、接口收到充值数据 mobile---" + mobile+" 生成订单，订单号orderNum ="+orderNum);
            //扣费金额
            BigDecimal feeAmount = position.getAmount().multiply(productStore.getDiscountPrice());

            /**
             * 话费价格保护
             */
            if(businessType == SysConstants.BusinessType.telbill) {
                if(feePriceProtect(position.getAmount(), feeAmount)) {
                    log.info("3、接口收到充值数据 mobile---" + mobile+" 订单号orderNum ="+orderNum+" 超过价格保护阀值 订单到此结束！");
                    resp.setStatusCode("1012");
                    resp.setStatusMsg("超过价格保护阀值");
                    resp.setCustomId(customId);
                    return resp;
                }
            }

            /**
             * 创建订单基础对象
             */
            ChargeOrder order = new ChargeOrder();
            order.setBusinessType(businessType);
            order.setOrderNum(orderNum);
            order.setUserId(userId);
            order.setRechargeMobile(mobile);
            order.setOperator(operator);
            order.setProvinceId(provinceId);
            order.setPositionCode(positionCode);
            order.setAmount(feeAmount);
            order.setSource(SysConstants.Source.gateway);
            if(source !=null) {
                order.setSource(SysConstants.Source.page);
            }
            order.setOrderTime(new Date());
            order.setCustomid(customId);
            order.setPushStatus(ChargeOrder.PushState.UNPUSH);
            order.setCallbackUrl(callbackUrl);
            order.setRemark(remark);


            //货架已经下架
            if(productStore.getStatus() != 1) {
                order.setStatus(ChargeOrder.OrderStatus.NO_STORE);
                gateWayService.saveOrder(order);
                log.info("3、接口收到充值数据 mobile---" + mobile+" 订单号orderNum ="+orderNum+" 货架已经下架 订单到此结束！");
                resp.setStatusCode("1008");
                resp.setStatusMsg("货架已经下架");
                resp.setCustomId(customId);
                return resp;
            }

            List<ChargeRequest> supplyList = new ArrayList();

            /**
             * 系统匹配
             */
            if(productStore.getSendType() == ChargeProductStore.SendType.sysmatch) {
                supplyList = findMatchSupply(provinceId,positionCode,businessType,operator,mobile,positionAmount);
            }

            /**
             * 指定通道
             */
            if(productStore.getSendType() == ChargeProductStore.SendType.assign) {
                supplyList = findAssignSupply(businessType,productStore.getId(),positionCode,operator,provinceId,mobile,positionAmount);
            }

            if(supplyList.size() == 0) {
                order.setStatus(ChargeOrder.OrderStatus.NO_CHANNEL);
                gateWayService.saveOrder(order);
                log.info("3、接口收到充值数据 mobile---" + mobile+" 订单号orderNum ="+orderNum+" 无可用通道 订单到此结束！");
                resp.setStatusCode("1009");
                resp.setStatusMsg("无可用通道");
                resp.setCustomId(customId);
                return resp;
            }

            //成本折扣
            BigDecimal costDiscount = supplyList.get(0).getCostDiscount();
            //成本折扣
            order.setOrderCostDiscount(costDiscount);
            //计算成本（面值*折扣）
            order.setOrderCost(positionAmount.multiply(costDiscount));
            //售价折扣
            order.setOrderPriceDiscount(productStore.getDiscountPrice());

            /**
             * 查询是否加入缓存
             */
            boolean ischche=false;
            CacheRole cacheRole=null;
            CacheRole cacheRoleSh=null;
            cacheRole = (CacheRole) JSONObject.toBean(redisTemplate.getObject(SysConstants.CACHE_SUPPLY_KEY+supplyList.get(0).getSupplyId()), CacheRole.class);
            log.info("操作日志缓存功能TD："+JSONObject.fromObject(cacheRole));
            if(businessType==1) {
                cacheRoleSh = (CacheRole) JSONObject.toBean(redisTemplate.getObject(SysConstants.CACHE_USER_FLOW_KEY + userId), CacheRole.class);
            } else {
                cacheRoleSh = (CacheRole) JSONObject.toBean(redisTemplate.getObject(SysConstants.CACHE_USER_TEL_KEY + userId), CacheRole.class);
            }
            log.info("操作日志缓存功能SH：" + JSONObject.fromObject(cacheRoleSh));
            if(cacheRole!=null){
                ischche=isCache(cacheRole,businessType,String.valueOf(operator),String.valueOf(provinceId),code);
                if(!ischche){
                    if(cacheRoleSh!=null){
                        ischche=isCache(cacheRoleSh,businessType,String.valueOf(operator),String.valueOf(provinceId),code);
                    }
                }
            }else if(cacheRoleSh!=null){
                ischche=isCache(cacheRoleSh,businessType,String.valueOf(operator),String.valueOf(provinceId),code);
            }
            if(ischche){
                CacheOrder cacheOrder=new CacheOrder();
                cacheOrder.setOrderNum(orderNum);
                cacheOrder.setUserId(userId);
                cacheOrder.setUserName(user.getUserCnName());
                cacheOrder.setCustomid(customId);
                cacheOrder.setMobile(mobile);
                cacheOrder.setBusinessType(businessType);
                cacheOrder.setOperator(operator);
                cacheOrder.setProvince(provinceId);
                ChargePosition chargePosition=new ChargePosition();
                chargePosition.setCode(code);
                List<ChargePosition> chargePositionList=chargePostionService.getPosition(chargePosition);
                if(chargePositionList!=null&&chargePositionList.size()>0) {
                    cacheOrder.setProductName(chargePositionList.get(0).getPackageSize());
                }
                ChargeSupply chargeSupply=gateWayService.findChargeSupplyById(supplyList.get(0).getSupplyId());
                if(chargeSupply!=null) {
                    cacheOrder.setSupplyName(chargeSupply.getName());
                    cacheOrder.setSupplier(chargeSupply.getSupplyName());
                }
                cacheOrder.setPositionCode(supplyList.get(0).getPositionCode());
                cacheOrder.setOriginalPrice(position.getAmount());//原价
                cacheOrder.setPayCount(position.getAmount());//应付金额
                cacheOrder.setStatus(CacheOrder.statuss.CACHE);
                cacheOrderService.insertCacheOrder(cacheOrder);
                order.setStatus(ChargeOrder.OrderStatus.CACHING);
                order.setCallbackUrl(callbackUrl);
                gateWayService.saveOrder(order);
                log.info("3、接口收到充值数据 mobile---" + mobile + " 订单号orderNum =" + orderNum + " 保存订单成功-进缓存！"+JSONObject.fromObject(cacheOrder));
                resp.setStatusCode(SysConstants.SUCCESS);
                resp.setStatusMsg("提交成功");
                resp.setCustomId(customId);
                resp.setOrderNum(orderNum);
            }else {
                /**
                 * 扣款
                 */
                try {
                    String res=  gateWayService.paymentAmount(userId, feeAmount, businessType, orderNum);
                    if(res!=null&&res.equals("1010")){
                        order.setStatus(ChargeOrder.OrderStatus.NO_BALANCE);
                        gateWayService.saveOrder(order);
                        log.info("3、接口收到充值数据 mobile---" + mobile + " 订单号orderNum =" + orderNum + " 余额不足 订单到此结束！");
                        resp.setStatusCode("1010");
                        resp.setStatusMsg("余额不足");
                        resp.setCustomId(customId);
                        return resp;
                    }
                }catch (Exception e) {
                    order.setStatus(ChargeOrder.OrderStatus.PAY_ERROR);
                    gateWayService.saveOrder(order);
                    log.info("3、接口收到充值数据 mobile---" + mobile+" 订单号orderNum ="+orderNum+" 扣费失败 订单到此结束！"+e.getMessage());
                    resp.setStatusCode("1011");
                    resp.setStatusMsg("扣费失败");
                    resp.setCustomId(customId);
                    return resp;
                }

                /**
                 * 12 创建订单
                 */
                order.setStatus(ChargeOrder.OrderStatus.CREATE_ORDER);
                gateWayService.saveOrder(order);
                log.info("3、接口收到充值数据 mobile---" + mobile+" 订单号orderNum ="+orderNum+" 保存订单成功！");
                /**
                 * 13 添加发送队列
                 */
                ChargeMessage chargeMessage = new ChargeMessage();
                chargeMessage.setOrderNum(orderNum);
                chargeMessage.setBusinessType(businessType);

                log.info("4、接口收到充值数据 mobile---" + mobile+" 订单号orderNum ="+orderNum+"添加流量消息队列" + JSON.toJSONString(chargeMessage));
                MNSClient client = MessageClient.getClient();
                chargeMessage.setSupplyList(supplyList);
                String queueName=SysConstants.Queue.SUBMIT_QUEUE;
                //判断是否走单通道-应对只能绑定一个IP的供应商
                for(int i=0;i<supplyList.size();i++){
                    if(SysConstants.alone_mao.get(supplyList.get(i).getSupplyName())!=null){
                        queueName=SysConstants.Queue.SUBMIT_QUERE_ALONE;
                    }
                }
                log.info("队列名："+queueName);
                CloudQueue queue = client.getQueueRef(queueName);
                Message message = new Message();
                message.setMessageBody(JSON.toJSONString(chargeMessage));
                log.info("队列消息体："+JSON.toJSONString(chargeMessage));
                Message putMsg = queue.putMessage(message);
                log.info("5、接口收到充值数据 mobile---" + mobile+" 订单号orderNum ="+orderNum+"添加流量消息队列  ----成功  Send message id is: " + putMsg.getMessageId());
            }
            resp.setStatusCode(SysConstants.SUCCESS);
            resp.setStatusMsg("提交成功");
            resp.setCustomId(customId);
            resp.setOrderNum(orderNum);
            long endChangeTime=System.currentTimeMillis();
            log.info("订单编号"+orderNum+",手机号:"+mobile+"客户订单号:"+customId+"用户支付创建订单调用接口执行时长:"+(endChangeTime-startChargeTime));
        } catch (Exception e) {
            log.info("2、接口收到充值数据 sign---" + sign+"  mobile---" + mobile+"  code---" + code+
                    "  customId---" + customId+"  callbackUrl---" + callbackUrl+"  userToken---" + userToken+"  remark---" + remark+"  系统异常："+e.getMessage());
            log.error("2、接口收到充值数据 sign---" + sign+"  mobile---" + mobile+"  code---" + code+
                    "  customId---" + customId+"  callbackUrl---" + callbackUrl+"  userToken---" + userToken+"  remark---" + remark+"  系统异常："+e.getMessage(),e);
            e.printStackTrace();
            resp.setStatusCode("1099");
            resp.setStatusMsg("系统异常");
            resp.setCustomId(customId);
            return resp;
        }

        return resp;
    }

    /**
     * 话费价格保护
     * @param realPrice 实际价格
     * @param reducedPrice 折扣价格
     * @return true在保护范围  false不在保护范围
     */
    private boolean feePriceProtect(BigDecimal realPrice,BigDecimal reducedPrice) {
        BigDecimal protectPrice = realPrice.multiply(new BigDecimal("0.98"));

        if(reducedPrice.compareTo(protectPrice) == 1 ) {
            return false;
        }
        return true;
    }

    /**
     * 指定供应商发送
     * @param businessType 业务类型
     * @param storeId      货架id
     * @param positionCode 平台档位编码
     * @param operator     运营商
     * @param provinceId   省份id
     * @param mobile       手机号
     * @param positionAmount 面值
     * @return
     */
    private List<ChargeRequest> findAssignSupply(int businessType,
                                                 int storeId,
                                                 String positionCode,
                                                 int operator,
                                                 int provinceId,
                                                 String mobile,
                                                 BigDecimal positionAmount) {

        List<ChargeRequest> supplyList = new ArrayList();

        //根据货架id找到供应商id列表,可能存在多个供应商

        List<ChargeProductStoreSupply> storeSupplyList = gateWayService.findProductStoreSupply(businessType,storeId);
        for (int i = 0; i < storeSupplyList.size(); i++) {
            ChargeRequest chargeRequest = new ChargeRequest();

            ChargeProductStoreSupply productStoreSupply = storeSupplyList.get(i);

            int supplyId = productStoreSupply.getSupplyId();
            String supplyName = gateWayService.findSupplyById(supplyId);
            //1 找到供应商名称,用于路由

            //2 根据商户传的档位编码,找到供应商的档位编码
            ChargeSupplyPosition supplyPosition = gateWayService.findSupplyPosition(businessType, supplyId, positionCode);
            if(supplyPosition==null){//尚未找到供应商编码
                continue;
            }
            String packSize = new Integer(supplyPosition.getPackageSize()).toString();

            //3 根据供应商反查通道
            ChargeChannel queryChannel = new ChargeChannel();
            queryChannel.setProvinceId(provinceId);
            queryChannel.setPositionCode(positionCode);
            queryChannel.setSupplyId(supplyId);
            List<ChargeChannel> channelList = gateWayService.findChannelByPositionProvince(queryChannel);
            if (channelList.size() == 0) {
                //如果找不到通道,再找另一个供应商
                continue;
            }

            ChargeChannel chargeChannel = channelList.get(0);

            if (SysConstants.ChannelStatus.close == chargeChannel.getStatus()) {
                continue;
            }
            //不对应业务类型的供应商不添加
            if (!gateWayService.supplyType(businessType, supplyId)) {
                continue;
            }

            chargeRequest.setSupplyId(supplyId);
            chargeRequest.setSupplyName(supplyName);
            chargeRequest.setPackageSize(packSize);
            chargeRequest.setPositionCode(supplyPosition.getCode());
            chargeRequest.setMobile(mobile);
            chargeRequest.setWeight(chargeChannel.getWeight());
            chargeRequest.setCostDiscount(chargeChannel.getCostDiscount());
            chargeRequest.setOperator(operator);
            chargeRequest.setAmount(positionAmount);

            supplyList.add(chargeRequest);

        }
        return supplyList;
    }


    /**
     * 自动匹配发送
     * @param provinceId 省份id
     * @param positionCode 档位编码
     * @param businessType 业务类型
     * @param operator     运营商
     * @param mobile       手机号
     * @param positionAmount 面值
     * @return
     */
    private List<ChargeRequest> findMatchSupply(int provinceId,String positionCode,
                                                int businessType,int operator,String mobile,
                                                BigDecimal positionAmount) {

        List<ChargeRequest> supplyList = new ArrayList();


        ChargeChannel queryChannel = new ChargeChannel();
        queryChannel.setProvinceId(provinceId);
        queryChannel.setPositionCode(positionCode);
        List<ChargeChannel> channelsList = gateWayService.findChannelByPositionProvince(queryChannel);

        for(int i=0;i<channelsList.size();i++) {
            ChargeRequest chargeRequest = new ChargeRequest();

            ChargeChannel chargeChannel =  channelsList.get(i);

            if(SysConstants.ChannelStatus.close == chargeChannel.getStatus()) {
                continue;
            }

            int supplyId = chargeChannel.getSupplyId();
            String supplyName = gateWayService.findSupplyById(supplyId);

            ChargeSupplyPosition supplyPosition = gateWayService.findSupplyPosition(businessType,supplyId,positionCode);
            String packSize = new Integer(supplyPosition.getPackageSize()).toString();

            chargeRequest.setSupplyId(supplyId);
            chargeRequest.setSupplyName(supplyName);
            chargeRequest.setPackageSize(packSize);
            chargeRequest.setPositionCode(supplyPosition.getCode());
            chargeRequest.setMobile(mobile);
            chargeRequest.setWeight(chargeChannel.getWeight());
            chargeRequest.setOperator(operator);
            chargeRequest.setAmount(positionAmount);

            supplyList.add(chargeRequest);
        }
        return supplyList;
    }

    /**
     * 验证接口请求参数
     * @param request
     * @return
     */
    public GateWayResp validParam(HttpServletRequest request,String business) {
        String sign = request.getParameter("sign");
        String mobile = request.getParameter("mobile");//手机号
        String code = request.getParameter("code");//档位编码
        String customId = request.getParameter("customId");
        String callbackUrl = request.getParameter("callbackUrl");
        String userToken = request.getParameter("token");
        String remark = request.getParameter("remark");

        GateWayResp resp = new GateWayResp();

        if (StringUtils.isEmpty(userToken) || StringUtils.isEmpty(sign)
                || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(code)
                || StringUtils.isEmpty(customId) || StringUtils.isEmpty(callbackUrl)) {
            resp.setStatusCode("1001");
            resp.setStatusMsg("缺少必要参数");
            resp.setCustomId(customId);
            log.info("2、接口收到充值数据 有空值现象，此订单到此结束！ mobile=" + mobile);
            return resp;
        }


        if (!business.equals("video")) {
            if (!CommonUtil.isPhoneNum(mobile)) {
                resp.setStatusCode("1002");
                resp.setStatusMsg("手机号不合法");
                resp.setCustomId(customId);
                log.info("2、接口收到充值数据  mobile=" + mobile + "，手机号不合法 此订单到此结束！");
                return resp;
            }
        }

        /**
         * 验证号码充值阀值
         */
        try {
            boolean valid = gateWayService.validPhoneSum(mobile,business);
            if (!valid) {
                log.info("2、接口收到充值数据  mobile="+mobile+"  超过当天充值阀值，此订单到此结束！");
                resp.setStatusCode("1013");
                resp.setStatusMsg("号码充值次数超过充值次数阀值");
                resp.setCustomId(customId);
                return resp;
            }
        } catch (Exception e) {
            log.error("号码充值次数阀值异常---",e);
        }

        return resp;
    }

    /**
     * 验证是否被缓存
     */
    public boolean  isCache(CacheRole cacheRole,int businessType,String operator,String provinceId,String code) {
        if (cacheRole.getStatus() == CacheRole.statuss.OPEN && cacheRole.getBusinessType() == businessType) { //判断状态以及类型
            if (cacheRole.getOperator().equals(String.valueOf(CacheRole.operators.WHOLE)) ||isIndexof(cacheRole.getOperator(),operator)||cacheRole.getOperator().equals(operator)){//判断运营商
                if (cacheRole.getProvince().equals(String.valueOf(CacheRole.provinces.WHOLE)) || isIndexof(cacheRole.getProvince(),provinceId)||cacheRole.getProvince().equals(provinceId)) {//判断地区
                    //判断卡品
                    if(isIndexof(cacheRole.getPositionCode(),String.valueOf(CacheRole.positionCodes.WHOLE_MOBILE_FLOW))||
                            isIndexof( cacheRole.getPositionCode(),String.valueOf(CacheRole.positionCodes.WHOLE_UNICOM_FLOW))||
                            isIndexof( cacheRole.getPositionCode(),String.valueOf(CacheRole.positionCodes.WHOLE_TELECOM_FLOW))||
                            isIndexof( cacheRole.getPositionCode(),String.valueOf(CacheRole.positionCodes.WHOLE_MOBILE_BILL))||
                            isIndexof( cacheRole.getPositionCode(),String.valueOf(CacheRole.positionCodes.WHOLE_UNICOM_BILL))||
                            isIndexof( cacheRole.getPositionCode(),String.valueOf(CacheRole.positionCodes.WHOLE_TELECOM_BILL))||
                            isIndexof( cacheRole.getPositionCode(),String.valueOf(CacheRole.positionCodes.WHOLE))||
                            isIndexof( cacheRole.getPositionCode(),code)||cacheRole.getPositionCode().equals(code)){
                        return true;
                    }else if(businessType==SysConstants.BusinessType.flow){
                        if(cacheRole.getPositionCode().equals(String.valueOf(CacheRole.positionCodes.WHOLE_MOBILE_FLOW))||cacheRole.getPositionCode().equals(String.valueOf(CacheRole.positionCodes.WHOLE_UNICOM_FLOW))
                                ||cacheRole.getPositionCode().equals(String.valueOf(CacheRole.positionCodes.WHOLE_TELECOM_FLOW))){
                            return true;
                        }
                    }else if(businessType==SysConstants.BusinessType.telbill){
                        if(cacheRole.getPositionCode().equals(String.valueOf(CacheRole.positionCodes.WHOLE_MOBILE_BILL))||cacheRole.getPositionCode().equals(String.valueOf(CacheRole.positionCodes.WHOLE_UNICOM_BILL))
                                ||cacheRole.getPositionCode().equals(String.valueOf(CacheRole.positionCodes.WHOLE_TELECOM_BILL))){
                            return true;
                        }
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
        return false;
    }

    public  boolean  isIndexof(String province,String provinceId){
        List provinceList= Arrays.asList(province.split(","));
        if(provinceList.contains(provinceId)){
            return true;
        }else{
            return false;
        }
    }
}
