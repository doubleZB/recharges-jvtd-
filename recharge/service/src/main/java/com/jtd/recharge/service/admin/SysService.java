package com.jtd.recharge.service.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.mapper.AdminMenuMapper;
import com.jtd.recharge.dao.mapper.AdminRoleMapper;
import com.jtd.recharge.dao.mapper.AdminRolelMenuMapper;
import com.jtd.recharge.dao.mapper.AdminUserFMapper;
import com.jtd.recharge.dao.mapper.AdminUserMapper;
import com.jtd.recharge.dao.mapper.AdminUserRoleMapper;
import com.jtd.recharge.dao.po.AdminMenu;
import com.jtd.recharge.dao.po.AdminMenuShow;
import com.jtd.recharge.dao.po.AdminRole;
import com.jtd.recharge.dao.po.AdminRolelMenu;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.AdminUserF;
import com.jtd.recharge.dao.po.AdminUserRole;

/**
 * Created by WXP 2016-11-25 11:13:38.
 */
@Service
public class SysService {

    @Resource
    AdminMenuMapper menuMapper;
    @Resource
    AdminRoleMapper roleMapper;
    @Resource
    AdminRolelMenuMapper rolelMenuMapper;
    @Resource
    AdminUserRoleMapper userRoleMapper;
    @Resource
    AdminUserMapper adminUserMapper;
    @Resource
    AdminUserFMapper adminUserFMapper;


    /**
     * 获取所有权限列表
     * @return
     */
    public List<AdminMenuShow> getAllMenu(){
        return trans(menuMapper.select(),null) ;
    }
    /**
     * 根据roleid获取所有权限列表
     * @return
     */
    public List<AdminMenuShow> getAllMenubyRoleid(int roleid){
        return trans(menuMapper.select(),roleid) ;
    }

    /**
     * 根据角色名称获取所有角色列表
     * @return
     */
    public List<AdminRole> getAllRole(String roleName){
        AdminRole role = new AdminRole();
        if(roleName!=null){
            role.setName(roleName);
        }
        return roleMapper.selectAll(role);
    }

    /**
     * 根据角色id查看角色权限
     * @param menus
     * @param roleId
     * @return
     */
    public List<AdminMenuShow> trans(List<AdminMenu> menus, Integer roleId){
        List<AdminMenuShow> menusShows = new ArrayList<>();
        if(roleId==null){
            for (int i = 0; i <menus.size() ; i++) {
                AdminMenuShow menuShow = new AdminMenuShow();
                AdminMenu menu = menus.get(i);
                menuShow.setId(menu.getId());
                menuShow.setName(menu.getName());
                menuShow.setpId(menu.getParentId());
                menusShows.add(menuShow);
            }
        }else {
            for (int i = 0; i <menus.size() ; i++) {
                AdminMenu menu = menus.get(i);
                AdminMenuShow menuShow = new AdminMenuShow();
                AdminRolelMenu roleMenu = new AdminRolelMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getId());

                List<AdminRolelMenu> rms = rolelMenuMapper.selectByParam(roleMenu);
                if (rms!=null&&!rms.isEmpty()){
                    menuShow.setChecked(true);
                }
                menuShow.setId(menu.getId());
                menuShow.setName(menu.getName());
                menuShow.setpId(menu.getParentId());
                menusShows.add(menuShow);
            }
        }
        return menusShows;
    }

    /**
     * 判断角色名是否存在
     * @param rolrname
     * @return
     */
    public String  isExistRole(String rolrname){
        AdminRole role = new AdminRole();
        role.setName(rolrname);
        List<AdminRole> roles = roleMapper.selectAll(role);
        if (roles!=null&& !roles.isEmpty()){
            return "T";//存在
        }
        return "F";//不存在
    }

    /**
     * 添加角色
     * @param rolename
     * @param menusId
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void addRole(String rolename,String menusId){
        AdminRole role = new AdminRole();
        role.setName(rolename);
        role.setUpdateTime(new Date());
        roleMapper.insert(role);
        if(menusId!=null&&menusId.length()>0){
            String[] msid = menusId.split(",");
            AdminRolelMenu rm = new AdminRolelMenu();
            for (int i = 0; i <msid.length ; i++) {
                rm.setRoleId(role.getId());
                rm.setMenuId(Integer.parseInt(msid[i]));
                rm.setUpdateTime(new Date());
                rolelMenuMapper.insert(rm);
            }
        }
    }


    /**
     * 修改角色权限
     * @param roleID
     * @param menusId
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updateRole(int roleID,String menusId){
        //先删除以前的对应关系
        AdminRolelMenu rm = new AdminRolelMenu();
        rm.setRoleId(roleID);
        rolelMenuMapper.deleteByParam(rm);
        if(menusId!=null&&menusId.length()>0){
            String[] msid = menusId.split(",");
            rm = new AdminRolelMenu();
            rm.setRoleId(roleID);
            for (int i = 0; i <msid.length ; i++) {
                rm.setMenuId(Integer.parseInt(msid[i]));
                rm.setUpdateTime(new Date());
                rolelMenuMapper.insert(rm);
            }
        }
    }

    /**
     * 根据角色id删除角色
     * @param roleID
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String delRole(int roleID){
        String msg = "F";
        roleMapper.deleteByPrimaryKey(roleID);
        //缺少删除用户关联部分
        AdminUserRole userRole = new AdminUserRole();
        userRole.setRoleId(roleID);
        userRoleMapper.deleteByParam(userRole);
        msg = "T";
        return msg;
    }

    /**
     * 根据用户id查找相应权限
     * @param uid
     * @return
     */
    public List<AdminMenu> getMenusByUid(int uid){
        List<AdminMenu> menus = null;
        List<AdminUserRole> adminUserRoles =userRoleMapper.selectByUid(uid);
        if(adminUserRoles!=null&&!adminUserRoles.isEmpty()){
            AdminUserRole adminUserRole = adminUserRoles.get(0);
            AdminRolelMenu adminRolelMenu = new AdminRolelMenu();
            adminRolelMenu.setRoleId(adminUserRole.getRoleId());
            List<AdminRolelMenu> adminRolelMenus = rolelMenuMapper.selectByParam(adminRolelMenu);
            if(adminRolelMenus!=null && !adminRolelMenus.isEmpty()){
                menus = new ArrayList<>();
                for (int i = 0; i <adminRolelMenus.size() ; i++) {
                    AdminRolelMenu roleMenu = adminRolelMenus.get(i);
                    AdminMenu menu = menuMapper.selectByPrimaryKey(roleMenu.getMenuId());
                    menus.add(menu);
                }
            }
        }

        return menus;
    }

    /**
     * 将菜单格式化到页面显示
     * @param menus
     * @return
     */
    public NavData getMenuJson(List<AdminMenu> menus){
        Map<String ,Object> map = new TreeMap<>();
        NavData data = new NavData();
        if (menus!=null&&!menus.isEmpty()){

            List<AdminMenu> rootMenuList = new ArrayList<>();
          
            Map<Integer,List<AdminMenu>> secondMenuMap = new HashMap<>();
            List<AdminMenu> menusLast = new ArrayList<>();
            Set<Integer> idSet = RootMenuMeta.getIdSet();
            for (int i = 0; i <menus.size() ; i++) {
                AdminMenu menu = menus.get(i);
                if(menu.getParentId()==0){
                    rootMenuList.add(menu);
                }else {
                    if (idSet.contains(menu.getParentId())){
                    	getSecondMenu(secondMenuMap, menu.getParentId()).add(menu);
                    }else {
                            menusLast.add(menu);
                        }
                    }
            }
            AdminMenu[] sortList = new AdminMenu[rootMenuList.size()];
            rootMenuList.toArray(sortList);
			Arrays.sort(sortList, new Comparator<AdminMenu>() {  
		        public int compare(AdminMenu n1,AdminMenu n2)  
		        {  
		        	RootMenuMeta meta1 = RootMenuMeta.parse(n1.getId());
					RootMenuMeta meta2 = RootMenuMeta.parse(n2.getId());
					if(meta1.ordinal() < meta2.ordinal()) {
		        		return -1;
		        	}
		        	return 1;
		        }  
		    });
            map.put("lst",sortList);

            Map<Integer,List<SecNav>> secMap = new HashMap<>();
            if(!menusLast.isEmpty()){
            	for (List<AdminMenu> secMenuList : secondMenuMap.values()) {
            		 if(!secMenuList.isEmpty()){
            			 List<SecNav> sec1 = new ArrayList<>();
                         for (int i = 0; i <secMenuList.size() ; i++) {
                            AdminMenu menu1 = secMenuList.get(i);
                            List<AdminMenu> menus11 = new ArrayList<>();
							for (int j = 0; j <menusLast.size() ; j++) {
                                 AdminMenu menuLast = menusLast.get(j);
                                 if(menuLast.getParentId()==menu1.getId()){
                                     menus11.add(menuLast);
                                 }
                             }
                             SecNav secNav = new SecNav();
                             secNav.setNext(menus11);
                             secNav.setTittle(menu1.getName());
                             sec1.add(secNav);
                             menus11 = new ArrayList<>();
                         }
                         secMap.put(secMenuList.get(0).getParentId(),sec1);
                     }
				}
               
            }
            List<List<SecNav>> sortSecList = new ArrayList<>();
            //确保二级菜单的输出顺序和一级菜单一致
            for (AdminMenu rootMenu : sortList) {
				for (Map.Entry<Integer,List<SecNav>> entry : secMap.entrySet()) {
					if(entry.getKey().equals(rootMenu.getId())) {
						sortSecList.add(entry.getValue());
						break;
					}
				}
			}
            int index = 1;
            for (List<SecNav> sec1 : sortSecList) {
            	 if (sec1!=null){
                     map.put("parentNav"+index,sec1);
                     index++;
                 }
			}
           
          
        }
        data.setData(map);

        return data;
    }
	private List<AdminMenu> getSecondMenu(Map<Integer, List<AdminMenu>> secondMenuMap, Integer key) {
		List<AdminMenu> list = secondMenuMap.get(key);
		if(list == null) {
			secondMenuMap.put(key, new ArrayList<AdminMenu>());
		}
		return secondMenuMap.get(key);
	}

    /**
     * 用到的菜单辅助类
     */
    class SecNav{
        private String tittle;
        private List<AdminMenu> next;

        public String getTittle() {
            return tittle;
        }

        public void setTittle(String tittle) {
            this.tittle = tittle;
        }

        public List<AdminMenu> getNext() {
            return next;
        }

        public void setNext(List<AdminMenu> next) {
            this.next = next;
        }
    }


    /**
     * 检查后台用户名是否存在
     * @param loginName
     * @return
     */
    public String checkAdminUserName(String loginName){
        AdminUser record = new AdminUser();
        record.setAdminName(loginName);
        List<AdminUser> adminUsers =adminUserMapper.selectAdminUserByParam(record);
        if (adminUsers!=null&&!adminUsers.isEmpty()){
            return "T";
        }else {
            return "F";
        }
    }

    /**
     * 添加后台用户
     * @param user
     * @param roleid
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void addAdminUser(AdminUser user,int roleid){
        adminUserMapper.insertSelective(user);
        AdminUserRole userRole = new AdminUserRole();
        userRole.setUid(user.getId());
        userRole.setRoleId(roleid);
        userRoleMapper.insertSelective(userRole);
    }


    /**
     * 分页查询后台用户
     * @param loginName
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageInfo<AdminUserF> getAdminUser(String loginName,Integer pageNumber, Integer pageSize){
        AdminUser user = new AdminUser();
        if (loginName!=null&&loginName.length()!=0){
            user.setAdminName(loginName);
        }
        PageHelper.startPage(pageNumber, pageSize, "a.id desc");
        List<AdminUserF> adminUserFs = adminUserFMapper.selectAdminUserByParam(user);
        PageInfo<AdminUserF> pageInfo = new PageInfo<>(adminUserFs);
        return pageInfo;
    }


    /**
     * 删除后台用户
     * @param uid
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean delAdminUser(Integer uid){
        //！没有删除用户--角色映射表！//
        int count = adminUserMapper.deleteByPrimaryKey(uid);
        if(count==1){
            return true;
        }
        return false;
    }

    /**
     * 根据用户id获取单个后台用户信息
     * @param uid
     * @return
     */
    public AdminUserF getAdminUserByid(Integer uid){
        AdminUser user = new AdminUser();
        user.setId(uid);
        List<AdminUserF> adminUserFs = adminUserFMapper.selectAdminUserByParam(user);
        if (adminUserFs!=null&&!adminUserFs.isEmpty()){
            return adminUserFs.get(0);
        }
        return null;
    }

    /**
     * 修改后台用户信息
     * @param userId
     * @param userName
     * @param password
     * @param roleId
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void  toEditAdminUser(int userId,String userName,String password,int roleId){
        AdminUser adminUser = new AdminUser();
        adminUser.setId(userId);
        adminUser.setName(userName);
        adminUser.setPassword(password);
        adminUser.setUpdateTime(new Date());
        adminUserMapper.updateByPrimaryKeySelective(adminUser);

        AdminUserRole userRole = new AdminUserRole();
        userRole.setUid(userId);
        userRoleMapper.deleteByParam(userRole);

        userRole.setRoleId(roleId);
        userRoleMapper.insertSelective(userRole);

    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updatepswd(AdminUser user){
        user.setUpdateTime(new Date());
        adminUserMapper.updateByPrimaryKeySelective(user);
    }
}
