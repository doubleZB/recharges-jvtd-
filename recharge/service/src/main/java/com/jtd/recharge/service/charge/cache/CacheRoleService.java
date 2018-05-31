package com.jtd.recharge.service.charge.cache;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.mapper.*;
import com.jtd.recharge.dao.po.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liyabin on 2017/8/22.
 */
@Service
public class CacheRoleService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private CacheRoleMapper cacheRoleMapper;
    @Resource
    private UserAppMapper userAppMapper;
    @Resource
    private ChargeProductStoreMapper chargeProductStoreMapper;
    @Resource
    private ChargeProductStoreSupplyMapper chargeProductStoreSupplyMapper;
    @Resource
    private ChargeSupplyPositionMapper chargeSupplyPositionMapper;
    @Resource
    private ChargeSupplyMapper chargeSupplyMapper;


    public List<CacheRole> getRuleList(CacheRole cacheRole) {
        return cacheRoleMapper.selectCache(cacheRole);
    }

    public List<CacheRole> checkRepeatRule(CacheRole cacheRole) {
        return cacheRoleMapper.checkRepeatRule(cacheRole);
    }


    /**
     * 获取缓存规则的列表，和分页
     *
     * @param pageNumber
     * @param pageSize
     * @param cacheRole
     * @return
     */
    public PageInfo<CacheRole> getRulePage(Integer pageNumber, Integer pageSize, CacheRole cacheRole) {
        if (pageNumber == null || "".equals(pageNumber)) {
            pageNumber = 0;
        }
        if (pageSize == null || "".equals(pageSize)) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNumber, pageSize, "id desc");
        List<CacheRole> roleList = cacheRoleMapper.selectRuleListByConditions(cacheRole);
        for (CacheRole role : roleList) {
            String codeStr = getPosition(role);
            role.setPositionCodeStr(codeStr);
        }
        PageInfo<CacheRole> pageInfo = new PageInfo<>(roleList);
        return pageInfo;
    }

    public Boolean checkRuleIlegle(CacheRole cacheRole) {
        int count = cacheRoleMapper.checkRuleIlegle(cacheRole);
        if (count > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取用户卡品
     *
     * @param cacheRole
     */
    public String getPosition(CacheRole cacheRole) {

        String positionCode = cacheRole.getPositionCode();
        String positionCodeStr = "";
        if (!StringUtils.isEmpty(positionCode)) {
            List<String> list = new ArrayList<String>();
            List<String> positioncodes = new ArrayList<String>();
            for (String str : positionCode.split(",")) {
                if ("111".equals(str)) {
                    list.add("全部移动流量");
                } else if ("222".equals(str)) {
                    list.add("全部联通流量");
                } else if ("333".equals(str)) {
                    list.add("全部电信流量");
                } else if ("444".equals(str)) {
                    list.add("全移动话费");
                } else if ("555".equals(str)) {
                    list.add("全联通话费");
                } else if ("666".equals(str)) {
                    list.add("全电信话费");
                } else {
                    positioncodes.add(str);
                }
            }
            if (positioncodes.size() > 0) {
                cacheRole.setPositionCode(StringUtils.join(positioncodes, ","));
                List<ChargeSupplyPosition> codeList = getPositionList(cacheRole);
                List<String> positionName = new ArrayList<>();
                for (ChargeSupplyPosition posotion : codeList) {
                    positionName.add(posotion.getName());
                }
                list.addAll(positionName);


            }
            positionCodeStr = StringUtils.join(list, ",");
        }
        return positionCodeStr;
    }

    public List<ChargeSupplyPosition> getPositionList(CacheRole cacheRole) {
        List<ChargeSupplyPosition> codeList = new ArrayList<ChargeSupplyPosition>();

        Integer[] oprators = {1, 2, 3};
        if (!"0".equals(cacheRole.getOperator())) {
            String[] op = cacheRole.getOperator().split(",");
            oprators = new Integer[op.length];
            for (int i = 0; i < op.length; i++) {
                oprators[i] = Integer.parseInt(op[i]);
            }
        }

        List<String> strArr = null;
        if (cacheRole.getPositionCode() != null)
            strArr = Arrays.asList(cacheRole.getPositionCode().split(","));

        if (cacheRole.getCacheType() == 1) {
            List<Integer> groupIds = userAppMapper.getGroupIds(cacheRole.getBusinessType(), cacheRole.getUserId());
            if (groupIds.size() > 0) {
                List<Integer> storeIds = chargeProductStoreMapper.getStoreIds(groupIds);
                if (storeIds.size() > 0) {
                    List<Integer> supplyIds = chargeProductStoreSupplyMapper.getSupplyId(cacheRole.getBusinessType(), storeIds);
                    if (supplyIds.size() > 0)
                        codeList = chargeSupplyPositionMapper.getSupplyPosition(cacheRole.getBusinessType(), oprators, supplyIds, strArr);
                }
            }
        } else if (cacheRole.getCacheType() == 2) {
            List<Integer> supplyIds = new ArrayList<Integer>();
            supplyIds.add(Integer.parseInt(cacheRole.getSupplier()));
            codeList = chargeSupplyPositionMapper.getSupplyPosition(cacheRole.getBusinessType(), oprators, supplyIds, strArr);
        }
        return codeList;
    }

    public int insert(CacheRole record) {
        return cacheRoleMapper.insert(record);
    }

    public CacheRole selectByPrimaryKey(long id) {
        return cacheRoleMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKey(CacheRole record) {
        return cacheRoleMapper.updateByPrimaryKey(record);
    }

    public int deleteByPrimaryKey(Long id) {
        return cacheRoleMapper.deleteByPrimaryKey(id);
    }
}
