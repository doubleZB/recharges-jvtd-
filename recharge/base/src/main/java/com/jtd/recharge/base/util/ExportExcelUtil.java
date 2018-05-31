package com.jtd.recharge.base.util;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExportExcelUtil<T> {


    private static Logger log = Logger.getLogger(ExportExcelUtil.class);

    /**
     * 导出Excel
     * @param response HttpServletResponse
     * @param fileName 导出后excel文件名称
     * @param headers excel文件内标题
     * @param contactsList 数据集合
     * @throws Exception
     */
    public static void exportExcel(HttpServletResponse response, String fileName, String[] headers, List contactsList) throws Exception{
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        OutputStream out2 = null;
        try {
            out2 = response.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        short count[] = {0};
        exportExcel(fileName, headers, contactsList, out2, DateUtil.SQL_TIME_FMT, count);
        try {
            if (out2 != null) {
                out2.close();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    /**
     * @param title   sheet的名称
     * @param dataset 需要导出的数据集合
     * @param out     导出流
     * @param pattern 时间类型的格式化样式
     * @param cindex  需要统计字段的属性索引 从0开始，支持多个统计字段
     */
    @SuppressWarnings("deprecation")
    public static void exportExcel(String title, String[] params,
                                   Collection<?> dataset, OutputStream out, String pattern,
                                   short[] cindex) {
        // 从参数中拆分出表头和导出字段列表
        if (params != null && params.length > 0) {
            int len = params.length;
            String headers[] = new String[len];
            String cloums[] = new String[len];
            for (int i = 0; i < len; i++) {
                cloums[i] = params[i].split(":")[0];// 要导出的字段列表
                headers[i] = params[i].split(":")[1];// excel表头
            }
            // 创建 excel工作表
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 创建sheet,设置title
            HSSFSheet sheet = workbook.createSheet(title);
            // 设置每一列的宽度
            sheet.setDefaultColumnWidth(18);
            sheet.setDefaultRowHeight((short) 350);
            HSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor((short) 40);
            style.setFillPattern((short) 1);
            style.setBorderBottom((short) 1);
            style.setBorderLeft((short) 1);
            style.setBorderRight((short) 1);
            style.setBorderTop((short) 1);
            style.setAlignment((short) 2);
            HSSFFont font = workbook.createFont();
            font.setColor((short) 20);
            font.setFontHeightInPoints((short) 12);
            font.setBoldweight((short) 700);
            style.setFont(font);
            HSSFCellStyle style2 = workbook.createCellStyle();
            style2.setFillForegroundColor((short) 43);
            style2.setFillPattern((short) 1);
            style2.setBorderBottom((short) 1);
            style2.setBorderLeft((short) 1);
            style2.setBorderRight((short) 1);
            style2.setBorderTop((short) 1);
            style2.setAlignment((short) 2);
            style2.setVerticalAlignment((short) 1);
            HSSFFont font2 = workbook.createFont();
            font2.setBoldweight((short) 400);
            style2.setFont(font2);
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(
                    (short) 0, (short) 0, (short) 0, (short) 0, (short) 4,
                    (short) 2, (short) 6, (short) 5));
            comment.setString(new HSSFRichTextString("注释！"));
            comment.setAuthor("ape-tech");
            HSSFRow row = sheet.createRow(0);
            // 设置表头
            for (int i = 0; i < len; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            // 设置数据
            Iterator<?> it = dataset.iterator();
            int index = 0;
            long count[] = new long[0];
            if (cindex != null && cindex.length > 0) {
                count = new long[cindex.length];
            }
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                Object t = it.next();
                Field[] fields = t.getClass().getDeclaredFields();
                for (int i = 0; i < fields.length; i = i + 1) {
                    Field field = fields[i];
                    String fieldName = field.getName();
                    short findex = isHasFildeIndex(cloums, fieldName);
                    if (findex == -1) {
                        continue;
                    } else {
                        HSSFCell cell = row.createCell(findex);
                        cell.setCellStyle(style2);
                        String getMethodName = "get"
                                + fieldName.substring(0, 1).toUpperCase()
                                + fieldName.substring(1);
                        try {
                            Class<?> tCls = t.getClass();
                            Method getMethod = tCls.getMethod(getMethodName,
                                    new Class[0]);
                            Object value = getMethod.invoke(t, new Object[0]);
                            String textValue = null;
                            if ((value instanceof Date)) {
                                Date date = (Date) value;
                                SimpleDateFormat sdf = new SimpleDateFormat(
                                        pattern);
                                textValue = sdf.format(date);
                            } else if ((value instanceof byte[])) {
                                row.setHeightInPoints(60.0F);
                                sheet.setColumnWidth(i, 2856);
                                byte[] bsValue = (byte[]) value;
                                HSSFClientAnchor anchor = new HSSFClientAnchor(
                                        (short) 0, (short) 0, (short) 1023,
                                        (short) 255, (short) 6, index,
                                        (short) 6, index);
                                anchor.setAnchorType(2);
                                patriarch.createPicture(anchor,
                                        workbook.addPicture(bsValue, 5));
                            } else {
                                if (value != null) {
                                    textValue = value.toString();
                                }
                            }
                            short ts = isHasCount(cindex, findex);
                            /*if (ts != -1) {
								count[ts] = count[ts]
										+ Long.parseLong(textValue);
							}*/
                            if (textValue != null) {
                                Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
                                Matcher matcher = p.matcher(textValue);
                                if (matcher.matches()) {
                                    cell.setCellValue(Double
                                            .parseDouble(textValue));
                                } else {
                                    HSSFRichTextString richString = new HSSFRichTextString(
                                            textValue);
                                    HSSFFont font3 = workbook.createFont();
                                    font3.setColor((short) 12);
                                    richString.applyFont(font3);
                                    cell.setCellValue(richString);
                                }
                            }
                        } catch (Exception e) {
                            log.error("报告导出异常", e);
                        }
                    }

                }
            }
            // 增加统计数据
//			if (cindex != null && cindex.length > 0) {
//				index = index + 1;
//				HSSFRow crow = sheet.createRow(index);
//				HSSFCell hcell = crow.createCell(0);
//				hcell.setCellStyle(style);
//				hcell.setCellValue("总数");
//				for (short i : cindex) {
//					short m = isHasCount(cindex, i);
//					HSSFCell ccell = crow.createCell(i);
//					ccell.setCellStyle(style2);
//					ccell.setCellValue(count[m]);
//				}
//			}
            try {
                workbook.write(out);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @param title   sheet的名称
     * @param dataset 需要导出的数据集合
     * @param out     导出流
     * @param pattern 时间类型的格式化样式
     * @param cindex  需要统计字段的属性索引 从0开始，支持多个统计字段
     */
    @SuppressWarnings("deprecation")
    public static void exportExcelNew(String title, String[] params,
                                      Collection<?> dataset, OutputStream out, String pattern,
                                      short[] cindex) {
        // 从参数中拆分出表头和导出字段列表
        if (params != null && params.length > 0) {
            int len = params.length;
            String headers[] = new String[len];
            String cloums[] = new String[len];
            for (int i = 0; i < len; i++) {
                cloums[i] = params[i].split(":")[0];// 要导出的字段列表
                headers[i] = params[i].split(":")[1];// excel表头
            }
            // 创建 excel工作表
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 创建sheet,设置title
            HSSFSheet sheet = workbook.createSheet(title);
            // 设置每一列的宽度
            sheet.setDefaultColumnWidth(20);
            sheet.setDefaultRowHeight((short) 350);
            HSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor((short) 40);
            style.setFillPattern((short) 1);
            style.setBorderBottom((short) 1);
            style.setBorderLeft((short) 1);
            style.setBorderRight((short) 1);
            style.setBorderTop((short) 1);
            style.setAlignment((short) 2);
            HSSFFont font = workbook.createFont();
            font.setColor((short) 20);
            font.setFontHeightInPoints((short) 12);
            font.setBoldweight((short) 700);
            style.setFont(font);
            HSSFCellStyle style2 = workbook.createCellStyle();
            style2.setFillForegroundColor((short) 43);
            style2.setFillPattern((short) 1);
            style2.setBorderBottom((short) 1);
            style2.setBorderLeft((short) 1);
            style2.setBorderRight((short) 1);
            style2.setBorderTop((short) 1);
            style2.setAlignment((short) 2);
            style2.setVerticalAlignment((short) 1);
            HSSFFont font2 = workbook.createFont();
            font2.setBoldweight((short) 400);
            style2.setFont(font2);
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(
                    (short) 0, (short) 0, (short) 0, (short) 0, (short) 4,
                    (short) 2, (short) 6, (short) 5));
            comment.setString(new HSSFRichTextString("注释！"));
            comment.setAuthor("ape-tech");
            HSSFRow row = sheet.createRow(0);
            // 设置表头
            for (int i = 0; i < len; i = i + 1) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            // 设置数据
            Iterator<?> it = dataset.iterator();
            int index = 0;
            long count[] = new long[0];
            if (cindex != null && cindex.length > 0) {
                count = new long[cindex.length];
            }
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                Map<String, Object> t = (Map<String, Object>) it.next();
                int i = 0;
                for (Iterator<String> iter = t.keySet().iterator(); iter.hasNext(); ) {
                    i++;
                    String fieldName = iter.next();
                    short findex = isHasFildeIndex(cloums, fieldName);
                    if (findex == -1) {
                        continue;
                    } else {
                        HSSFCell cell = row.createCell(findex);
                        cell.setCellStyle(style2);
                        try {
                            Object value = t.get(fieldName);
                            String textValue = null;
                            if ((value instanceof Date)) {
                                Date date = (Date) value;
                                SimpleDateFormat sdf = new SimpleDateFormat(
                                        pattern);
                                textValue = sdf.format(date);
                            } else if ((value instanceof byte[])) {
                                row.setHeightInPoints(60.0F);
                                sheet.setColumnWidth(i, 2856);
                                byte[] bsValue = (byte[]) value;
                                HSSFClientAnchor anchor = new HSSFClientAnchor(
                                        (short) 0, (short) 0, (short) 1023,
                                        (short) 255, (short) 6, index,
                                        (short) 6, index);
                                anchor.setAnchorType(2);
                                patriarch.createPicture(anchor,
                                        workbook.addPicture(bsValue, 5));
                            } else {
                                if (value != null) {
                                    textValue = value.toString();
                                }
                            }
                            short ts = isHasCount(cindex, findex);
                            if (ts != -1) {
                                count[ts] = count[ts]
                                        + Long.parseLong(textValue);
                            }
                            if (textValue != null) {
                                Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
                                Matcher matcher = p.matcher(textValue);
                                if (matcher.matches()) {
                                    cell.setCellValue(Double
                                            .parseDouble(textValue));
                                } else {
                                    HSSFRichTextString richString = new HSSFRichTextString(
                                            textValue);
                                    HSSFFont font3 = workbook.createFont();
                                    font3.setColor((short) 12);
                                    richString.applyFont(font3);
                                    cell.setCellValue(richString);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
            // 增加统计数据
            if (cindex != null && cindex.length > 0) {
                index = index + 1;
                HSSFRow crow = sheet.createRow(index);
                HSSFCell hcell = crow.createCell(0);
                hcell.setCellStyle(style);
                hcell.setCellValue("总数");
                for (short i : cindex) {
                    short m = isHasCount(cindex, i);
                    HSSFCell ccell = crow.createCell(i);
                    ccell.setCellStyle(style2);
                    ccell.setCellValue(count[m]);
                }
            }
            try {
                workbook.write(out);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static short isHasFildeIndex(String cloums[], String fildeName) {
        short index = -1;
        if (cloums != null && cloums.length > 0) {
            for (short i = 0; i < cloums.length; i++) {
                String filed = cloums[i];
                if (fildeName.toLowerCase().equals(filed.toLowerCase())) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    // 查询需要统计的字段在数组中的位置
    public static short isHasCount(short cindex[], short index) {
        short rs = -1;
        if (cindex != null && cindex.length > 0) {
            for (short i = 0; i < cindex.length; i++) {
                short j = cindex[i];
                if (j == index) {
                    rs = i;
                    break;
                }
            }
        }
        return rs;
    }

    /**
     * 导出excel共同方法，多表联合查询，没有orm类，反射不了
     *
     * @param title
     * @param headers
     * @param dataset
     * @param out
     * @param pattern
     */
    @SuppressWarnings({"deprecation", "unchecked"})
    public static void exportToExcel(String title, String[] headers,
                                     String[] fielders, Collection<?> dataset, OutputStream out,
                                     String pattern) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet(title);

        sheet.setDefaultColumnWidth(15);

        HSSFCellStyle style = workbook.createCellStyle();

        style.setFillForegroundColor((short) 40);
        style.setFillPattern((short) 1);
        style.setBorderBottom((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setBorderTop((short) 1);
        style.setAlignment((short) 2);

        HSSFFont font = workbook.createFont();
        font.setColor((short) 20);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight((short) 700);

        style.setFont(font);

        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor((short) 43);
        style2.setFillPattern((short) 1);
        style2.setBorderBottom((short) 1);
        style2.setBorderLeft((short) 1);
        style2.setBorderRight((short) 1);
        style2.setBorderTop((short) 1);
        style2.setAlignment((short) 2);
        style2.setVerticalAlignment((short) 1);

        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight((short) 400);

        style2.setFont(font2);

        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(
                (short) 0, (short) 0, (short) 0, (short) 0, (short) 4,
                (short) 2, (short) 6, (short) 5));

        comment.setString(new HSSFRichTextString("注释！"));

        comment.setAuthor("ape-tech");

        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i = (short) (i + 1)) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        Iterator it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            Object t = it.next();

            // Field[] fields = t.getClass().getDeclaredFields();

            for (short i = 0; i < fielders.length; i = (short) (i + 1)) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                // Field field = fields[i];
                // String fieldName = field.getName();
                String fieldName = fielders[i];

                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName,
                            new Class[0]);
                    Object value = getMethod.invoke(t, new Object[0]);

                    if (fieldName.equals("age")) {
                        @SuppressWarnings("unused")
                        int i1 = ((Integer) value).intValue();
                    }

                    String textValue = null;

                    if ((value instanceof Boolean)) {
                        boolean bValue = ((Boolean) value).booleanValue();
                        textValue = "男";
                        if (!bValue)
                            textValue = "女";
                    } else if ((value instanceof Date)) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if ((value instanceof byte[])) {
                        row.setHeightInPoints(60.0F);

                        sheet.setColumnWidth(i, 2856);
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(
                                (short) 0, (short) 0, (short) 1023,
                                (short) 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor,
                                workbook.addPicture(bsValue, 5));
                    } else {
                        textValue = value.toString();
                    }

                    if (textValue != null) {
                        Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(
                                    textValue);
                            HSSFFont font3 = workbook.createFont();
                            font3.setColor((short) 12);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}