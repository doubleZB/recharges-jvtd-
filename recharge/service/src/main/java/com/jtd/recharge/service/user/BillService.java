package com.jtd.recharge.service.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.mapper.UserBalanceDetailMapper;
import com.jtd.recharge.dao.po.UserBalanceDetail;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by anna on 2016-12-12.
 */
@Service
public class BillService {
    @Resource
    UserBalanceDetailMapper userBalanceDetailMapper;


    public PageInfo<UserBalanceDetail> getBillDetails(UserBalanceDetail detail, Integer pageNumber, Integer pageSize){
        PageHelper.startPage(pageNumber, pageSize, "id desc");
        List<UserBalanceDetail> details = userBalanceDetailMapper.selectByParam(detail);
        PageInfo<UserBalanceDetail> info = new PageInfo<>(details);
        return info;
    }

    public List<UserBalanceDetail> getBillDetails(UserBalanceDetail detail){
        List<UserBalanceDetail> details = userBalanceDetailMapper.selectByParam(detail);
        return details;
    }

}
