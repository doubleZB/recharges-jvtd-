package com.jtd.recharge.user.action.finance;

import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.PropertiesUtils;
import com.jtd.recharge.dao.bean.util.DesignTimeUtil;
import com.jtd.recharge.dao.po.ChargeOrder;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserBalanceDetail;
import com.jtd.recharge.service.charge.order.OrderService;
import com.jtd.recharge.service.user.BillService;
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
@RequestMapping("/balanceExport")
public class BalanceExport {
    private static Properties prop = PropertiesUtils.loadProperties("config.properties");
    @Resource
    BillService billService;

    /**
     * 导出excel表
     */
    @RequestMapping("exportbalance")
    public void exportOrder(HttpServletRequest request,HttpServletResponse response) throws IOException {
        ChargeOrder chargeOrder = new ChargeOrder();
        User users =(User) request.getSession().getAttribute("users");
        Integer id = users.getId();
        String flowNum = request.getParameter("flowNum");
        String inOrOut=request.getParameter("inOrOut");
        String costType=request.getParameter("costType");
        List<String> fileNames = new ArrayList(); // 用于存放生成的文件名称s

        //设开始时间
        String startTime = request.getParameter("startTime");
        //设结束时间
        String endTime = request.getParameter("endTime");
        UserBalanceDetail detail = new UserBalanceDetail();
        //设置当前登录用户主键ID
        detail.setUserId(users.getId());

        if (flowNum!=null && flowNum.length()!=0){
            detail.setSequence(flowNum);
        }
        if (startTime!=null && startTime.length()!=0){
            detail.setUpdateTime(DateUtil.String2Date(startTime));
        }
        if (endTime!=null && endTime.length()!=0){
            detail.setUpdateTimeEnd(DateUtil.String2Date(endTime));
        }
        if (inOrOut!=null && inOrOut.length()!=0 && !"0".equals(inOrOut)){
            detail.setBillType(Integer.parseInt(inOrOut));
        }
        if (costType!=null && costType.length()!=0 && !"0".equals(costType)){
            detail.setCategory(Integer.parseInt(costType));
        }
        List<UserBalanceDetail> list  = billService.getBillDetails(detail);
        String tableName ="账单明细";

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
                font2.setFontHeightInPoints((short) 8);
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
                cell.setCellValue("账单明细统计");
                //=======================================//
                //获取第二行
                HSSFRow row2 = sheet.createRow(1);
                String[] titles = {"序号", "支付流水号", "出入账", "类别", "描述", "金额(元)", "余额(元)", "时间"};
                for (int i = 0; i < titles.length; i++) {
                    String title = titles[i];
                    HSSFCell cell2 = row2.createCell(i);
                    cell2.setCellStyle(style2);
                    cell2.setCellValue(title);
                }
                int h=0;
                for (int k = pagesize*j; k < pagesize*(j+1); k++) {
                    //输出list
                    if(k>=list.size()){
                        break;
                    }
                    UserBalanceDetail userBalanceDetail = list.get(k);
                    Integer category = userBalanceDetail.getCategory();
                    if (category == UserBalanceDetail.Category.RECHARGE) {
                        userBalanceDetail.setCategoryName("充值");
                    } else if (category == UserBalanceDetail.Category.REFUND) {
                        userBalanceDetail.setCategoryName("退款");
                    }  else if (category == UserBalanceDetail.Category.CONSUME) {
                        userBalanceDetail.setCategoryName("消费");
                    } else if (category == UserBalanceDetail.Category.BORROW) {
                        userBalanceDetail.setCategoryName("借款");
                    } else if (category == UserBalanceDetail.Category.TRANSFER) {
                        userBalanceDetail.setCategoryName("转账");
                    } else if (category == UserBalanceDetail.Category.REDUCE_MONEY) {
                        userBalanceDetail.setCategoryName("减款");
                    }
                    Integer billType = userBalanceDetail.getBillType();
                    if (billType == UserBalanceDetail.BillType.CHARGE_OFF) {
                        userBalanceDetail.setBillTypeName("出账");
                    } else if (billType ==UserBalanceDetail.BillType.ENTRY) {
                        userBalanceDetail.setBillTypeName("入账");
                    }

                    HSSFRow row = sheet.createRow(h + 2);

                    HSSFCell cc1 = row.createCell(0);
                    cc1.setCellValue(userBalanceDetail.getId());

                    HSSFCell cc2 = row.createCell(1);
                    cc2.setCellValue(userBalanceDetail.getSequence());

                    HSSFCell cc3 = row.createCell(2);
                    cc3.setCellValue(userBalanceDetail.getCategoryName());

                    HSSFCell cc4 = row.createCell(3);
                    cc4.setCellValue(userBalanceDetail.getCategoryName());

                    HSSFCell cc5 = row.createCell(4);
                    cc5.setCellValue(userBalanceDetail.getDescription());

                    HSSFCell cc6 = row.createCell(5);
                    cc6.setCellValue(userBalanceDetail.getAmount().toString());

                    HSSFCell cc7 = row.createCell(6);
                    cc7.setCellValue(userBalanceDetail.getBalance().toString());

                    HSSFCell cc8 = row.createCell(7);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String format = simpleDateFormat.format(userBalanceDetail.getUpdateTime());
                    cc8.setCellValue(format);
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

    public static void zipFiles(File[] srcfile, File zipfile) {
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