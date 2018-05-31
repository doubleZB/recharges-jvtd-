package com.jtd.recharge.service.charge.dict;

import com.jtd.recharge.dao.mapper.DictMapper;
import com.jtd.recharge.dao.po.Dict;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lihuimin on 2016/12/1.
 */
@Service
public class DictService {
    @Resource
    DictMapper dictMapper;

    /**
     * 查找字典表中的内容
     * @param dict
     * @return
     */
    public List<Dict> selectDict(Dict dict) {
        return dictMapper.selectDict(dict);
    }
}
