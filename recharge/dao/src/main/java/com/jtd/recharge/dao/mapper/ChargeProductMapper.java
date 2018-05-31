package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.bean.util.ProductParam;
import com.jtd.recharge.dao.po.ChargeProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChargeProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargeProduct record);

    int insertSelective(ChargeProduct record);

    ChargeProduct selectByPrimaryKey(Integer id);

    List<ChargeProduct> selectProductParam(ProductParam productParam);

    int updateByPrimaryKeySelective(ChargeProduct record);

    int updateByPrimaryKey(ChargeProduct record);

    /**
     * 查询productId
     */
    ChargeProduct selectProductId(@Param("businessType") int businessType,
                                  @Param("operator") int operator,
                                  @Param("provinceId") int provinceId,
                                  @Param("positionCode") String positionCode);
}