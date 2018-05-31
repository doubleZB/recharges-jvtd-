package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.Dict;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(Integer id);

    /**
     * wxp 2016-11-16 16:45:00
     * 查询所有省份
     * @return
     */
    List<Dict> selectAllDict(Dict record);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);

    List<Dict> selectDict(Dict dict);

    List<Dict> selectDictByModule(Dict dict);
}