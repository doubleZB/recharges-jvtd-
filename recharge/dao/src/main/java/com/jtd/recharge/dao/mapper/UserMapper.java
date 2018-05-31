package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectUserBySid(String sid);

    User selectUserByToken(String token);

    /**
     * 商户商户列表
     * @param map
     * @return
     */
    List<User> getUserList(Map map);

    List<User> selectUserNameMobile(User user);

    User selectUser(User user);

    int updateUserPassword(User user);

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    User selectUserByUserId(@Param("userId") Integer userId);

    /**
     * ip地址修改
     * @param user
     * @return
     */
    int updateUserIpAddress(User user);

    /**
     * 支付密码修改
     * @param user
     * @return
     */
    int updateUserPayPassword(User user);
    /**
     *修改绑定用户状态
     * @param user
     * @return
     */
    int updateUserMobile(User user);
    /**
     * 账户信息详情
     * @return
     */
    User selectUserById(User user);

    /**
     * 查询用户所有信息
     * @return
     */
    List<User> selectUserList();

    /**
     * 商户端添加子账户
     * @param user
     * @return
     */
    int insertUserSon(User user);

    /**
     * 商户端修改子账户
     * @param user
     * @return
     */
    int updateUserSon(User user);

    /**
     * 根据用户ID去查询用户表中pId
     * @param users
     * @return
     */
    List<User> selectUserListByPId(User users);


    /**
     * 查询商户列表
     * @param user 查询条件
     * @return 商户列表
     */
    List<User> queryUserList(User user);
}