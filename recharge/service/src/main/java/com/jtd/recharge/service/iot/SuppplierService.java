package com.jtd.recharge.service.iot;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.mapper.IotSupplyMapper;
import com.jtd.recharge.dao.po.IotSupply;
import com.jtd.recharge.define.SupplierRank;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */
@Service
public class SuppplierService {

    @Resource
    private IotSupplyMapper iotSupplyMapper;

    public PageInfo<IotSupply> getSupplierList(String name, Integer pageNumber, Integer pageSize){
        PageHelper.startPage(pageNumber,pageSize,"id desc");
        IotSupply supply = new IotSupply();
        supply.setName(name);
        List<IotSupply> list=iotSupplyMapper.selectBySupply(supply);
        for(IotSupply iotSupply : list){
            iotSupply.setRankLiteral(SupplierRank.parse(iotSupply.getRank()).name());
        }
        PageInfo<IotSupply> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo<IotSupply> getAllSupply(){
        List<IotSupply> list=iotSupplyMapper.selectAllSupply();
        PageInfo<IotSupply> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    public int addSupplier(IotSupply iotSupply) {
       return  iotSupplyMapper.insertSelective(iotSupply);
    }

    public String checkSupplierName(String name) {
        IotSupply supply = new IotSupply();
        supply.setName(name);
        List<IotSupply> list=iotSupplyMapper.selectBySupply(supply);
        if(list!= null && !list.isEmpty()){
            return "T";
        }else{
            return "F";
        }
    }

    public IotSupply getSupplierById(Integer supplyId) {
        return iotSupplyMapper.selectByPrimaryKey(supplyId);
    }
    
    public IotSupply getSupplierByIccid(String iccid) {
        return iotSupplyMapper.selectByIccid(iccid);
    }

    public Integer updateById(IotSupply iotSupply) {
        return iotSupplyMapper.updateByPrimaryKeySelective(iotSupply);
    }

    public Integer delSupplier(Integer supplyId) {
        return iotSupplyMapper.deleteByPrimaryKey(supplyId);
    }

    public String checkEnName(String enName) {
        IotSupply supply = new IotSupply();
        supply.setEnName(enName);
        List<IotSupply> list=iotSupplyMapper.selectBySupply(supply);
        if(list!= null && !list.isEmpty()){
            return "T";
        }else{
            return "F";
        }
    }
}
