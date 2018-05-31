package com.jtd.recharge.service.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.mapper.UserPayOrderMapper;
import com.jtd.recharge.dao.po.UserPayOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 银行卡转账，及审核
 */
@Service
@Transactional(readOnly = true)
public class UserPayOrderService {
    @Resource
    public UserPayOrderMapper userPayOrderMapper;

    /**
     * 添加用户支付账单
     * @param userPayOrder
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int addPayUserOrder(UserPayOrder userPayOrder){
        return userPayOrderMapper.insertUserOrder(userPayOrder);
    }

    /**
     * 查询用户支付列表，根据条件加分也
     * @param userPayOrder
     * @return
     */
    public PageInfo<UserPayOrder> findUserOrderListByName(UserPayOrder userPayOrder,Integer pageNumber, Integer pageSize){
        PageHelper.startPage(pageNumber,pageSize,"id desc");
        List<UserPayOrder> userPayOrdersList = userPayOrderMapper.selectUserOrderByName(userPayOrder);
        PageInfo<UserPayOrder> pageInfo=new PageInfo<>(userPayOrdersList);
        return pageInfo;
    }

    /**
     * 根据id查询银行审核详情页
     * @param id
     * @return
     */
    public List<UserPayOrder> findUserOrderListDetails(Integer id){
        List<UserPayOrder>  userListDetails = userPayOrderMapper.selectUserOrderDetails(id);
        return userListDetails;
    }

    /**
     * 根据id修改审核状态
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateUserOrderList(UserPayOrder userPayOrder){
        return userPayOrderMapper.updateUsercheckCauseById(userPayOrder);
    }

}
