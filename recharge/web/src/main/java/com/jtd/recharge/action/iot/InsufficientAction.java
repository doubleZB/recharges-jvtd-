package com.jtd.recharge.action.iot;

import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.po.IotConfig;
import com.jtd.recharge.service.iot.IotConfigService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by ${zyj} on 2018/4/16.
 * 库存预警设置
 */
@Controller
@RequestMapping("/insufficient")
public class InsufficientAction {
    @Resource
    private IotConfigService iotConfigService;

    /**
     * 跳转到预警设置页面
     *
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "iot/insufficientStock";
    }

    @RequestMapping("list")
    @ResponseBody
    public IotConfig list() {
        IotConfig config = new IotConfig();
        config.setName("stock_alarm");
        return iotConfigService.getByName(config);
    }

    @RequestMapping("addStockAlarm")
    @ResponseBody
    public ReturnMsg addStockAlarm(IotConfig config){
        ReturnMsg msg = new ReturnMsg();
        msg.setSuccess(true);
        JSONObject jsonObject = JSONObject.fromObject(config.getConfigData());
        if(jsonObject.getString("open")==null ||  ("").equals(jsonObject.getString("open"))){
            msg.setSuccess(false);
            msg.setMessage("请设置开关");
            return msg;
        }
        if(jsonObject.getString("threshold")==null ||  ("").equals(jsonObject.getString("threshold"))){
            msg.setSuccess(false);
            msg.setMessage("请设置库存报警阈值");
            return msg;
        }
        if(jsonObject.getString("mobiles")==null ||  ("").equals(jsonObject.getString("mobiles"))){
            msg.setSuccess(false);
            msg.setMessage("请填写手机号");
            return msg;
        }
        if(config.getId()!=null){
            Integer ret =  iotConfigService.updateStockAlarm(config);
            if(ret >0){
                msg.setMessage("更新成功");
                return msg;
            }else{
                msg.setSuccess(false);
                msg.setMessage("更新失败");
                return msg;
            }
        }else {
            Integer ret = iotConfigService.addStockAlarm(config);
            if (ret > 0) {
                msg.setMessage("添加成功");
                return msg;
            } else {
                msg.setSuccess(false);
                msg.setMessage("添加失败");
                return msg;
            }
        }
    }
}

