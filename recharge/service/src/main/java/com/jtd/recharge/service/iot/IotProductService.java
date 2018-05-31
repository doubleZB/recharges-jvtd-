package com.jtd.recharge.service.iot;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.mapper.IotProductMapper;
import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardPeriod;
import com.jtd.recharge.define.CardType;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */
@Service
public class IotProductService {

    @Resource
    private IotProductMapper iotProductMapper;

    public PageInfo<IotProduct> getProductList(Integer operator, Integer period, Integer type, Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber,pageSize,"id desc");
        IotProduct iotProduct = new IotProduct();
        if(operator != null){
            iotProduct.setOperator(operator);
        }
        if(period != null){
            iotProduct.setPeriod(period);
        }
        if(type != null){
            iotProduct.setType(type);
        }
        List<IotProduct> list =  iotProductMapper.selectByIotProduct(iotProduct);
        for (IotProduct item : list) {
			item.setPeriodLiteral(CardPeriod.parse(item.getPeriod()).name());
			item.setTypeLiteral(CardType.parse(item.getType()).name());
			item.setOperatorLiteral(CardOperator.parse(item.getOperator()).name());
		}
        PageInfo<IotProduct> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
    
    public PageInfo<IotProduct> getAllProduct() {
        List<IotProduct> list =  iotProductMapper.selectAllIotProduct();
        PageInfo<IotProduct> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    public Integer addProduct(IotProduct iotProduct) {
       return   iotProductMapper.insertSelective(iotProduct);
    }

    public IotProduct getProductById(Integer id) {
       return  iotProductMapper.selectByPrimaryKey(id);
    }

    public Integer updateById(IotProduct iotProduct) {
        return iotProductMapper.updateByPrimaryKey(iotProduct);
    }

    public Integer delProduct(Integer productId) {
       return  iotProductMapper.deleteByPrimaryKey(productId);
    }

    public List<IotProduct> listAllProduct() {
       return iotProductMapper.selectAllIotProduct();
    }
}
