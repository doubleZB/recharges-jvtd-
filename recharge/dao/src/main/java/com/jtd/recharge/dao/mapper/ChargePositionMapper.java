package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.ChargePosition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChargePositionMapper {

    List<ChargePosition> selectByPrimary(ChargePosition chargePosition);

    /**
     * 根据业务类型,档位编码,运营商查询是否存在此档位
     * @param businessType
     * @param code
     * @param operator
     * @return
     */
    ChargePosition selectPosition(@Param("businessType") Integer businessType,
                                  @Param("code") String code,
                                  @Param("operator") Integer operator);


    List<ChargePosition> selectUserPosition(@Param("groupId") Integer groupId,@Param("businessType") Integer businessType);
}