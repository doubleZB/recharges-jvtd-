package com.jtd.recharge.action.iot;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.define.SupplierRank;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.po.IotSupply;
import com.jtd.recharge.service.iot.SuppplierService;


/**
 * Created by Administrator on 2018/3/20.
 * 供应商管理的控制器
 */
@Controller
@RequestMapping("/supplier")
public class SupplierController {

    @Resource
    private SuppplierService suppplierService;

    /**
     * 跳转到供应商列表页面的方法
     * @return
     */
    @RequestMapping("/getSupplier")
    public String getSupplier(HttpServletRequest request){
        request.setAttribute("rankList",SupplierRank.values());
        return "iot/supplier/supplierList";
    }

    /**
     * 获取供应商的数据
     * @param name  供应商名称
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("/getSupplierList")
    @ResponseBody
    public PageInfo<IotSupply> getSupplierLIst(String name, Integer pageNumber, Integer pageSize){
        PageInfo<IotSupply> supplierList = suppplierService.getSupplierList(name, pageNumber, pageSize);
        return supplierList;
    }
    /**
     * 新增供应商
     */
    @RequestMapping("/addOrUpdateSupply")
    @ResponseBody
    public ReturnMsg addSupplier(IotSupply iotSupply){
        ReturnMsg msg = new ReturnMsg();
        msg.setSuccess(false);
        if (iotSupply.getName().equals("") || iotSupply.getName() == null){
            msg.setMessage("供应商名称不能为空");
            return msg;
        }

        if (iotSupply.getRank()==null){
            msg.setMessage("请选择供应商等级");
            return msg;
        }
        if (iotSupply.getContactName().equals("") || iotSupply.getContactName() == null){
            msg.setMessage("联系人姓名不能为空");
            return msg;
        }
        if (iotSupply.getId()!=null){
            iotSupply.setUpdateTime(new Date());
            try {
                suppplierService.updateById(iotSupply);
                msg.setSuccess(true);
                msg.setMessage("修改成功");
            } catch (Exception e) {
                if(e instanceof DuplicateKeyException) {
                    msg.setMessage("供应商"+iotSupply.getName()+"已存在");
                    msg.setSuccess(false);
                }else {
                    msg.setMessage(e.getMessage());
                    msg.setSuccess(false);
                }
            }
        }else {
            if(iotSupply.getEnName()==null || iotSupply.getEnName().equals("")){
                msg.setMessage("英文名称不能为空");
                return msg;
            }
            iotSupply.setCreateTime(new Date());
            try {
                suppplierService.addSupplier(iotSupply);
                msg.setSuccess(true);
                msg.setMessage("新增成功");
            } catch (Exception e) {
                if(e instanceof DuplicateKeyException) {
                    msg.setMessage("供应商"+iotSupply.getName()+"已存在");
                    msg.setSuccess(false);
                }else {
                    msg.setMessage(e.getMessage());
                    msg.setSuccess(false);
                }
            }
        }
        return msg;
    }

    /**
     * 检查供应商的名称是否存在
     * @param name
     * @return
     */
    @RequestMapping("/checkSupplierName")
    @ResponseBody
    public String checkSupplierName(String name){
        String ret = suppplierService.checkSupplierName(name);
        return ret;
    }
    /**
     * 检查供应商的名称是否存在
     * @param enName
     * @return
     */
    @RequestMapping("/checkEnName")
    @ResponseBody
    public String checkEnName(String enName){
        String ret = suppplierService.checkEnName(enName);
        return ret;
    }

    /**
     * 通过id获取供应商信息
     * @param supplyId
     * @return
     */
    @RequestMapping("/getSupplierById")
    @ResponseBody
    public IotSupply getSupplierById(Integer supplyId){
        return suppplierService.getSupplierById(supplyId);
    }

    /**
     * 根据id删除供应商
     * @param supplyId
     * @return
     */
//    @RequestMapping("/delSupplier")
//    @ResponseBody
//    public Integer delSupplier(Integer supplyId){
//        return suppplierService.delSupplier(supplyId);
//    }
}
