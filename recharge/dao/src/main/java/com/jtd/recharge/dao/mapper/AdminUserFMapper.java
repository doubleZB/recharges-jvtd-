package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.AdminUserF;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AdminUserFMapper {
    List<AdminUserF> selectAdminUserByParam(AdminUser adminuser);
}