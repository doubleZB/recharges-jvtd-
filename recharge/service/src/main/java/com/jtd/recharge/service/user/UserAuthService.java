package com.jtd.recharge.service.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.BalanceRecord;
import com.jtd.recharge.dao.mapper.UserAuthMapper;
import com.jtd.recharge.dao.po.UserApp;
import com.jtd.recharge.dao.po.UserAuth;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */
@Service
@Transactional(readOnly = true)
public class UserAuthService {

    @Resource
    UserAuthMapper userAuthMapper;

    /**
     * 添加商户认证
     * @param userAuth
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int insertUsersAuth(UserAuth userAuth) {
        return userAuthMapper.insertSelective(userAuth);
    }


    /**
     * 商户认证审核列表页
     * @param pageNumber
     * @param pageSize
     * @param userAuth
     * @return
     */
    public PageInfo<UserAuth> selectUserAuthList(Integer pageNumber, Integer pageSize, UserAuth userAuth) {
        PageHelper.startPage(pageNumber,pageSize,"update_time desc");
        List<UserAuth> user = userAuthMapper.selectUserAuthList(userAuth);
        PageInfo<UserAuth> pageInfo = new PageInfo<>(user);
        return pageInfo;
    }

    /**
     * 根据ID修改审核用户认证信息
     * @param userAuth
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateUserAuthByID(UserAuth userAuth) {
        return userAuthMapper.updateUserAuthByID(userAuth);
    }

    /**
     * 根据ID查询userAuth表中的数据
     * @param userAuth
     * @return
     */
    public List<UserAuth> selectUserAuthByID(UserAuth userAuth) {
        return userAuthMapper.selectUserAuthList(userAuth);
    }
}
