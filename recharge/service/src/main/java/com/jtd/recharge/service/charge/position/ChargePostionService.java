package com.jtd.recharge.service.charge.position;

import com.jtd.recharge.dao.bean.util.ProductParam;
import com.jtd.recharge.dao.mapper.ChargePositionMapper;
import com.jtd.recharge.dao.mapper.ChargeProductMapper;
import com.jtd.recharge.dao.mapper.ChargeUserGroupMapper;
import com.jtd.recharge.dao.po.ChargePosition;
import com.jtd.recharge.dao.po.ChargeProduct;
import com.jtd.recharge.dao.po.UserApp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @autor jipengkun
 */
@Service
public class ChargePostionService {

    @Resource
    ChargeUserGroupMapper chargeUserGroupMapper;

    @Resource
    ChargePositionMapper chargePositionMapper;

    public List<ChargePosition> getPosition(ChargePosition chargePosition){
        return  chargePositionMapper.selectByPrimary(chargePosition);
    }

    /**
     * 获取商户对应业务类型档位编码
     * @param userApp
     * @return
     */
    public List<ChargePosition> findUserPosition(UserApp userApp, Integer businessType) {
        int groupId = chargeUserGroupMapper.selectGroupIdByUserIdAndappType(userApp);
        return chargePositionMapper.selectUserPosition(groupId,businessType);
    }
}
