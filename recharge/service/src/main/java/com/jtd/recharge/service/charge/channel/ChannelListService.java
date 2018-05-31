package com.jtd.recharge.service.charge.channel;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.mapper.ChargeChannelMapper;
import com.jtd.recharge.dao.mapper.DictMapper;
import com.jtd.recharge.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by lyp on 2016/11/16.
 */
@Service
public class ChannelListService {
    @Resource
    private ChargeChannelMapper chargeChannelMapper;

    @Resource
    private DictMapper dictMapper;

    //查询供应商
    public List getSupplier(){
        List<ChargeSupply> list=chargeChannelMapper.getSupplier();
        return list;
    }
    //查询产品
    public List getProduct(ChargePosition chargePosition ){
        List<ChargePosition> list=chargeChannelMapper.getProduct(chargePosition);
        return list;
    }
    //添加渠道
    public int addChannels(ChargeChannel chargeChannel){
        int i=chargeChannelMapper.insertSelective(chargeChannel);
        return i;
    }

    /**
     * 渠道条件查询分页 lyp
     * @param chargeChannel
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageInfo<ChargeChannel> getChannelDetil(ChargeChannel chargeChannel,Integer pageNumber,Integer pageSize){
        PageHelper.startPage(pageNumber,pageSize,"id desc");
        List<ChargeChannel> list=chargeChannelMapper.selectChannelByCondition(chargeChannel);
        PageInfo<ChargeChannel> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 渠道开关维护 lyp
     * @param checkid
     * @param selecte
     * @return
     */
    public int editorChannelSwitch(int checkid,int selecte,String updateuser,String updatereason){
        ChargeChannel chargeChannel=new ChargeChannel();
        chargeChannel.setStatus(selecte);
        chargeChannel.setId(checkid);
        chargeChannel.setUpdateName(updateuser);
        chargeChannel.setUpdateReason(updatereason);
        return chargeChannelMapper.updateByPrimaryKeySelective(chargeChannel);
    }

    /**
     * 渠道列表删除 lyp
     * @param id
     * @return
     */

    public int deleteChannels(Integer id){

        return chargeChannelMapper.deleteByPrimaryKey(id);
    }

    /**
     * 渠道列表编辑
     * @param chargeChannel
     * @return
     */
    @Transactional
    public int updateChannel(ChargeChannel chargeChannel){

        return chargeChannelMapper.updateByPrimaryKeySelective(chargeChannel);
    }

    /**
     * 获取省份
     * @param dict
     * @return
     */
    public List<Dict> getProvince(Dict dict){
        List<Dict> list=dictMapper.selectAllDict(dict);
        return list;
    }



    /**
     * 防止渠道重复添加 lyp
     * @param chargeChannel
     * @return
     */
    public List<ChargeChannel> selectChannelInsert(ChargeChannel chargeChannel){
        List<ChargeChannel> list=chargeChannelMapper.selectChannelByCondition(chargeChannel);
        return list;
    }

    /**
     * 渠道一键修改
     * @param chargeChannel
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateAllChannel(ChargeChannel chargeChannel){
        return chargeChannelMapper.updateAllSelective(chargeChannel);
    }

    /**
     * 获取渠道对应的商品
     * @param chargeSupplyPosition
     * @return
     */
    public List<ChargeSupplyPosition> getSupplierProduct(ChargeSupplyPosition chargeSupplyPosition){

        return chargeChannelMapper.getSupplierProduct(chargeSupplyPosition);
    }


    /**
     * 渠道开关批量修改
     * @param chargeChannel
     * @return
     */
    public int updateAllSwitch(ChargeChannel chargeChannel){

        return chargeChannelMapper.updateAllSwitch(chargeChannel);
    }

    /**
     * 查看渠道相应的卡品
     * @param chargeSupplyPosition
     * @return
     */
    public PageInfo<ChargeSupplyPosition> selectChargeSupplyPosition(Integer pageNumber, Integer pageSize, ChargeSupplyPosition chargeSupplyPosition) {
        PageHelper.startPage(pageNumber,pageSize,"id desc");
        List<ChargeSupplyPosition> supplierProduct = chargeChannelMapper.getSupplierProduct(chargeSupplyPosition);
        PageInfo<ChargeSupplyPosition> pageInfo = new PageInfo<>(supplierProduct);
        return pageInfo;
    }
}
