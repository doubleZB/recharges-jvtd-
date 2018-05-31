package com.jtd.recharge.service.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.base.util.CommonUtil;
import com.jtd.recharge.dao.bean.BalanceRecord;
import com.jtd.recharge.dao.bean.UserBalances;
import com.jtd.recharge.dao.bean.UserGroup;
import com.jtd.recharge.dao.mapper.*;
import com.jtd.recharge.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lihuimin on 2016/11/11.
 * 商户公用业务层
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    @Resource
    public UserMapper userMapper;

    @Resource
    public ChargeProductGroupMapper chargeProductGroupMapper;

    @Resource
    public UserBalanceMapper userBalanceMapper;
    @Resource
    public UserBalancesMapper userBalancesMapper;
    @Resource
    public UserGroupMapper userGroupMapper;
    @Resource
    public UserBalanceMonitorMapper userBalanceMonitorMapper;
    @Resource
    public ChargeProductStoreFMapper chargeProductStoreFMapper;
    @Resource
    UserBalanceDetailMapper userBalanceDetailMapper;
    @Resource
    BalanceRecordMapper balanceRecordMapper;

    public User findUserBySid(String sid) {
        return userMapper.selectUserBySid(sid);
    }

    /**
     * 商户列表
     * @param pageNumber
     * @param pageSize
     * @param u
     * @return
     */
    public PageInfo<User> getUserList(Integer pageNumber, Integer pageSize,User u) {
        PageHelper.startPage(pageNumber,pageSize,"register_time desc");
        Map map = new HashMap();
        map.put("userCnName",u.getUserCnName());
        map.put("userName",u.getUserName());
        map.put("status",u.getStatus());
        map.put("sells",u.getSells());
        List<User> user = userMapper.getUserList(map);
        PageInfo<User> pageInfo = new PageInfo<>(user);
        return pageInfo;
    }

    /**
     * 销售商户列表
     * @param pageNumber
     * @param pageSize
     * @param u
     * @return
     */
    public PageInfo<BalanceRecord> getMarketsUserList(Integer pageNumber, Integer pageSize,User u) {
        PageHelper.startPage(pageNumber,pageSize,"register_time desc");
        Map map = new HashMap();
        map.put("userCnName",u.getUserCnName());
        map.put("userName",u.getUserName());
        map.put("status",u.getStatus());
        map.put("sells",u.getSells());
        List<BalanceRecord> user = balanceRecordMapper.getMarketsUserList(map);
        PageInfo<BalanceRecord> pageInfo = new PageInfo<>(user);
        return pageInfo;
    }

    /**
     * 查看货架组
     * @return
     */
    public List<ChargeProductGroup> selectProductGroup() {
        return chargeProductGroupMapper.selectProductGroup();
    }

    /**
     * 新增商户
     * @param usergroup
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int addUser(UserGroup usergroup) {
        return userGroupMapper.insertUser(usergroup);
    }

    /**
     * 去修改
     * @param usergroup
     * @return
     */
    public Map<String,Object> toUpdateUser(UserGroup usergroup) {
       //根据id查询
        UserGroup u = userGroupMapper.selectUserByid(usergroup);
        //动态追加
        List<ChargeProductGroup> list = chargeProductGroupMapper.selectProductGroup();
        Map<String, Object> map = new HashMap();
        map.put("user", u);
        map.put("list", list);
        return map;
    }

    /**
     * 修改
     * @param usergroup
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateUserList(UserGroup usergroup) {
       return userGroupMapper.updateUserList(usergroup);
    }


    /**
     * 新增banlance
     * @param balance
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int addBalance(UserBalance balance) {
        return userBalanceMapper.insertBalance(balance);
    }


    /**
     * 验证唯一
     * @param
     * @return
     */
    public List<User> selectUserNameMobile(User user) {
        return userMapper.selectUserNameMobile(user);
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    public User UserList(User user) {
        return userMapper.selectUser(user);
    }

    /**
     * 用户额度
     * @param balance
     * @return
     */
    public List<UserBalances> selectUserBalanceList(UserBalances balance) {
        return userBalancesMapper.selectUserBalance(balance);
    }

    /**
     * 修改密码
     * @param user
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updatePassword(User user) {
        return userMapper.updateUserPassword(user);
    }

    /**
     * 根据用户id获取用户信息
     * @return
     */
    public User findUserByUserId(Integer userId) {
        return userMapper.selectUserByUserId(userId);
    }

    /**
     * ip地址修改
     * @param user
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateUserIpAddress(User user){
       return  userMapper.updateUserIpAddress(user);
    }

    /**
     * 密码为空时修改用户
     * @param usergroup
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateUserLists(UserGroup usergroup) {
        return userGroupMapper.updateUserLists(usergroup);
    }

    /**
     * 商户端支付密码修改
     * @param user
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateUserPayPassword(User user) {
        return  userMapper.updateUserPayPassword(user);
    }
    /**
     *余额不足提醒
     * @param balance
     * @return
     */
    public List<UserBalances> selectUserBalanceByUserId(UserBalances balance) {
        return userBalancesMapper.selectUserBalanceByUserId(balance);
    }
    /**
     * 查询提示Monitor表
     * @param userBalanceMonitor
     * @return
     */

    public List<UserBalanceMonitor>  selectUserBalanceMonitorByUserId(UserBalanceMonitor userBalanceMonitor) {
        return  userBalanceMonitorMapper.selectUserBalanceMonitor(userBalanceMonitor);
    }

    /**
     * 新增余额提示开关
     * @param userBalanceMonitor
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int insertUserBalanceMonitor(UserBalanceMonitor userBalanceMonitor) {
        return  userBalanceMonitorMapper.insertUserBalanceMonitor(userBalanceMonitor);
    }
    /**
     * 修改余额提示开关
     * @param userBalanceMonitor
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateUserBalanceMonitor(UserBalanceMonitor userBalanceMonitor) {
        return  userBalanceMonitorMapper.updateUserBalanceMonitor(userBalanceMonitor);
    }
    /**
     * 修改余额提示开关
     * @param userBalanceMonitor
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateUserBalanceMonitors(UserBalanceMonitor userBalanceMonitor) {
        return  userBalanceMonitorMapper.updateUserBalanceMonitors(userBalanceMonitor);
    }
    /**
     *修改绑定用户状态
     * @param user
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateUserMobile(User user) {
        return userMapper.updateUserMobile(user);
    }
    /**
     * 账户信息详情
     * @return
     */
    public User selectUserById(User user) {
        return userMapper.selectUserById(user);
    }

    /**
     * 用户端页面充值通过手机号获取产品
     * @param userMobileRecharge
     * @return
     */
    public List<UserMobileRecharge> selectUserMobileProductStore(UserMobileRecharge userMobileRecharge){
        return chargeProductStoreFMapper.selectUserMobileProductStore(userMobileRecharge);
    }
    /**
     * 根据状态查询信息Monitor
     * @return
     */
    public List<UserBalanceMonitor> selectUserBalanceMonitorStatusTwo() {
        return userBalanceMonitorMapper.selectUserBalanceMonitorStatusTwo();
    }
    /**
     * 根据状态查询信息Monitor
     * @return
     */
    public List<UserBalanceMonitor> selectUserBalanceMonitorStatus() {
        return userBalanceMonitorMapper.selectUserBalanceMonitorStatus();
    }

    /**
     * 查询用户信息
     * @return
     */
    public List<User> selectUser() {
        return userMapper.selectUserList();
    }

    /**
     * 商户端添加子账户
     * @param user
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int insertUserSon(User user) {
        return userMapper.insertUserSon(user);
    }

    /**
     * 商户端修改子账户
     * @param user
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateUserSon(User user) {
        return userMapper.updateUserSon(user);
    }


    /**
     * 商户端修改调款金额从主账户划入子账户
     */

    /**
     * 商户端修改主账户调款金额
     * @param userBalances
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updateUserBalanceByUserId(UserBalances userBalances) {
        int i = userBalancesMapper.updateUserBalanceByUserId(userBalances);
        if(i>0){
            /**
             * 主账户减款
             */
            //商户端修改子账户调款金额
            userBalancesMapper.updateUserBalanceByUserIdSon(userBalances);
            /**
             * 根据主账户用户id添加user_balance_detail表
             */
            Integer userId = userBalances.getUserId();
            BalanceRecord bal = new BalanceRecord();
            bal.setUserId(userId);

            //根据用户id在去查询balance表余额
            BalanceRecord balance = balanceRecordMapper.selectBlance(bal);

            //调拨金额
            BigDecimal userBalance = userBalances.getUserBalance();
            UserBalanceDetail detail = new UserBalanceDetail();
            //收款存入详情表中 重新赋值 转账
            detail.setCategory(UserBalanceDetail.Category.TRANSFER);
            //出账
            detail.setBillType(UserBalanceDetail.BillType.CHARGE_OFF);
            //帐单流水
            CommonUtil commonUtil = new CommonUtil();
            String payNum = commonUtil.getPayNum();
            //付给账单流水
            detail.setSequence(payNum);
            //账单描述
            detail.setDescription("调款从主账户划入子账户");
            //赋值给detail表中金额
            detail.setAmount(userBalance);
            //赋值给detail表中userId
            detail.setUserId(userId);
            //获取账户余额
            BigDecimal userBalanceAll = balance.getUserBalance();
            //赋值给detail表中用户余额
            detail.setBalance(userBalanceAll);
            //系统时间
            detail.setUpdateTime(new Date());
            //添加balance_detail表中数据
            int j = userBalanceDetailMapper.insertBalanceDetail(detail);
            if(j>0){
                /**
                 * 子账户加款
                 * 根据子账户用户id添加user_balance_detail表
                 */
                Integer userIdSun = userBalances.getpId();
                bal.setUserId(userIdSun);

                //根据子用户id在去查询balance表余额
                BalanceRecord balanceTwo = balanceRecordMapper.selectBlance(bal);
                //入账
                detail.setBillType(UserBalanceDetail.BillType.ENTRY);
                //帐单流水
                CommonUtil commonUtilTwo = new CommonUtil();
                String payNumTwo = commonUtilTwo.getPayNum();
                //付给账单流水
                detail.setSequence(payNumTwo);
                //赋值给detail表中userId
                detail.setUserId(userIdSun);
                //账单描述
                detail.setDescription("主账户转入");
                //获取账户余额
                BigDecimal userBalanceAllTwo = balanceTwo.getUserBalance();
                //赋值给detail表中用户余额
                detail.setBalance(userBalanceAllTwo);
                //添加balance_detail表中数据
                userBalanceDetailMapper.insertBalanceDetail(detail);
            }
        }
    }

    /**
     * 商户端修改子账户调款金额
     * @param userBalances
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updateUserBalanceByUserIdSon(UserBalances userBalances) {
        int i = userBalancesMapper.updateUserBalanceByUserIdSon(userBalances);
        if(i>0){
            /**
             * 主账户加款
             */
            //修改子账户余额
            userBalancesMapper.updateUserBalanceByUserId(userBalances);
            /**
             * 根据字用户id添加user_balance_detail表
             */
            Integer userId = userBalances.getpId();
            BalanceRecord bal = new BalanceRecord();
            bal.setUserId(userId);
            //根据用户id在去查询balance表余额
            BalanceRecord balance = balanceRecordMapper.selectBlance(bal);

            //调拨金额
            BigDecimal userBalance = userBalances.getUserBalance();
            UserBalanceDetail detail = new UserBalanceDetail();
            //收款存入详情表中 重新赋值 转账
            detail.setCategory(UserBalanceDetail.Category.TRANSFER);
            //入账
            detail.setBillType(UserBalanceDetail.BillType.ENTRY);
            //帐单流水
            CommonUtil commonUtil = new CommonUtil();
            String payNum = commonUtil.getPayNum();
            //付给账单流水
            detail.setSequence(payNum);
            //账单描述
            detail.setDescription("调款从子账户划入主账户");
            //赋值给detail表中金额
            detail.setAmount(userBalance);
            //赋值给detail表中userId
            detail.setUserId(userId);
            //获取账户余额
            BigDecimal userBalanceAll = balance.getUserBalance();
            //赋值给detail表中用户余额
            detail.setBalance(userBalanceAll);
            //系统时间
            detail.setUpdateTime(new Date());
            //添加balance_detail表中数据
            int j = userBalanceDetailMapper.insertBalanceDetail(detail);
            if(j>0){
                /**
                 * 子账户减款
                 * 根据主账户用户id添加user_balance_detail表
                 */
                Integer userIdFather = userBalances.getUserId();
                bal.setUserId(userIdFather);

                //根据用户id在去查询balance表余额
                BalanceRecord balanceTwo = balanceRecordMapper.selectBlance(bal);
                //出账
                detail.setBillType(UserBalanceDetail.BillType.CHARGE_OFF);
                //帐单流水
                CommonUtil commonUtilTwo = new CommonUtil();
                String payNumTwo = commonUtilTwo.getPayNum();
                //付给账单流水
                detail.setSequence(payNumTwo);
                //账单描述
                detail.setDescription("子账户转出");
                //赋值给detail表中userId
                detail.setUserId(userIdFather);
                //获取账户余额
                BigDecimal userBalanceAllTwo = balanceTwo.getUserBalance();
                //赋值给detail表中用户余额
                detail.setBalance(userBalanceAllTwo);
                //添加balance_detail表中数据
                userBalanceDetailMapper.insertBalanceDetail(detail);
            }
        }
    }


    /**
     * 根据用户ID去查询用户表中pId
     * @param users
     * @return
     */
    public List<User> selectUserListByPId(User users) {
        return userMapper.selectUserListByPId(users);
    }


    /**
     * 根据用户ID去修改用户全称
     * @param user
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateUser(User user) {
        return userMapper.updateUserSon(user);
    }

    /**
     * 查询商户列表
     * @param user 查询条件
     * @return 商户列表
     */
    public List<User> queryUserList(User user) {
        return userMapper.queryUserList(user);
    }
}