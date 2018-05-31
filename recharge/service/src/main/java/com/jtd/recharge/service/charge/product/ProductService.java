package com.jtd.recharge.service.charge.product;

import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.dao.bean.util.ProductParam;
import com.jtd.recharge.dao.mapper.ChargePositionMapper;
import com.jtd.recharge.dao.mapper.ChargeProductMapper;
import com.jtd.recharge.dao.mapper.DictMapper;
import com.jtd.recharge.dao.po.ChargePosition;
import com.jtd.recharge.dao.po.ChargeProduct;
import com.jtd.recharge.dao.po.Dict;
import com.jtd.recharge.service.charge.position.ChargePostionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by WXP on 2016-11-16 18:16:51.
 */
@Service
public class ProductService {

    @Resource
    public DictMapper dictMapper;
    @Resource
    public ChargePositionMapper positionMapper;
    @Resource
    public ChargePostionService chargePostionService;

    @Resource
    public ChargeProductMapper chargeProductMapper;

    /**
     * 返回省份字典
     * @return
     */
    public List<Dict> getAllPrivence(){
        Dict dict = new Dict();
        dict.setModule("province");
        return dictMapper.selectAllDict(dict);
    }


    /**
     * 获取流量档位清单
     * @return
     */
    public List<ChargePosition> getFlowPosition(){
        ChargePosition position = new ChargePosition();
        position.setBusinessType(1);
        return positionMapper.selectByPrimary(position);
    }

    /**
     * 获取话费档位清单
     * @return
     */
    public List<ChargePosition> getPhoneCostPosition(){
        ChargePosition position = new ChargePosition();
        position.setBusinessType(2);
        return positionMapper.selectByPrimary(position);
    }

    /**
     * 初始化商品数据
     * @return
     */

    public String initProductData(){
        String msg = "失败！";
        int count = 0;
        try{

            Dict dict = new Dict();
            dict.setModule("province");
            int[] yys = {1,2,3};//运营商
            List<Dict> dicts = dictMapper.selectAllDict(dict);//省份
            int[] scope = {1,2};//生效范围
            List<ChargePosition> YDll = getPositionByType(1,1);//移动流量
            List<ChargePosition> LTll = getPositionByType(1,2);//联通流量
            List<ChargePosition> DXll = getPositionByType(1,3);//电信流量

            List<ChargePosition> YDhf = getPositionByType(2,1);//移动话费
            List<ChargePosition> LThf = getPositionByType(2,2);//联通话费
            List<ChargePosition> DXhf = getPositionByType(2,3);//电信话费


            //初始化流量数据
            for (int i = 0; i < yys.length; i++) {
                ChargeProduct p = new ChargeProduct();
                p.setBusinessType(1);
                p.setUpdateTime(new Date());
                p.setOperator(yys[i]);//运营商
                for (int j = 0; j <dicts.size(); j++) {
                    p.setProvinceId(dicts.get(j).getKey());//省份
                    for (int k = 0; k <scope.length ; k++) {
                        p.setScope(scope[k]);//生效范围
                        if(yys[i]==1){
                            for (int o = 0; o <YDll.size() ; o++) {
                                p.setPositionCode(YDll.get(o).getCode());//档位编码
                                chargeProductMapper.insert(p);
                                count++;
                                System.out.println(count);
                            }
                        }else if(yys[i]==2){
                            for (int o = 0; o <LTll.size() ; o++) {
                                p.setPositionCode(LTll.get(o).getCode());//档位编码
                                chargeProductMapper.insert(p);
                                count++;
                                System.out.println(count);
                            }
                        }else if(yys[i]==3){
                            for (int o = 0; o <DXll.size() ; o++) {
                                p.setPositionCode(DXll.get(o).getCode());//档位编码
                                chargeProductMapper.insert(p);
                                count++;
                                System.out.println(count);
                            }
                        }
                    }
                }


            }


            //初始化话费数据
            for (int j = 0; j <dicts.size() ; j++) {
                ChargeProduct p = new ChargeProduct();
                p.setBusinessType(2);
                p.setProvinceId(dicts.get(j).getKey());
                p.setUpdateTime(new Date());
                for (int i = 0; i <yys.length ; i++) {
                        p.setOperator(yys[i]);
                        if(1==yys[i]){
                            for (int l = 0; l <YDhf.size() ; l++) {
                                p.setPositionCode(YDhf.get(l).getCode());
                                chargeProductMapper.insert(p);
                                count++;
                                System.out.println(count);
                            }
                        }else if(2==yys[i]){
                            for (int l = 0; l <LThf.size() ; l++) {
                                p.setPositionCode(LThf.get(l).getCode());
                                chargeProductMapper.insert(p);
                                count++;
                                System.out.println(count);
                            }
                        }else if(yys[i]==3){
                            for (int l = 0; l <DXhf.size() ; l++) {
                                p.setPositionCode(DXhf.get(l).getCode());
                                chargeProductMapper.insert(p);
                                count++;
                                System.out.println(count);
                            }
                    }
                }
            }

            msg="成功！";

        }catch (Exception e){
            msg=e.getLocalizedMessage();
        }

        return msg;
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

}
