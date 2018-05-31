package com.jtd.recharge.gateway.service;

import com.jtd.recharge.base.cache.redis.RedisTemplate;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.CommonUtil;
import com.jtd.recharge.dao.mapper.*;
import com.jtd.recharge.dao.po.*;
import org.apache.commons.beanutils.converters.IntegerArrayConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @autor jipengkun
 */
@Service
@Transactional(readOnly = true)
public class GateWayService {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    ChargeProductGroupMapper productGroupMapper;

    @Resource
    ChargeProductMapper chargeProductMapper;

    @Resource
    ChargeUserGroupMapper chargeUserGroupMapper;

    @Resource
    ChargeProductStoreMapper chargeProductStoreMapper;

    @Resource
    ChargePositionMapper chargePositionMapper;

    @Resource
    ChargeOrderMapper chargeOrderMapper;

    @Resource
    ChargeChannelMapper chargeChannelMapper;

    @Resource
    ChargeProductStoreSupplyMapper productStoreSupplyMapper;

    @Resource
    UserBalanceMapper userBalanceMapper;

    @Resource
    ChargeSupplyMapper chargeSupplyMapper;

    @Resource
    ChargeSupplyPositionMapper chargeSupplyPositionMapper;

    @Resource
    UserBalanceDetailMapper userBalanceDetailMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    UserAppMapper userAppMapper;

    @Resource(name="commRedisTemplate")
    RedisTemplate redisTemplate;

    /**
     * 根据省份和档位编码查通道
     * @return
     */
    public List<ChargeChannel> findChannelByPositionProvince(ChargeChannel chargeChannel) {
        return chargeChannelMapper.selectChannelByCondition(chargeChannel);
    }

    /**
     * 获取供应商档位信息
     * @param businessType
     * @param supplyId
     * @param positionCode
     * @return
     */
    public ChargeSupplyPosition findSupplyPosition(int businessType,
                                                   int supplyId,
                                                   String positionCode
    ) {
        ChargeSupplyPosition supplyPosition = chargeSupplyPositionMapper.selectSupplyPostionByParam(businessType,supplyId,positionCode);
        return supplyPosition;
    }

    /**
     * 根据供应商id获取供应商名称
     * @param supplyId
     * @return
     */
    public String findSupplyById(int supplyId) {
        ChargeSupply chargeSupply = chargeSupplyMapper.selectByPrimaryKey(supplyId);
        return chargeSupply.getSupplyName();
    }

    /**
     * 根据供应商id获取供应商名称
     * @param supplyId
     * @return
     */
    public ChargeSupply findChargeSupplyById(int supplyId) {
        return chargeSupplyMapper.selectByPrimaryKey(supplyId);
    }

    /**
     * 扣费操作
     * @param userId
     * @param feeAmount 扣费金额
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public String paymentAmount(int userId,BigDecimal feeAmount,int businessType,String orderNum) {
        //获取帐户余额
        synchronized (GateWayService.class) {
            UserBalance userBalance =userBalanceMapper.selectUserBalanceByUserId(userId);
            BigDecimal balance = userBalance.getUserBalance();//用户余额
          /* BigDecimal borrowBalance = userBalance.getBorrowBalance();//借款额度
             BigDecimal creditBalance = userBalance.getCreditBalance();//信用额度
             //可用金额
             BigDecimal ableBlance = balance.add(creditBalance);*/

            if (feeAmount.compareTo(balance) == 1) {
                return "1010";
            }else{
                userBalanceMapper.updateBalance(userId, feeAmount);
            }
        }
        //添加帐单明细
        UserBalanceDetail userBalanceDetail = new UserBalanceDetail();
        userBalanceDetail.setUserId(userId);
        userBalanceDetail.setSequence(CommonUtil.getPayNum());
        userBalanceDetail.setBillType(UserBalanceDetail.BillType.CHARGE_OFF);
        userBalanceDetail.setCategory(UserBalanceDetail.Category.CONSUME);
        if(SysConstants.BusinessType.flow == businessType) {
            userBalanceDetail.setDescription("流量充值-" + orderNum);
        } else {
            userBalanceDetail.setDescription("话费充值-" + orderNum);
        }
        userBalanceDetail.setAmount(feeAmount);
        UserBalance userBalance = userBalanceMapper.selectUserBalanceByUserId(userId);
        userBalanceDetail.setBalance(userBalance.getUserBalance());
        userBalanceDetail.setUpdateTime(new Date());
        userBalanceDetailMapper.insertSelective(userBalanceDetail);
        return "success";
    }


    /**
     * 根据货架找到供应商列表
     * @param businessType
     * @param storeId
     * @return
     */
    public List<ChargeProductStoreSupply> findProductStoreSupply(int businessType , int storeId) {
        ChargeProductStoreSupply productStoreSupply = new ChargeProductStoreSupply();
        productStoreSupply.setBusinessType(businessType);
        productStoreSupply.setStoreId(storeId);

        List<ChargeProductStoreSupply> list = productStoreSupplyMapper.selectProductStoreSupplyByCondition(productStoreSupply);
        return list;
    }

    /**
     * 查询档位信息
     * @return
     */
    public ChargePosition findPosition(int businessType,String code,int operator) {
        ChargePosition position = chargePositionMapper.selectPosition(businessType,code,operator);
        return position;
    }

    /**
     * 获取产品信息
     * @param businessType
     * @param operator
     * @param provinceId
     * @param positionCode
     * @return
     */
    public ChargeProduct findProduct(int businessType,int operator,int provinceId,String positionCode) {
        //根据business类型,运营商,省份,档位编码查询productId charge_product
        ChargeProduct chargeProduct = chargeProductMapper.selectProductId(businessType,operator,provinceId,positionCode);
        return chargeProduct;
    }

    /**
     * 获取用户应用
     * @param userId
     * @return
     */
    public UserApp findUserApp(int userId,int businessType) {
        UserApp userApp = new UserApp();
        userApp.setUserId(new Integer(userId));
        userApp.setAppType(new Integer(businessType));
        return userAppMapper.selectUserAppByUserAppType(userApp);
    }

    /**
     * 根据货架组id与产品id获取货架
     * @param groupId
     * @param productId
     * @return
     */
    public ChargeProductStore findProductStore(int groupId,int productId) {
        //根据货架组id 商品id,获取product_store 折扣价格,上下架状态
        ChargeProductStore chargeProductStore = chargeProductStoreMapper.selectProductStore(groupId,productId);
        return chargeProductStore;
    }

    /**
     * 创建 订单
     * @param order
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int saveOrder(ChargeOrder order) {
        return chargeOrderMapper.insertSelective(order);
    }

    /**
     * 创建 订单
     * @param order
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateOrder(ChargeOrder order) {
        return chargeOrderMapper.updateOrderByOrderNum(order);
    }

    /**
     * 判断供应商id是否是对应的业务,话费 流量
     * @param businessType
     * @return
     */
    public boolean supplyType(int businessType,int supplyId) {
        ChargeSupply chargeSupply = new ChargeSupply();
        chargeSupply.setBusinessType(businessType);
        chargeSupply.setId(supplyId);
        List list = chargeSupplyMapper.selectByParam(chargeSupply);
        if(list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询订单号查询订单信息
     * @param orderNum
     * @return
     */
    public ChargeOrder findChargeOrder(String orderNum,String customId, String phone) {

        return chargeOrderMapper.selectOrderByOrderNumAndCustomId(orderNum, customId, phone);
    }

    /**
     * 查询订单号,手机号查询订单信息
     * @param mobile
     * @return
     */
    public ChargeOrder selectOrderByMobileAndCustomId(String mobile,String customId) {

        return chargeOrderMapper.selectOrderByMobileAndCustomId(mobile,customId);
    }

    /**
     * 根据token找用户
     * @param token
     * @return
     */
    public User findUserByToken(String token) {
        return userMapper.selectUserByToken(token);
    }

    /**
     * 单个手机号每天可以充值次数
     * @param phone
     * @return true 可以充值  false不可以充值
     */
    public boolean validPhoneSum(String phone,String business) {
        int sysSum = Integer.parseInt(SysConstants.PHONE_SUM_FLOW);

//        String phoneSumPrefix = "phoneSum_";
        String phoneSumPrefix = "phoneSum_flow_";
        if("telbill".equals(business)){
            sysSum = Integer.parseInt(SysConstants.PHONE_SUM_TELBILL);
            phoneSumPrefix = "phoneSum_telbill_";
        }
        String phoneSum = redisTemplate.get(phoneSumPrefix+phone);
        if(phoneSum == null) {
            redisTemplate.set(phoneSumPrefix+phone,"1");
            return true;
        }
        if(Integer.parseInt(phoneSum) >= sysSum) {
            return false;
        } else {
            int sum = Integer.parseInt(phoneSum) + 1;
            redisTemplate.set(phoneSumPrefix+phone,new Integer(sum).toString());
            return true;
        }
    }
}

