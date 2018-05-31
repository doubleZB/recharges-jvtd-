package com.jtd.recharge.service.admin;

import com.jtd.recharge.dao.mapper.AdminUserMapper;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @autor jipengkun
 */
@Service
public class AdminService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private AdminUserMapper adminUserMapper;
    /**
     *根据管理员id查询用户
     * @return
     */
    public AdminUser queryUserByUserId(Integer userid){
        return adminUserMapper.selectByPrimaryKey(userid);
    }

    /**
     *查询用户
     * @return
     */
    public AdminUser queryUser(AdminUser adminuser) {
        return adminUserMapper.selectAdminUser(adminuser);
    }


    /**
     * 查询登录用户
     */

    public List<AdminUser> selectAdminUser(AdminUser adminuser){
        return adminUserMapper.selectAdminUserByParam(adminuser);
    }

}
