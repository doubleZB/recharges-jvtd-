package com.jtd.recharge.service.charge.store;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.dao.bean.product.ProductF;
import com.jtd.recharge.dao.bean.util.ProductParam;
import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.mapper.*;
import com.jtd.recharge.dao.po.*;
import com.jtd.recharge.service.admin.OperateLogService;
import com.jtd.recharge.service.charge.position.ChargePostionService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by anna on 2016-11-14.
 */
@Service
@Transactional(readOnly = true)
public class StoreService {
    @Resource
    public ChargeProductStoreSupplyMapper storeSupplyMapper;
    @Resource
    public ChargeProductGroupMapper productGroupMapper;
    @Resource
    public DictMapper dictMapper;
    @Resource
    public ChargePostionService chargePostionService;
    @Resource
    public ChargeProductMapper chargeProductMapper;
    @Resource
    public ChargeSupplyMapper supplyMapper;
    @Resource
    public ChargeProductStoreMapper storeMapper;
    @Resource
    public ChargeProductStoreFMapper storeFMapper;
    @Resource
    public ChargeSupplyMapper csupplyMapper;
    @Resource
    public ChargePositionMapper positionMapper;
    @Resource
    public ChargeChannelMapper chargeChannelMapper;
    @Resource
    public ChargeSupplyPositionMapper chargeSupplyPositionMapper;
    @Resource
    public OperateLogService operateLogService;



    public Boolean isStoreGroupNameExist(String name){
        Boolean isExist = false;
        ChargeProductGroup group = productGroupMapper.selectByName(name);
        if(group!=null){
            isExist = true;
        }
        return isExist;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public Boolean saveStoreGroupNameExist(String name,AdminUser user){
        Boolean isExist = false;
        ChargeProductGroup group = new ChargeProductGroup();
        group.setName(name);
        group.setUpdatetime(new Date());
        int i = productGroupMapper.insert(group);
        if(i==1){
            isExist = true;
            operateLogService.logInfo(user.getName(),"货架组管理","新增货架组："+name);
        }
        return isExist;
    }
    public PageInfo<ChargeProductGroup> selectProductGroup(Integer pageNumber, Integer pageSize){
        PageHelper.startPage(pageNumber, pageSize, "id desc");
        List<ChargeProductGroup> groups = productGroupMapper.selectProductGroup();
        PageInfo<ChargeProductGroup> pageInfo = new PageInfo<>(groups);
        return pageInfo;
    }

    public List<ChargeProductGroup> selectAllProductGroup(){
        List<ChargeProductGroup> groups = productGroupMapper.selectProductGroup();
        return groups;
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ReturnMsg repareGroup(Integer groupId,String groupName,AdminUser user){
        ReturnMsg msg = new ReturnMsg();
        ChargeProductGroup group = new ChargeProductGroup();
        group.setId(groupId);
        group.setUpdatetime(new Date());
        group.setName(groupName);
        int num = productGroupMapper.updateByPrimaryKey(group);
        if(num==1){
            msg.setSuccess(true);
            msg.setMessage("操作成功！");
            operateLogService.logInfo(user.getName(),"货架组管理","修改货架组，id="+groupId+",新货架组名称为："+groupName);
        }else {
            msg.setSuccess(false);
            msg.setMessage("操作失败！");
        }
        return msg;
    }

    public List<Dict> getAllPrivence(){
        Dict dict = new Dict();
        dict.setModule("province");
        return dictMapper.selectAllDict(dict);
    }

    /**
     * 根据产品类型和运营商查档位表
     * @param businessType
     * @param yys
     * @return
     */
    public List<ChargePosition> getPositionByType(Integer businessType,Integer yys){
        ChargePosition chargePosition = new ChargePosition();
        chargePosition.setBusinessType(businessType);
        chargePosition.setOperator(yys);
        return  chargePostionService.getPosition(chargePosition);
    }
    public List<ChargeSupply> getChargeSupply(int type){
        ChargeSupply supply = new ChargeSupply();
        if(type!=0){
            supply.setBusinessType(type);
        }
        return  supplyMapper.selectByParam(supply);
    }


    /**
     * 根据条件查询产品
     * @param subdata
     * @return
     */
    public List<ChargeProduct> getChargeProduct(String  subdata){
        ProductParam productParam = new ProductParam();
        try {
            JSONObject jsonObject = new JSONObject(subdata);
            String splx = jsonObject.getString("splx");//商品类型
            productParam.setBusiness_type(splx);
            productParam.setOperator(jsonObject.getString("yys"));//运营商
            String[] positions = {};
            if ("1".equals(splx)){//流量
                productParam.setScope(jsonObject.getString("sxfw"));//流量的生效范围
                positions = jsonObject.get("yxll").toString().replace("[","").replace("]","").replace("\"","").split(",");//流量的档位编码
            }else if ("2".equals(splx)){
                positions = jsonObject.get("yxhf").toString().replace("[","").replace("]","").replace("\"","").split(",");//话费的档位编码
            }else{
                positions = jsonObject.get("yxcp").toString().replace("[","").replace("]","").replace("\"","").split(",");//视频会员档位编码
            }
            if (positions.length!=0){
                List<String> psx = new ArrayList<>();
                for (int i=0;i<positions.length;i++){
                    psx.add(positions[i]);
                }
                productParam.setPosition_code(psx);
            }else {
                return null;
            }
            String []sf = jsonObject.get("yxsf").toString().replace("[","").replace("]","").replace("\"","").split(",");//已选择的省份
            if (sf.length!=0){
                List<String> yxsf = new ArrayList<>();
                for (int i=0;i<sf.length;i++){
                    yxsf.add(sf[i]);
                }
                productParam.setProvince_id(yxsf);
            }else {
                return null;
            }
            return  chargeProductMapper.selectProductParam(productParam);
        }catch (Exception e){
            return null;
        }
    }
    /**
     * 将产品转换成页面显示类
     * @param products
     * @return
     */
    public List<ProductF> getProductF(List<ChargeProduct>  products){

        List<ProductF> productFs = null;
        if(products!=null&&products.size()!=0){
            productFs = new ArrayList<>();
            for (int i=0;i<products.size();i++){
                ChargeProduct product = products.get(i);
                ProductF productF = new ProductF();
                productF.setId(product.getId());
                productF.setOperator(SysConstants.operatorMap.get(product.getOperator()));
                productF.setBusinessType(SysConstants.businessTypeMap.get(product.getBusinessType()));
                productF.setEffectTime(SysConstants.effect_timeMap.get(product.getEffectTime()));
                ChargePosition position = new ChargePosition();
                position.setCode(product.getPositionCode());
                List<ChargePosition> positions = positionMapper.selectByPrimary(position);
                productF.setFlowPackageSize(positions.get(0).getPackageSize());
                productF.setLimitNum(product.getLimitNum());
                Dict dict = new Dict();
                dict.setKey(product.getProvinceId());
                List<Dict> dicts = dictMapper.selectAllDict(dict);
                productF.setProvince(dicts.get(0).getValue());
                productF.setPositionCode(product.getPositionCode());
                productF.setUpdateTime(product.getUpdateTime());
                productF.setScope(SysConstants.scopeMap.get(product.getScope()));
                productF.setValidityTime(SysConstants.validity_timeMap.get(product.getValidityTime()));
                productF.setAmount(product.getAmount());
                productFs.add(productF);
            }
        }
        return productFs;
    }


    /**
     * 添加货架
     * @param subdata
     * @param user
     * @return
     * @throws Exception
     *
     * 2017年4月1日 版本改动  王相平
     * 1、对选择供应商的货架如果存在该批次货架就不添加---->货架存在则不添加，不存在则添加
     * 2、对选择供应商的货架对应的供应商如果不存在渠道则不添加---->如果不存在渠道依旧添加，上下架状态改为下架
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ReturnMsg addStore(String subdata,AdminUser user) throws Exception{
        ReturnMsg msg = new ReturnMsg();
        msg.setMessage("操作失败！");
        msg.setSuccess(false);
        String mesg="操作失败！";
        JSONObject jsonObject = new JSONObject(subdata);
        Integer xhjz = jsonObject.getInt("xhjz");//货架组id
        String[] spids = jsonObject.get("mytable").toString().replace("[","").replace("]","").replace("\"","").split(",");//商品ids
        BigDecimal zk = jsonObject.getBigDecimal("zk");//折扣
        int struts = jsonObject.getInt("xzzt");//状态
        int xzqd = jsonObject.getInt("xzqd");//选择渠道
        List<ChargeProductStore> productStores = new ArrayList<>();
        int huojiaType =1;
        ChargeProduct chargeProduct = chargeProductMapper.selectByPrimaryKey(Integer.parseInt(spids[0]));
        huojiaType = chargeProduct.getBusinessType();
        //存在性判断
        if(xzqd==2){//系统自动匹配
            for (int i =0;i<spids.length;i++){
                ChargeProductStore store =new ChargeProductStore();
                store.setGroupId(xhjz);
                store.setProductId(Integer.parseInt(spids[i]));
                List<ChargeProductStore> stores = storeMapper.selectStoreSelective(store);
                if(stores!=null&&!stores.isEmpty()){
                    //说明已经存在该货架，不再添加   2017年4月1日11:16:50注掉，改为有就不添加该货架，添加没有的
//                        mesg = "1";
//                        throw new RuntimeException(mesg);
                }else {//不存在放到待保存列表
                    store.setDiscountPrice(zk);
                    store.setStatus(struts);
                    store.setUpdatetime(new Date());
                    store.setSendType(2);
                    productStores.add(store);
                }
            }
        }else if(xzqd==1){//指定供应商
            for (int i =0;i<spids.length;i++){
                ChargeProductStore store =new ChargeProductStore();
                store.setGroupId(xhjz);
                store.setProductId(Integer.parseInt(spids[i]));
                List<ChargeProductStore> stores = storeMapper.selectStoreSelective(store);
                if(stores!=null&&!stores.isEmpty()){
                    //说明已经存在该货架，不再添加    2017年4月1日11:16:50注掉，改为有就不添加该货架，添加没有的
//                        mesg = "1";
//                        throw new RuntimeException(mesg);
                }else {//不存在放到待保存列表
                    store.setDiscountPrice(zk);
                    store.setStatus(struts);
                    store.setUpdatetime(new Date());
                    store.setSendType(1);
                    productStores.add(store);
                }

            }
        }

        /**
         * 对可以保存的货架进行再处理
         */
        if(xzqd==2){//系统自动匹配
            //保存
            for (int i=0;i<productStores.size();i++){
                ChargeProductStore store = productStores.get(i);
                storeMapper.insert(store);
                operateLogService.logInfo(user.getName(),"货架管理","新增系统匹配货架：id="+store.getId()+" 货架组id="+store.getGroupId()+"" +
                        " 产品id="+store.getProductId()+" 折扣="+store.getDiscountPrice());
            }
            mesg="添加成功"+productStores.size()+"个货架！";
        }else if(xzqd==1){//指定供应商
            int count = 0;
            //xzgys = 选择供应商
            String[] xzgys =jsonObject.get("xzgys").toString().replace("[","").replace("]","").replace("\"","").split(",");//供应商ids
            for (int i=0;i<productStores.size();i++){
                ChargeProductStore store = productStores.get(i);
                Integer productId = store.getProductId();
                ChargeProduct product = chargeProductMapper.selectByPrimaryKey(productId);
                String positionCode = product.getPositionCode();
                ChargeChannel channel = new ChargeChannel();
                channel.setPositionCode(positionCode);
                channel.setProvinceId(product.getProvinceId());
//                List<Integer> suppliersids = new ArrayList<>();//2017年4月1日11:31:16注掉
                List<Integer> suppliersidsHasChannel = new ArrayList<>();
                List<Integer> suppliersidsNoChannel = new ArrayList<>();
                for (int j=0;j<xzgys.length;j++){
                    channel.setSupplyId(Integer.parseInt(xzgys[j]));
                    List<ChargeChannel> chargeChannels = chargeChannelMapper.selectChannelByCondition(channel);
                    //判断是不是存在渠道
                    if(chargeChannels!=null && chargeChannels.size()==1){
                       /* //存在渠道，判断供应商是否有该卡品  2017年4月1日11:32:12 注掉
                        ChargeSupplyPosition supplyPosition = chargeSupplyPositionMapper.
                                selectSupplyPostionByParam(huojiaType,Integer.parseInt(xzgys[j]),positionCode);
                        if(supplyPosition!=null){
                            //既存在渠道又存在存在卡品 ，记录供应商id，待保存
                            suppliersids.add(Integer.parseInt(xzgys[j]));
                        }*/
                        suppliersidsHasChannel.add(Integer.parseInt(xzgys[j]));
                    }else {
                        suppliersidsNoChannel.add(Integer.parseInt(xzgys[j]));
                    }
                }
                //处理存在匹配的渠道和供应商卡品
                if(!suppliersidsHasChannel.isEmpty()){//存在匹配的渠道和供应商卡品
                    storeMapper.insert(store);//保存到货架表
                    ChargeProductStoreSupply productStoreSupply = new ChargeProductStoreSupply();
                    productStoreSupply.setStoreId(store.getId());
                    //保存货架和供应商映射关系
                    for (int j=0;j<suppliersidsHasChannel.size();j++){
                        productStoreSupply.setSupplyId(suppliersidsHasChannel.get(j));
                        productStoreSupply.setBusinessType(huojiaType);
                        productStoreSupply.setUpdateTime(new Date());
                        storeSupplyMapper.insert(productStoreSupply);
                        operateLogService.logInfo(user.getName(),"货架管理","新增指定渠道货架：id="+store.getId()+" 货架组id="+store.getGroupId()+"" +
                                " 产品id="+store.getProductId()+" 折扣="+store.getDiscountPrice()+" 指定供应商id="+suppliersidsHasChannel.get(j));
                    }
                    count++;//成功添加的货架个数加一
                }
                //处理不存在匹配的渠道和供应商卡品
                if(!suppliersidsNoChannel.isEmpty()){//不存在匹配的渠道和供应商卡品
                    store.setStatus(2);//下架该货架
                    storeMapper.insert(store);//保存到货架表
                    ChargeProductStoreSupply productStoreSupply = new ChargeProductStoreSupply();
                    productStoreSupply.setStoreId(store.getId());
                    //保存货架和供应商映射关系
                    for (int j=0;j<suppliersidsNoChannel.size();j++){
                        productStoreSupply.setBusinessType(huojiaType);
                        productStoreSupply.setSupplyId(suppliersidsNoChannel.get(j));
                        productStoreSupply.setUpdateTime(new Date());
                        storeSupplyMapper.insert(productStoreSupply);
                        operateLogService.logInfo(user.getName(),"货架管理","新增指定渠道货架：id="+store.getId()+" 货架组id="+store.getGroupId()+"" +
                                " 产品id="+store.getProductId()+" 折扣="+store.getDiscountPrice()+" 指定供应商id="+suppliersidsNoChannel.get(j));
                    }
                    count++;//成功添加的货架个数加一
                }

            }

            mesg="添加成功"+count+"个货架！";
        }

        msg.setMessage(mesg);
        msg.setSuccess(true);
        return msg;
    }

    public PageInfo<ChargeProductStoreF> selectProductStore(String subData, Integer pageNumber, Integer pageSize,
                                                            Integer zk_type,
                                                            BigDecimal zk_num,
                                                            Integer supplierID){
        try {
            ChargeProductStoreF store = getStore(subData,zk_type,zk_num,supplierID);
            PageHelper.startPage(pageNumber, pageSize, "s.id desc");
            List<ChargeProductStoreF> storeFs = storeFMapper.selectProductStore(store);
            PageInfo<ChargeProductStoreF> pageInfo = new PageInfo<>(storeFs);
            return pageInfo;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void edit_one_key(String subData,
                                    Integer zk_type,
                                    BigDecimal zk_num,
                                    Integer supplierID,
                                    String status,
                                    BigDecimal zk_to_update,
                                    AdminUser user
                                  ){
        ChargeProductStoreF store = getStore(subData,zk_type,zk_num,supplierID);
        int i = 0;
        if(status!=null && !"".equals(status)){
            store.setStatus_to_update(status);
            i = 1;
        }
        if(zk_to_update!=null && !"".equals(zk_to_update)){
            store.setZk_to_update(zk_to_update);
        }
        storeFMapper.edit_one_key(store);
        String msg = "一键修改货架折扣成功";
        if(i==1){
            msg = "一键修改货架上下架状态成功";
        }
        operateLogService.logInfo(user.getName(),"货架管理",msg);
    }

    public ChargeProductStoreF getStore(String subData,
                                    Integer zk_type,
                                    BigDecimal zk_num,
                                    Integer supplierID){

            JSONObject jsonObject = new JSONObject(subData);
            String yys = jsonObject.getString("yys");//运营商
            String lx = jsonObject.getString("lx");//商品类型
            String privanceIds = jsonObject.get("sf").toString();
            privanceIds = privanceIds.replace("[","").replace("]","").trim();
            List<Integer> privanceIdList = null;
            String ids[] = null;
            if(!"".equals(privanceIds)){
                ids = privanceIds.split(",");
                privanceIdList = new ArrayList<>();
                for (int i = 0; i <ids.length ; i++) {
                    privanceIdList.add(Integer.parseInt(ids[i].substring(1,ids[i].length()-1)));
                }
            }

            String mianzhiIds = jsonObject.get("mz").toString();
            mianzhiIds = mianzhiIds.replace("[","").replace("]","").trim();
            List<Integer> mianzhiList = new ArrayList<>();
            if(!"".equals(mianzhiIds)){
                ids = mianzhiIds.split(",");
                for (int i = 0; i <ids.length ; i++) {
                    mianzhiList.add(Integer.parseInt(ids[i].substring(1,ids[i].length()-1)));
                }
            }


            int yz = jsonObject.getInt("yz");//匹配原则
            int zt = jsonObject.getInt("zt");//状态
            String hjmc = jsonObject.getString("hjmc");//货架组名称
            String qdmc = jsonObject.getString("qdmc");//渠道名称
            String hjid = jsonObject.getString("hjid");//货架id

            ChargeProductStoreF store = new ChargeProductStoreF();
            if(hjid!=null && !"".equals(hjid)){
                store.setId(Integer.parseInt(hjid));
            }
            if(!"0".equals(lx)){
                store.setProductTyoe(lx);
            }
            if(!"0".equals(yys)){
                store.setYys(yys);
            }
            if(privanceIdList!=null && !privanceIdList.isEmpty()){
                store.setPrivanceIds(privanceIdList);
            }
            if(mianzhiList!=null && !mianzhiList.isEmpty()){
                store.setPositionCodes(mianzhiList);
            }
            if(!"".equals(hjmc)){
                store.setGroupName(hjmc);
            }
            if(yz!=0){
                store.setSendType(yz+"");
            }
            if(zt!=0){
                store.setStatus(zt+"");
            }

            if (yz==1 && supplierID != 0) {
                store.setSupplierID(supplierID);
            }
            store.setZk_type(zk_type);
            store.setZk_num(zk_num);
            return store;
    }

    public void addInfoToChargeProductStoreF(List<ChargeProductStoreF> storeFs){
        if(storeFs!=null&&!storeFs.isEmpty()){
            for(int i=0;i<storeFs.size();i++){
                ChargeProductStoreF storeF = storeFs.get(i);
                storeF.setStatus(SysConstants.storeStstusMap.get(Integer.parseInt(storeF.getStatus())));
                storeF.setProductTyoe(SysConstants.businessTypeMap.get(Integer.parseInt(storeF.getProductTyoe())));
                storeF.setYys(SysConstants.operatorMap.get(Integer.parseInt(storeF.getYys())));
                if(storeF.getActiveScope()!=null&&!"".equals(storeF.getActiveScope())){
                    storeF.setActiveScope(SysConstants.scopeMap.get(Integer.parseInt(storeF.getActiveScope())));
                }
                storeF.setSendType(SysConstants.send_typeMap.get(Integer.parseInt(storeF.getSendType())));
            }
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void editStatus(String subData,int status,AdminUser user){
        String ids[]=subData.split(",");
        for(int i = 0;i<ids.length;i++){
            ChargeProductStore store = storeMapper.selectByPrimaryKey(Integer.parseInt(ids[i]));
            if(store!=null&&store.getStatus()!=3){
                store = new ChargeProductStore();
                store.setId(Integer.parseInt(ids[i]));
                store.setStatus(status);
                storeMapper.updateByPrimaryKeySelective(store);
                if(status==1){
                    operateLogService.logInfo(user.getName(),"货架管理","上架货架，id="+store.getId());
                }else if(status==2){
                    operateLogService.logInfo(user.getName(),"货架管理","下架货架，id="+store.getId());
                }else if(status==3){
                    operateLogService.logInfo(user.getName(),"货架管理","永久架货架，id="+store.getId());
                }
            }

        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void editZk(String subData,BigDecimal discount,AdminUser user){
        String ids[]=subData.split(",");
        for(int i = 0;i<ids.length;i++){
            ChargeProductStore store = new ChargeProductStore();
            store.setDiscountPrice(discount);
            store.setId(Integer.parseInt(ids[i]));
            storeMapper.updateByPrimaryKeySelective(store);
            operateLogService.logInfo(user.getName(),"货架管理","修改货架折扣为"+discount+"，id="+store.getId());
        }
    }

    /**
     * 修改货架发送渠道【系统匹配|指定渠道】
     * @param subData
     * @param storeIds
     * @param user
     * @return
     *
     * 2017年4月1日11:55:02  版本修改   王相平
     * 1、
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ReturnMsg editSendType(String subData,String storeIds,AdminUser user){

        ReturnMsg msg = new ReturnMsg();
        msg.setMessage("操作失败！");

        String ids[]=storeIds.split(",");
        JSONObject jsonObject = new JSONObject(subData);

        int type = jsonObject.getInt("plradio");
        if(type==0){//系统匹配
            //删除匹配表数据
            for (int i=0;i<ids.length;i++){
                ChargeProductStoreSupply storeSupply = new ChargeProductStoreSupply();
                storeSupply.setStoreId(Integer.parseInt(ids[i]));
                List<ChargeProductStoreSupply> storeSupplies = storeSupplyMapper.selectProductStoreSupplyByCondition(storeSupply);
                if(storeSupplies!=null&&!storeSupplies.isEmpty()){
                    for (int j=0;j<storeSupplies.size();j++){
                        storeSupplyMapper.deleteByPrimaryKey(storeSupplies.get(j).getId());
                    }
                }
            }
            ChargeProductStore productStore = new ChargeProductStore();
            for (int i=0;i<ids.length;i++){
                productStore.setId(Integer.parseInt(ids[i]));
                productStore.setSendType(2);
                storeMapper.updateByPrimaryKeySelective(productStore);
                operateLogService.logInfo(user.getName(),"货架管理","修改货架为系统匹配，id="+ids[i]);
            }
            msg.setMessage("修改成功"+ids.length+"个货架！");
            msg.setSuccess(true);
        }else {//指定渠道
            int count =0 ;
            String supplids[] = jsonObject.get("plcheck").toString().replace("[","").replace("]","").replace("\"","").split(",");
            ChargeProductStore productStore = null;
            for (int i=0;i<ids.length;i++){
                //更新货架组状态
                ChargeProductStoreF f = new ChargeProductStoreF();
                f.setId(Integer.parseInt(ids[i]));
                List<ChargeProductStoreF> productStoreF = storeFMapper.selectProductStore(f);

                if(productStoreF!=null && !productStoreF.isEmpty()){
                    //判断所选的供应商类型和货架类型是否匹配
                    for (int j=0;j<supplids.length;j++){
                        ChargeSupply supply = supplyMapper.selectByPrimaryKey(Integer.parseInt(supplids[j]));
                        String bntype = supply.getBusinessType()+"";
                        String pdtype = productStoreF.get(0).getProductTyoe();
                        if(!bntype.equals(pdtype)){
                            throw new RuntimeException("1");
                        }
                    }

                    //查询出该货架信息
                    ChargeProductStore store = storeMapper.selectByPrimaryKey(Integer.parseInt(ids[i]));
                    //获取产品id
                    Integer productId = store.getProductId();
                    //获取产品信息
                    ChargeProduct product = chargeProductMapper.selectByPrimaryKey(productId);
                    //获取档位编码
                    String positionCode = product.getPositionCode();
                    ChargeChannel channel = new ChargeChannel();
                    channel.setPositionCode(positionCode);
                    channel.setProvinceId(product.getProvinceId());
//                    List<Integer> suppliersids = new ArrayList<>();//2017年4月1日13:29:41 注掉
                    List<Integer> suppliersidsHasChannel = new ArrayList<>();//2017年4月1日13:29:41 新增
                    List<Integer> suppliersidsNoChannel = new ArrayList<>();//2017年4月1日13:29:41 新增
                    for (int j=0;j<supplids.length;j++){
                        channel.setSupplyId(Integer.parseInt(supplids[j]));
                        //获取当前条件下渠道
                        List<ChargeChannel> chargeChannels = chargeChannelMapper.selectChannelByCondition(channel);
                        //判断是不是存在渠道
                        if(chargeChannels==null || chargeChannels.size()==0){
                            suppliersidsNoChannel.add(Integer.parseInt(supplids[j]));
                           /* //存在渠道，判断供应商是否有该卡品   2017年4月1日13:32:17 注掉
                            ChargeSupplyPosition supplyPosition = chargeSupplyPositionMapper.
                                    selectSupplyPostionByParam(product.getBusinessType(),Integer.parseInt(supplids[j]),positionCode);
                            if(supplyPosition!=null){
                                //既存在渠道又存在存在卡品 ，记录供应商id，待保存
                                suppliersids.add(Integer.parseInt(supplids[j]));
                            }*/

                        }else {
                            suppliersidsHasChannel.add(Integer.parseInt(supplids[j]));

                        }
                    }

                    //货架存在渠道
                    if(!suppliersidsHasChannel.isEmpty()){
                        //删除货架渠道匹配表数据
                        ChargeProductStoreSupply storeSupply = new ChargeProductStoreSupply();
                        storeSupply.setStoreId(Integer.parseInt(ids[i]));
                        List<ChargeProductStoreSupply> storeSupplies = storeSupplyMapper.selectProductStoreSupplyByCondition(storeSupply);
                        if(storeSupplies!=null&&!storeSupplies.isEmpty()){
                            for (int j=0;j<storeSupplies.size();j++){
                                storeSupplyMapper.deleteByPrimaryKey(storeSupplies.get(j).getId());
                            }
                        }

                        productStore= new ChargeProductStore();
                        productStore.setId(Integer.parseInt(ids[i]));
                        if(productStoreF.get(0).getStatus().equals("2")){
                            productStore.setStatus(1);//存在渠道的，自动上架
                        }
                        productStore.setSendType(1);
                        storeMapper.updateByPrimaryKeySelective(productStore);
                        //向匹配表插入数据
                        ChargeProductStoreSupply productStoreSupply = new ChargeProductStoreSupply();
                        productStoreSupply.setStoreId(Integer.parseInt(ids[i]));
                        StringBuffer stringBuffer = new StringBuffer();
                        for (int j=0;j<suppliersidsHasChannel.size();j++){
                            productStoreSupply.setSupplyId(suppliersidsHasChannel.get(j));
                            productStoreSupply.setBusinessType(product.getBusinessType());
                            productStoreSupply.setUpdateTime(new Date());
                            storeSupplyMapper.insert(productStoreSupply);
                            stringBuffer.append(suppliersidsHasChannel.get(j)+",");
                        }
                        operateLogService.logInfo(user.getName(),"货架管理","修改货架为指定渠道，id="+ids[i]+" 指定供应商id是"+stringBuffer.toString());
                        count++;
                    }
                    //不存在货架存在渠道
                    if(!suppliersidsNoChannel.isEmpty()){
                        //删除货架渠道匹配表数据
                        ChargeProductStoreSupply storeSupply = new ChargeProductStoreSupply();
                        storeSupply.setStoreId(Integer.parseInt(ids[i]));
                        List<ChargeProductStoreSupply> storeSupplies = storeSupplyMapper.selectProductStoreSupplyByCondition(storeSupply);
                        if(storeSupplies!=null&&!storeSupplies.isEmpty()){
                            for (int j=0;j<storeSupplies.size();j++){
                                storeSupplyMapper.deleteByPrimaryKey(storeSupplies.get(j).getId());
                            }
                        }

                        productStore= new ChargeProductStore();
                        productStore.setId(Integer.parseInt(ids[i]));
                        productStore.setSendType(1);
                        productStore.setStatus(2);//不存在渠道的，自动下架
                        storeMapper.updateByPrimaryKeySelective(productStore);
                        //向匹配表插入数据
                        ChargeProductStoreSupply productStoreSupply = new ChargeProductStoreSupply();
                        productStoreSupply.setStoreId(Integer.parseInt(ids[i]));
                        StringBuffer stringBuffer = new StringBuffer();
                        for (int j=0;j<suppliersidsNoChannel.size();j++){
                            productStoreSupply.setSupplyId(suppliersidsNoChannel.get(j));
                            productStoreSupply.setUpdateTime(new Date());
                            productStoreSupply.setBusinessType(product.getBusinessType());
                            storeSupplyMapper.insert(productStoreSupply);
                            stringBuffer.append(suppliersidsNoChannel.get(j)+",");
                        }
                        operateLogService.logInfo(user.getName(),"货架管理","修改货架为指定渠道，id="+ids[i]+" 指定供应商id是"+stringBuffer.toString());
                        count++;
                    }
                }

            }
            msg.setMessage("修改成功"+count+"个货架！");
            msg.setSuccess(true);
        }

        return msg;
    }

    public List<ChargeSupply> watchSupply(int storeId){
        List<ChargeSupply> supplies = null;
        ChargeProductStoreSupply productStoreSupply = new ChargeProductStoreSupply();
        productStoreSupply.setStoreId(storeId);
        List<ChargeProductStoreSupply> productStoreSupplies = storeSupplyMapper.selectProductStoreSupplyByCondition(productStoreSupply);

        if(productStoreSupplies!=null && !productStoreSupplies.isEmpty()){
            supplies = new ArrayList<>();
            for (int i=0;i<productStoreSupplies.size();i++){
                ChargeSupply supply = csupplyMapper.selectByPrimaryKey(productStoreSupplies.get(i).getSupplyId());
                supplies.add(supply);
            }
        }
        return supplies;
    }


    public List<ChargePosition> getPositionCard(int storeType,int operator){
        ChargePosition position = new ChargePosition();
        position.setBusinessType(storeType);
        position.setOperator(operator);
        return positionMapper.selectByPrimary(position);
    }


    public List<Map<String,String>> getCard(List<ChargePosition> chargePositions){
        List<Map<String,String>> cards =null;
        if(chargePositions!=null && !chargePositions.isEmpty()){
            cards = new ArrayList<>();
            for (int i = 0; i <chargePositions.size() ; i++) {
                Map<String,String> card = new HashMap<>();
                ChargePosition position = chargePositions.get(i);
                card.put("name",getOpertor(position.getOperator())+position.getPackageSize()+getType(position.getBusinessType()));
                card.put("code",position.getCode());
                card.put("amount",String.valueOf(chargePositions.get(i).getAmount()));
                cards.add(card);
            }
        }
        return cards;
    }

    public String getOpertor(int key){
        String value = "移动";
        if(key==1){
            value = "移动";
        }else if(key==2){
            value = "联通";
        }else if(key==3){
            value = "电信";
        }
        return value;
    }

    public String getType(int key){
        if(key==2){
            return "话费";
        }
        return "流量";
    }

    public List<Map<String,String>> getSuppliersByName(String name){
        ChargeSupply supply = new ChargeSupply();
        if(name!=null && !"".equals(name)){
            name = "%"+name+"%";
            supply.setName(name);
        }
        List<ChargeSupply> sp = supplyMapper.getSuppleir(supply);
        List<Map<String,String>> suppliers = null;
        if(sp!=null && !sp.isEmpty()){
            suppliers = new ArrayList<>();
            for (int i = 0; i <sp.size() ; i++) {
                Map<String ,String> map = new HashMap();
                ChargeSupply spl = sp.get(i);
                map.put("supplyName",spl.getName());
                map.put("supplyId",spl.getId()+"");
                suppliers.add(map);
            }
        }
        return suppliers;
    }

    /**
     * 查询货架组
     * @param chargeProductGroup
     * @return
     */
    public List<ChargeProductGroup> selectProductGroupList(ChargeProductGroup chargeProductGroup){
    return productGroupMapper.selectProductGroupList(chargeProductGroup);
    }

    /**
     * 货架组条件查询
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageInfo<ChargeProductGroup> selectProductGroupCondition(Integer pageNumber, Integer pageSize,ChargeProductGroup chargeProductGroup){
        PageHelper.startPage(pageNumber, pageSize, "id desc");
        List<ChargeProductGroup> groups = productGroupMapper.selectProductGroupCondition(chargeProductGroup);
        PageInfo<ChargeProductGroup> pageInfo = new PageInfo<>(groups);
        return pageInfo;
    }
}
