package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.ChargeSupplyPosition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 供应商档位DAO
 */
@Repository
public interface ChargeSupplyPositionMapper {

    ChargeSupplyPosition selectSupplyPostionByParam(@Param("businessType") int businessType,
                                                    @Param("supplyId") int supplyId,
                                                    @Param("positionCode") String positionCode);

    List<ChargeSupplyPosition> getSupplyPosition(@Param("businessType") Integer businessType, @Param("operator") Integer[] operator, @Param("supplyIds") List<Integer> supplyIds, @Param("positioncodes") List<String> positioncodes);

    List<String> getSupplyPositionById(@Param("ids") List<String> ids);
}