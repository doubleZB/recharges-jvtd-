package com.jtd.recharge.user.action.order;

import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.PropertiesUtils;
import com.jtd.recharge.dao.bean.util.DesignTimeUtil;
import com.jtd.recharge.dao.po.ChargeOrder;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.service.charge.order.OrderService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 2017/2/20.
 */
@Controller
@RequestMapping("/orderExport")
public class OrderExport {
    private static Properties prop = PropertiesUtils.loadProperties("config.properties");
    @Resource
    private OrderService orderService;

    /**
     * 导出excel表
     */
    @RequestMapping("exportOrder")
    public void exportOrder(HttpServletRequest request,HttpServletResponse response) throws IOException {
        ChargeOrder chargeOrder = new ChargeOrder();
        User users =(User) request.getSession().getAttribute("users");
        Integer id = users.getId();
        int businessType = Integer.parseInt(request.getParameter("businessType"));
        String  mobile=request.getParameter("mobile");
        int  sourceOne=Integer.parseInt(request.getParameter("source"));//来源
        int  provinceid=Integer.parseInt(request.getParameter("provinceid"));//省份
        int  operatorOne=Integer.parseInt(request.getParameter("operator"));//运营商
        int  statusOne=Integer.parseInt(request.getParameter("status"));//状态
        String  merchantOrderNumber=request.getParameter("merchantOrderNumber");//商家订单号
        String remark=null;//备注
        List<String> fileNames = new ArrayList(); // 用于存放生成的文件名称s
        if( !"".equals( request.getParameter("remark"))) {
            remark = "%" + request.getParameter("remark") + "%";
        }
        chargeOrder.setRemark(remark);
        if(!"".equals(request.getParameter("merchantOrderNumber"))){
            chargeOrder.setCustomid(merchantOrderNumber);
        }

        if(!"".equals(request.getParameter("childAccount"))&&!"0".equals(request.getParameter("childAccount"))){//获取子账号ID
            chargeOrder.setUserId(Integer.parseInt(request.getParameter("childAccount")));
        }else {
            chargeOrder.setUserId(id);
        }

        if(!"".equals(mobile)){
            chargeOrder.setRechargeMobile(mobile);
        }

        if(sourceOne>0){
            chargeOrder.setSource(sourceOne);
        }

        if(provinceid>0){
            chargeOrder.setProvinceId(provinceid);
        }

        if(operatorOne>0){
            chargeOrder.setOperator(operatorOne);
        }

        if(statusOne>0){
            if(statusOne==2){
                List<Integer> statuslist=new ArrayList<Integer>();
                statuslist.add(ChargeOrder.OrderStatus.NO_STORE);
                statuslist.add(ChargeOrder.OrderStatus.NO_CHANNEL);
                chargeOrder.setStatusList(statuslist);
            }else {
                chargeOrder.setStatus(statusOne);
            }
        }


        chargeOrder.setBusinessType(businessType);
        //设开始时间
        String startTime = request.getParameter("startTime");
        //设结束时间
        String endTime = request.getParameter("endTime");
        DesignTimeUtil.Time(startTime, endTime, chargeOrder);
        List<ChargeOrder> list  = orderService.selectUserOrder(chargeOrder);

        String tableName ="流量订单统计";
        if(businessType==ChargeOrder.Business_Type.TELEPHONE_CHARGE){
            tableName ="话费订单统计";
        }
        if(businessType==ChargeOrder.Business_Type.VIDEO_CHARGE){
            tableName ="视频会员订单统计";
        }

        try {
            String path =prop.getProperty("orderExcelExportFilePath")+File.separator;
            File zip = new File(path + "excel/"  + tableName + ".zip"); // 压缩文件
            int pagesize=30000;
            int totelNum=mm(list.size(),pagesize);
            for(int j=0;j<totelNum;j++) {
                FileOutputStream outputStream  = null;
                String filePath =prop.getProperty("orderExcelExportFilePath")+File.separator+j+"-"+users.getUserName()+"-"+ DateUtil.Date2String(new Date(),"yyyy-MM-dd")+tableName+".xls";
                fileNames.add(filePath);
                //创建一个Excel工作簿对象
                outputStream = new FileOutputStream(filePath);
                //创建一个Excel工作簿对象
                HSSFWorkbook workbook = new HSSFWorkbook();
                //创建一个合并单元格做为表头根据下标合并单元格
                CellRangeAddress c1 = new CellRangeAddress(0, 0, 0, 12);
                //得到Excel工作表对象(sheet的名字为title的值)
                HSSFSheet sheet = workbook.createSheet(tableName);

                //创建单元格样式
                HSSFCellStyle style1 = workbook.createCellStyle();
                style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                //创建字体样式
                HSSFFont font1 = workbook.createFont();
                font1.setFontHeightInPoints((short) 18);
                //把字体赋予样式
                style1.setFont(font1);

                //创建单元格样式
                HSSFCellStyle style2 = workbook.createCellStyle();
                style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                //创建字体样式
                HSSFFont font2 = workbook.createFont();
                font2.setFontHeightInPoints((short) 12);
                //把字体赋予样式
                style2.setFont(font2);


                //将创建好的单元格赋予表1
                sheet.addMergedRegion(c1);
                //获取第一行
                HSSFRow row1 = sheet.createRow(0);
                //获取第一行中的第一个单元格
                HSSFCell cell = row1.createCell(0);
                //把样式赋予单元格
                cell.setCellStyle(style1);
                if (businessType == ChargeOrder.Business_Type.FLOW) {
                    cell.setCellValue("流量订单统计");
                } else  if (businessType == ChargeOrder.Business_Type.TELEPHONE_CHARGE) {
                    cell.setCellValue("话费订单统计");
                }else{
                    cell.setCellValue("视频会员订单统计");
                }
                //=======================================//
                //获取第二行
                HSSFRow row2 = sheet.createRow(1);
                if (businessType == ChargeOrder.Business_Type.FLOW) {
                    String[] titles = {"序号", "订单号", "手机号", "省份", "运营商", "流量包", "支付金额", "下单时间", "归属账号", "来源", "商家订单号", "订单状态", "备注"};
                    for (int i = 0; i < titles.length; i++) {
                        String title = titles[i];
                        HSSFCell cell2 = row2.createCell(i);
                        cell2.setCellStyle(style2);
                        cell2.setCellValue(title);
                    }
                } else if (businessType == ChargeOrder.Business_Type.TELEPHONE_CHARGE) {
                    String[] titles = {"序号", "订单号", "手机号", "省份", "运营商", "面值", "支付金额", "下单时间", "归属账号", "来源", "商家订单号", "订单状态", "备注"};
                    for (int i = 0; i < titles.length; i++) {
                        String title = titles[i];
                        HSSFCell cell2 = row2.createCell(i);
                        cell2.setCellStyle(style2);
                        cell2.setCellValue(title);
                    }
                }else{
                    String[] titles = {"序号", "订单号", "手机号", "省份", "运营商", "产品", "支付金额", "下单时间", "归属账号", "来源", "商家订单号", "订单状态", "备注"};
                    for (int i = 0; i < titles.length; i++) {
                        String title = titles[i];
                        HSSFCell cell2 = row2.createCell(i);
                        cell2.setCellStyle(style2);
                        cell2.setCellValue(title);
                    }
                }
                int h=0;
                for (int k = pagesize*j; k < pagesize*(j+1); k++) {
                    //输出list
                    if(k>=list.size()){
                        break;
                    }
                    ChargeOrder chargeOrderOne = list.get(k);
                    Integer status = chargeOrderOne.getStatus();
                    if (status == ChargeOrder.OrderStatus.CREATE_ORDER) {
                        chargeOrderOne.setStatusName("创建订单");
                    } else if (status == ChargeOrder.OrderStatus.NO_STORE || status == ChargeOrder.OrderStatus.NO_CHANNEL) {
                        chargeOrderOne.setStatusName("提交失败");
                    } else if (status == ChargeOrder.OrderStatus.NO_BALANCE) {
                        chargeOrderOne.setStatusName("余额不足");
                    } else if (status == ChargeOrder.OrderStatus.CACHING) {
                        chargeOrderOne.setStatusName("待充值");
                    } else if (status == ChargeOrder.OrderStatus.CHARGEING) {
                        chargeOrderOne.setStatusName("充值中");
                    } else if (status == ChargeOrder.OrderStatus.CHARGE_SUCCESS) {
                        chargeOrderOne.setStatusName("成功");
                    } else if (status == ChargeOrder.OrderStatus.CHARGE_FAIL) {
                        chargeOrderOne.setStatusName("失败");
                    } else {
                        chargeOrderOne.setStatusName("");
                    }
                    Integer operator = chargeOrderOne.getOperator();
                    if (operator == ChargeOrder.OperatorStatus.MOVE) {
                        chargeOrderOne.setOperatorName("移动");
                    } else if (operator == ChargeOrder.OperatorStatus.UNI_INFO) {
                        chargeOrderOne.setOperatorName("联通");
                    } else if (operator == ChargeOrder.OperatorStatus.TELECOM) {
                        chargeOrderOne.setOperatorName("电信");
                    }else if (operator == ChargeOrder.OperatorStatus.YOUKU) {
                        chargeOrderOne.setOperatorName("优酷");
                    } else if (operator == ChargeOrder.OperatorStatus.iQIYI) {
                        chargeOrderOne.setOperatorName("爱奇艺");
                    }else if (operator == ChargeOrder.OperatorStatus.TENCENT) {
                        chargeOrderOne.setOperatorName("腾讯");
                    } else if (operator == ChargeOrder.OperatorStatus.SOHU) {
                        chargeOrderOne.setOperatorName("搜狐");
                    } else if (operator == ChargeOrder.OperatorStatus.LETV) {
                        chargeOrderOne.setOperatorName("乐视");
                    } else {
                        chargeOrderOne.setOperatorName("");
                    }
                    Integer source = chargeOrderOne.getSource();
                    if (source == ChargeOrder.UserSource.PORT) {
                        chargeOrderOne.setSourceName("接口");
                    } else if (source == ChargeOrder.UserSource.PAGE) {
                        chargeOrderOne.setSourceName("页面");
                    } else {
                        chargeOrderOne.setSourceName("");
                    }
                    HSSFRow row = sheet.createRow(h + 2);

                    HSSFCell cc1 = row.createCell(0);
                    cc1.setCellValue(chargeOrderOne.getId());

                    HSSFCell cc2 = row.createCell(1);
                    cc2.setCellValue(chargeOrderOne.getOrderNum());

                    HSSFCell cc3 = row.createCell(2);
                    cc3.setCellValue(chargeOrderOne.getRechargeMobile());

                    HSSFCell cc4 = row.createCell(3);
                    cc4.setCellValue(chargeOrderOne.getValue());

                    HSSFCell cc5 = row.createCell(4);
                    cc5.setCellValue(chargeOrderOne.getOperatorName());

                    HSSFCell cc6 = row.createCell(5);
                    cc6.setCellValue(chargeOrderOne.getPackageSize());

                    HSSFCell cc7 = row.createCell(6);
                    cc7.setCellValue(chargeOrderOne.getAmount().toString());

                    HSSFCell cc8 = row.createCell(7);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String format = simpleDateFormat.format(chargeOrderOne.getOrderTime());
                    cc8.setCellValue(format);

                    HSSFCell cc9 = row.createCell(8);
                    cc9.setCellValue(chargeOrderOne.getUserCnName());

                    HSSFCell cc10 = row.createCell(9);
                    cc10.setCellValue(chargeOrderOne.getSourceName());

                    HSSFCell cc11 = row.createCell(10);
                    cc11.setCellValue(chargeOrderOne.getCustomid());

                    HSSFCell cc12 = row.createCell(11);
                    cc12.setCellValue(chargeOrderOne.getStatusName());

                    HSSFCell cc13 = row.createCell(12);
                    cc13.setCellValue(chargeOrderOne.getRemark());
                    h++;
                }
                workbook.write(outputStream);
                try {
                    outputStream.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            File []srcfile = new File[fileNames.size()];
            for (int i = 0, n = fileNames.size(); i < n; i++) {
                srcfile[i] = new File(fileNames.get(i));
            }
            zipFiles(srcfile, zip);
            download(zip.toString(),response);
        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {

        }
    }

    /**
     * 文件删除
     * @param fileNames
     * @param zipPath
     */
    public void deleteFile(List<String> fileNames, String zipPath) {
        String sPath = null;
        File file = null;
        boolean flag = false;
        try {
            // 判断目录或文件是否存在
            for (int i = 0; i < fileNames.size(); i++) {
                sPath = fileNames.get(i);
                file = new File(sPath);
                if (file.exists())  {
                    file.delete();
                }
            }
            file = new File(zipPath);
            if (file.exists())  {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles(java.io.File[] srcfile, java.io.File zipfile) {
        byte[] buf = new byte[1024];
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                    zipfile));
            for (int i = 0; i < srcfile.length; i++) {
                FileInputStream in = new FileInputStream(srcfile[i]);
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载
     */
    public void download(String path,HttpServletResponse response) {
        try {

            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            OutputStream toClient = new BufferedOutputStream(response
                    .getOutputStream());

            response.setContentType("application/x-msdownload");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(filename.getBytes("GB2312"), "8859_1"));// 设定输出文件头
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public  int   mm(int   a, int   b){
        return   (a%b==0)?a/b:(a/b+1);
    }
}