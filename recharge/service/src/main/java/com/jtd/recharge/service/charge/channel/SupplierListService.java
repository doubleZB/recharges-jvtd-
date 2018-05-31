package com.jtd.recharge.service.charge.channel;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.mapper.ChargeSupplyMapper;
import com.jtd.recharge.dao.po.ChargeSupply;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lyp
 * on 2016/11/17.
 */
@Service
public class SupplierListService {
    @Resource
    private ChargeSupplyMapper chargeSupplyMapper;


    //添加供应商
    public int addSupplier(ChargeSupply chargeSupply){
        int i=chargeSupplyMapper.insertSelective(chargeSupply);
        return i;
    }


    /**
     * 分页查询 lyp
     * @param name
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageInfo<ChargeSupply> getSupplierPage( String name,String type,Integer pageNumber,Integer pageSize){
        String names=null;
        if(name==null||"".equals("name")){
            names=name;
        }else {
            names = "%" + name + "%";
        }
        int busstype=Integer.parseInt(type);
        ChargeSupply chargeSupply=new ChargeSupply();
        chargeSupply.setName(names);
        if(busstype>0){
            chargeSupply.setBusinessType(busstype);
        }


        PageHelper.startPage(pageNumber,pageSize,"id desc");
        List<ChargeSupply> list=chargeSupplyMapper.getSuppleir(chargeSupply);
        PageInfo<ChargeSupply> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 供应商修改 lyp
     * @param chargeSupply
     * @return
     */
    public int editorSupplier(ChargeSupply chargeSupply){
        int i=chargeSupplyMapper.updateByPrimaryKeySelective(chargeSupply);
        return i;
    }

    /**
     * 查询供应商
     * @param chargeSupply
     * @return
     */
    public List<ChargeSupply> getSupplier(ChargeSupply chargeSupply ){
        return chargeSupplyMapper.getSuppleir(chargeSupply);
    }

}
