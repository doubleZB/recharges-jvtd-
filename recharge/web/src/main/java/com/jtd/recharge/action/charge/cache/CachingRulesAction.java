package com.jtd.recharge.action.charge.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.base.cache.redis.RedisTemplate;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.dao.po.CacheRole;
import com.jtd.recharge.dao.po.ChargeSupplyPosition;
import com.jtd.recharge.service.charge.cache.CacheRoleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lhm on 2017/8/11.
 * 缓存规则
 */
@Controller
@RequestMapping("/cache")
public class CachingRulesAction {
    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    private CacheRoleService cacheRoleService;
    @Resource(name = "commRedisTemplate")
    RedisTemplate redisTemplate;

    /**
     * 缓存订单记录页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/cacheRule")
    public String cacheRule() {

        return "charge/cache/cachingRules";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/editRule")
    public String editRule(Integer id,Integer businessType,Integer cacheType,String objectName, HttpServletRequest request) {
        CacheRole role = cacheRoleService.selectByPrimaryKey(id);
        String rulePositionCode = role.getPositionCode();
        role.setPositionCode(null);
        request.setAttribute("role", role);
        request.setAttribute("rulePositionCode", rulePositionCode);
        request.setAttribute("businessType", businessType);
        request.setAttribute("cacheType", cacheType);
        request.setAttribute("objectName", objectName);
        request.setAttribute("pageNum", request.getParameter("pageNum"));
        request.setAttribute("positionCode", JSON.toJSONString(cacheRoleService.getPositionList(role)));
        return "charge/cache/editRule";
    }

    /**
     * 分页查询-缓存订单记录
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/cacheRuleSearch")
    public PageInfo cacheRuleSearch(Integer pageNumber, Integer pageSize, CacheRole cacheRole) {
        if (pageNumber == null || "".equals(pageNumber)) {
            pageNumber = 0;
        }
        if (pageSize == null || "".equals(pageSize)) {
            pageSize = 10;
        }
        PageInfo pageInfo = new PageInfo();
        try {
            pageInfo = cacheRoleService.getRulePage(pageNumber, pageSize, cacheRole);
        } catch (Exception e) {
            log.error(e);
        }
        return pageInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/saveRule")
    public String saveRule(CacheRole cacheRole, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("status", "true");
        map.put("msg", "保存成功");
        if (checkRuleIlegle(cacheRole) == false) {
            map.put("status", "false");
            map.put("msg", "保存失败，缓存规则超出上限");
            return JSON.toJSONString(map);
        }
        cacheRole.setCreateUser(request.getSession().getAttribute("admin_name").toString());
        String key = "";
        List<CacheRole> checkRepeatRule = cacheRoleService.checkRepeatRule(cacheRole);
        if (checkRepeatRule.size() == 0) {
            if (cacheRole.getId() == null) {
                cacheRole.setCreateTime(new Date());
                if (cacheRole.getCacheType() == CacheRole.cacheTypes.SUPPLIERCACHE) {
                    key = cacheRole.getSupplier();
                } else {
                    key = String.valueOf(cacheRole.getUserId());
                }
                if (cacheRole.getCacheType() == 2) {
                    redisTemplate.set(SysConstants.CACHE_SUPPLY_KEY + key, String.valueOf(JSONObject.toJSON(cacheRole)));
                    log.info("生成的缓存规则key:" + SysConstants.CACHE_SUPPLY_KEY + key + ",value:" + JSONObject.toJSON(cacheRole));
                }
                if (cacheRole.getCacheType() == 1) {
                    if(cacheRole.getBusinessType()==1) {
                        redisTemplate.set(SysConstants.CACHE_USER_FLOW_KEY + key, String.valueOf(JSONObject.toJSON(cacheRole)));
                        log.info("生成的缓存规则key:" + SysConstants.CACHE_USER_FLOW_KEY + key + ",value:" + JSONObject.toJSON(cacheRole));
                    }else{
                        redisTemplate.set(SysConstants.CACHE_USER_TEL_KEY + key, String.valueOf(JSONObject.toJSON(cacheRole)));
                        log.info("生成的缓存规则key:" + SysConstants.CACHE_USER_TEL_KEY + key + ",value:" + JSONObject.toJSON(cacheRole));
                    }
                }
                int saveStatus = cacheRoleService.insert(cacheRole);
                if (saveStatus == 0) {
                    map.put("status", "false");
                    map.put("msg", "保存失败");
                }
            } else {
                if (cacheRole.getCacheType() == CacheRole.cacheTypes.SUPPLIERCACHE) {
                    key = cacheRole.getSupplier();
                } else {
                    key = String.valueOf(cacheRole.getUserId());
                }

                if (cacheRole.getCacheType() == 2) {
                    redisTemplate.set(SysConstants.CACHE_SUPPLY_KEY + key, String.valueOf(JSONObject.toJSON(cacheRole)));
                    log.info("生成的缓存规则key:" + SysConstants.CACHE_SUPPLY_KEY + key + ",value:" + JSONObject.toJSON(cacheRole));
                }
                if (cacheRole.getCacheType() == 1) {
                    if(cacheRole.getBusinessType()==1) {
                        redisTemplate.set(SysConstants.CACHE_USER_FLOW_KEY + key, String.valueOf(JSONObject.toJSON(cacheRole)));
                        log.info("生成的缓存规则key:" + SysConstants.CACHE_USER_FLOW_KEY + key + ",value:" + JSONObject.toJSON(cacheRole));
                    }else{
                        redisTemplate.set(SysConstants.CACHE_USER_TEL_KEY + key, String.valueOf(JSONObject.toJSON(cacheRole)));
                        log.info("生成的缓存规则key:" + SysConstants.CACHE_USER_TEL_KEY + key + ",value:" + JSONObject.toJSON(cacheRole));
                    }
                    }
                int saveStatus = cacheRoleService.updateByPrimaryKey(cacheRole);
                if (saveStatus == 0) {
                    map.put("status", "false");
                    map.put("msg", "保存失败");
                }
            }
        } else {
            map.put("status", "false");
            map.put("msg", "数据重复请检查后再添加");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 验证规则，如果为商户可以有两个规则，流量和话费，如果为供应商只能有一个
     *
     * @return id 商户或供应商的id
     * cacheType 业务类型 1为商户，2为供应商
     * businessType 1为流量，2为话费
     */
    @ResponseBody
    @RequestMapping(value = "/checkRuleIlegle")
    public Boolean checkRuleIlegle(CacheRole cacheRole) {
        return cacheRoleService.checkRuleIlegle(cacheRole);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteRule")
    public int deleteByPrimaryKey(int id) {
        CacheRole cacheRole = cacheRoleService.selectByPrimaryKey(id);
        String key = "";
        if (cacheRole.getCacheType() == CacheRole.cacheTypes.SUPPLIERCACHE) {
            key = cacheRole.getSupplier();
        } else {
            key = String.valueOf(cacheRole.getUserId());
        }

        if (cacheRole.getCacheType() == 2) {
            redisTemplate.delete(SysConstants.CACHE_SUPPLY_KEY + key);
            log.info("删除的缓存规则key:" + SysConstants.CACHE_SUPPLY_KEY + key + ",value:" + JSONObject.toJSON(cacheRole));
        }
        if (cacheRole.getCacheType() == 1) {
            if(cacheRole.getBusinessType()==1) {
                redisTemplate.delete(SysConstants.CACHE_USER_FLOW_KEY + key);
                log.info("删除的缓存规则key:" + SysConstants.CACHE_USER_FLOW_KEY + key + ",value:" + JSONObject.toJSON(cacheRole));
            }else{
                redisTemplate.delete(SysConstants.CACHE_USER_TEL_KEY + key);
                log.info("删除的缓存规则key:" + SysConstants.CACHE_USER_TEL_KEY + key + ",value:" + JSONObject.toJSON(cacheRole));
            }
        }
        return cacheRoleService.deleteByPrimaryKey((long) id);
    }

    /**
     * 获取卡品
     *
     * @param cacheRole 运营商
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/getPosition")
    public List<ChargeSupplyPosition> getPosition(CacheRole cacheRole) {
        return cacheRoleService.getPositionList(cacheRole);
    }
}
