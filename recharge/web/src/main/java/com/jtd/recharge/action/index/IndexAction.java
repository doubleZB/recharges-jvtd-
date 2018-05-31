package com.jtd.recharge.action.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/11/11.
 * lhm
 */
@Controller
@RequestMapping("/index")

public class IndexAction {

    /*
* 开始进入页面
* */
    @RequestMapping("/fist")
    public String fistmaster(){
        return  "index/fist";
    }

    /*
* 主页面
* */
    @RequestMapping("/master")
    public String indexmaster(){
        return  "index/index";
    }

}
