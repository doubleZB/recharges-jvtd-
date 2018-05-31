package com.jtd.recharge.service.iot;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jtd.recharge.dao.mapper.UserMapper;
import com.jtd.recharge.dao.po.User;

/**
 * 用户鉴权
 * 
 * @author ninghui
 *
 */
@Service
public class UserApiAuthService {
	@Resource
	UserMapper userMapper;
	
	 /**
     * 根据token找用户
     * @param token
     * @return
     */
    public User findUserByToken(String token) {
        return userMapper.selectUserByToken(token);
    }
}
