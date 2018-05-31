package com.jtd.recharge.report;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @autor jipengkun
 */
@Controller
@RequestMapping("/Test")
public class TestCallback {

    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/callback")
    @ResponseBody
    public String flowRecharge(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map paramMap = request.getParameterMap();
            log.info(JSON.toJSON(paramMap));


            ServletInputStream in = request.getInputStream();
            InputStreamReader reader = new InputStreamReader(in, "utf-8");
            BufferedReader br = new BufferedReader(reader);
            String str = br.readLine();
            StringBuffer sb = new StringBuffer();

            while (str != null) {
                sb.append(str);
                str = br.readLine();
            }
            log.info("callback request---" + str);

            in.close();
            reader.close();
            br.close();


            log.info("测试返回 " + sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }
}
