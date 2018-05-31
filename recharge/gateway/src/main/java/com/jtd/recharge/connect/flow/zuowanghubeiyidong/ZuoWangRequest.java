package com.jtd.recharge.connect.flow.zuowanghubeiyidong;

import com.jtd.recharge.base.cache.redis.RedisTemplate;
import com.jtd.recharge.dao.mapper.ChargeOrderDetailMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liyabin on 2017/12/7.
 */
@Controller
@RequestMapping("/return")
public class ZuoWangRequest {

    @Resource
    ChargeOrderDetailMapper chargeOrderDetailMapper;

    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/zuowanghubeiyidong")
    protected  void report(HttpServletRequest request, HttpServletResponse response) throws IOException,DocumentException {
        String bodyxml=request.getParameter("xmlbody");
        String xmlhead=request.getParameter("xmlhead");
        Document documentHeader = DocumentHelper.parseText(xmlhead);
        Element rootHeader = documentHeader.getRootElement();
        String BIPCode = rootHeader.element("BIPType").elementText("BIPCode");
        log.info("BIPCode:"+BIPCode);
        ZuoWangDJFlowReport dj=new ZuoWangDJFlowReport();
        ZuoWangHuBeiYiDongFlowReport  bg=new ZuoWangHuBeiYiDongFlowReport();
        String result="";
        if(BIPCode.equals("BIP4B877")){
            result= dj.report(bodyxml,xmlhead,chargeOrderDetailMapper);
        }
        if(BIPCode.equals("BIP4B875")){
            result= bg.report(bodyxml,xmlhead,chargeOrderDetailMapper);
        }
        response.getWriter().print(result);
    }
}
