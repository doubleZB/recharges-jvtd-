package com.jtd.recharge.service.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.mapper.ChargeProductGroupMapper;
import com.jtd.recharge.dao.mapper.UserAppMapper;
import com.jtd.recharge.dao.po.ChargeProductGroup;
import com.jtd.recharge.dao.po.UserApp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/22.
 */
@Service
@Transactional(readOnly = true)
public class  UserAppService {

    @Resource
    UserAppMapper userAppMapper;
    @Resource
    ChargeProductGroupMapper chargeProductGroupMapper;

    /**
     * 查询商户应用表
     * @param pageNumber
     * @param pageSize
     * @param userApp
     * @return
     */
    @Transactional
    public PageInfo<UserApp> selectUserAppList(Integer pageNumber, Integer pageSize, UserApp userApp) {
        PageHelper.startPage(pageNumber,pageSize,"update_time desc");
        Map map = new HashMap();
        map.put("userId",userApp.getUserId());
        map.put("appType",userApp.getAppType());
        map.put("userCnName",userApp.getUserCnName());
        map.put("groupIds",userApp.getGroupIds());
        List<UserApp> app = userAppMapper.selectUserAppList(map);
        PageInfo<UserApp> pageInfo = new PageInfo<>(app);
        return pageInfo;
    }

    /**
     * 新增商户应用
     * @param userApp
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int addUserApp(UserApp userApp) {
        return userAppMapper.insertSelective(userApp);
    }


    /**
     * 根据id查询所有内容
     * @param userApp
     * @return
     */
    public Map<String, Object> selectUserAppListById(UserApp userApp) {
        UserApp app = userAppMapper.selectUserAppListById(userApp);
        //货架
        List<ChargeProductGroup> list = chargeProductGroupMapper.selectProductGroup();
        Map map = new HashMap();
        map.put("userApp", app);
        map.put("list", list);
        return map;
    }

    /**
     * 修改应用
     * @param userApp
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateApp(UserApp userApp) {
        return userAppMapper.updateApp(userApp);
    }

    /**
     * 根据userId查询应用
     * @param userApp
     * @return
     */
    public List<UserApp> selectUserAppByUserId(UserApp userApp) {
        return userAppMapper.selectUserAppByUserId(userApp);
    }
    /**
     * 根据userId appType查询应用
     * @param userApp
     * @return
     */
    public UserApp selectUserAppByUserAppType(UserApp userApp) {
        return userAppMapper.selectUserAppByUserAppType(userApp);
    }

    /**
     * 根据应用ID去查询信息
     * @param userApp
     * @return
     */
    public UserApp selectUserAppListByIdTwo(UserApp userApp) {
        return userAppMapper.selectUserAppListById(userApp);
    }
}
