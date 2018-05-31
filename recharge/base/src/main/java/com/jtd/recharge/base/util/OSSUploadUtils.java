package com.jtd.recharge.base.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.OSSObject;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/22.
 * 阿里云oss文件上传
 */
public class OSSUploadUtils {
    //获取阿里云基础参数
    private static final String ENDPOINT = (String) PropertiesUtils.loadProperties("config.properties").get("endpoint");
    private static final String ACCESS_KEY_ID = (String) PropertiesUtils.loadProperties("config.properties").get("accessKeyId");
    private static final String ACCESS_KEY_SECRET = (String) PropertiesUtils.loadProperties("config.properties").get("accessKeySecret");
    private static final String BUCKET_NAME = (String) PropertiesUtils.loadProperties("config.properties").get("bucketName");
    /**
     * 初始化OSS客户端
     */
    public static OSSClient initOSSClient(){
        // 创建OSSClient实例
        return new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 关闭OSS客户端
     */
    public static void colseOSSClient(OSSClient ossClient){
        // 关闭ossClient实例
        ossClient.shutdown();
    }

    /**
     * 创建Bucket上传管理空间
     * @param bucketName 创建的空间名称
     * @param ossClient  OSSClient客户端
     */
    public static void createBucket(OSSClient ossClient,String bucketName){
        //判断是否存在bucket空间
        System.out.println("bucketName0........."+bucketName);
        boolean exists = ossClient.doesBucketExist(bucketName);
        System.out.println("bucketName1........."+bucketName);
        if(!exists){
            ossClient.createBucket(bucketName);
            // 设置bucket权限
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicReadWrite);
        }
    }

    /**
     * 删除Bucket
     * @param bucketName 创建的空间名称
     * @param ossClient  OSSClient客户端
     */
    public static void deleteBucket(OSSClient ossClient,String bucketName){
        //判断是否存在bucket空间
        boolean exists = ossClient.doesBucketExist(bucketName);
        if(!exists){
            ossClient.deleteBucket(bucketName);
        }
    }

    /**
     * 删除单个文件
     */
    public static void deleteFile(OSSClient ossClient,String fileName,String bucketName){
        if (ossClient.doesObjectExist(bucketName,fileName)){
            ossClient.deleteObject(bucketName, fileName);
        }

    }

    /**
     * 下载文件
     */
    public static void downLoadFile(String fileName, HttpServletResponse response) throws IOException {
        OSSClient ossClient = initOSSClient();
        OSSObject ossObject = ossClient.getObject(BUCKET_NAME, fileName);
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 读Object内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        // 循环取出流中的数据
        byte[] buf = new byte[1024];
        InputStream inStream = ossObject.getObjectContent();
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*while (true) {
            String line = reader.readLine();
            if (line == null) break;
        }*/
        reader.close();
        colseOSSClient(ossClient);
    }

    public static String uploadFile(String modelName,MultipartFile file) throws IOException {
        //文件原始名称
        String originalFilename = file.getOriginalFilename();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //重命名文件名称
        String fileName = sdf.format(new Date());
        //随机5位数
        String random  = RandomStringUtils.randomNumeric(5);
        if (originalFilename.lastIndexOf(".") != -1){
            fileName = fileName+random+originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        // 上传文件
        InputStream inputStream = file.getInputStream();
        final String keySuffixWithSlash = modelName+"/";
        OSSClient ossClient = initOSSClient();
        createBucket(ossClient,BUCKET_NAME);
        boolean  doesObjectExist = ossClient.doesObjectExist(BUCKET_NAME,keySuffixWithSlash);
        if (!doesObjectExist){
            ossClient.putObject(BUCKET_NAME,keySuffixWithSlash,new ByteArrayInputStream(new byte[0]));
        }
        ossClient.putObject(BUCKET_NAME,keySuffixWithSlash+fileName,inputStream);
        colseOSSClient(ossClient);
        return fileName;
    }

    /**
     * 流方式上传文件
     * @param modelName
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String uploadFile(String modelName,String fileName,InputStream inputStream) throws IOException {
        //重命名文件名称
        final String keySuffixWithSlash = modelName+"/";
        OSSClient ossClient = initOSSClient();
        createBucket(ossClient,BUCKET_NAME);
        boolean  doesObjectExist = ossClient.doesObjectExist(BUCKET_NAME,keySuffixWithSlash);
        if (!doesObjectExist){
            ossClient.putObject(BUCKET_NAME,keySuffixWithSlash,new ByteArrayInputStream(new byte[0]));
        }
        ossClient.putObject(BUCKET_NAME,keySuffixWithSlash+fileName,inputStream);
        colseOSSClient(ossClient);
        return keySuffixWithSlash+fileName;
    }
}
