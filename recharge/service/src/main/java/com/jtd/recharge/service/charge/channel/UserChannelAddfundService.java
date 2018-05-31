package com.jtd.recharge.service.charge.channel;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.mapper.UserChannelAddfundMapper;
import com.jtd.recharge.dao.po.ChargeChannel;
import com.jtd.recharge.dao.po.UserChannelAddfund;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lyp on 2017/4/11.
 */
@Service
public class UserChannelAddfundService {
    @Resource
    private UserChannelAddfundMapper userChannelAddfundMapper;

    /**
     * 渠道加款查询功能
     * @param userChannelAddfund
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageInfo<UserChannelAddfund> selecctUserChannelAddfund(UserChannelAddfund userChannelAddfund,Integer pageNumber,Integer pageSize){
        PageHelper.startPage(pageNumber,pageSize,"id desc");
        List<UserChannelAddfund> list=userChannelAddfundMapper.selecctUserChannelAddfund(userChannelAddfund);
        PageInfo<UserChannelAddfund> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 渠道加款取消
     * @param userChannelAddfund
     * @return
     */
    public int deleteUserChannelAddfund(UserChannelAddfund userChannelAddfund){
        return userChannelAddfundMapper.deleteByPrimaryKey(userChannelAddfund);
    }

    /**
     * 渠道加款确认
     * @param userChannelAddfund
     * @return
     */
    public int updateUserChannelAddfund(UserChannelAddfund userChannelAddfund){
        return userChannelAddfundMapper.updateByPrimaryKeySelective(userChannelAddfund);
    }

    /**
     * 添加渠道加款信息
     * @param userChannelAddfund
     * @return
     */
    public int insertUserChannelAddfund(UserChannelAddfund userChannelAddfund){
        return userChannelAddfundMapper.insertSelective(userChannelAddfund);
    }

    /**
     * 获取图片URL
     * @param userChannelAddfund
     * @return
     */
    public List<UserChannelAddfund> selectImgUrlNameI(UserChannelAddfund userChannelAddfund){
        return userChannelAddfundMapper.selecctUserChannelAddfund(userChannelAddfund);
    }
}
