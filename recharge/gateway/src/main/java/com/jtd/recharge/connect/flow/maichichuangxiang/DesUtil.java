package com.jtd.recharge.connect.flow.maichichuangxiang;

import org.apache.axis.encoding.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * Created by liyabin on 2017/9/11.
 */
public class DesUtil {
    private final static String DES = "DES";
    private final static String DES1 = "DES/ECB/NoPadding";
    private final static String DES2 = "DES/ECB/NoPadding";

    public static void main(String[] args) throws Exception
    {
        DesUtil d = new DesUtil();
      //  String a = d.encrypt("streamNO=201603040951021049&LoginType=1&CorpLoginName=终端公司&CorpPassword=zte@1234&CorpID=&PorCode=&MessCode=", "00000001");
       // System.out.println(a);
        System.out.println(d.decrypt("VTSpWKQ7qnYx3PL4zwC4cxOz1VeAIRm8Zv+kKu3CI2IA3V4I9RQhisqmVBvvqO3/GMTTnVBszWoPb0WieD98wMh2FpCEu/GPtmHqM+9+sZnkM7pBk1vxAj0e9uCM+/O/vJU45WuTqiYvIQcnSS3/cvH6T0ij0XZV5xwcS6nM16RUJycUSoFmkA==", "a17bb2a5"));
    }
    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
    public String encrypt(String data, String key) throws Exception
    {
        byte[] databyte = data.getBytes("utf-8");
        int length = databyte.length%8;
        if(length != 0)
        {
            length = 8-length;
            byte[] byte_3 = new byte[databyte.length+length];
            byte[] bb = new byte[length];
            System.arraycopy(databyte, 0, byte_3, 0, databyte.length);
            System.arraycopy(bb, 0, byte_3, databyte.length, bb.length);

            byte[] bt = encrypt(byte_3, key.getBytes());
            String strs = Base64.encode(bt);
            return strs;
        }
        else
        {
            byte[] bt = encrypt(databyte, key.getBytes());
            String strs = Base64.encode(bt);
            return strs;
        }
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn
     *            需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception
     *             本方法不处理任何异常，所有异常全部抛出
     * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
     */
    public byte[] hexStr2ByteArr(String strIn) throws Exception
    {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2)
        {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public String decrypt(String data, String key) throws IOException,
            Exception
    {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, key.getBytes());
        // 去除末尾的0
        int num = 0;
        for (int i = bt.length - 1; i >= 0; i--)
        {
            if (bt[i] == 0 && i == bt.length - 1 - num)
            {
                num++;
            }
            else
            {
                break;
            }
        }
        byte[] resu = subBytes(bt, 0, bt.length - num);
        return new String(resu,"utf-8");
    }

    public byte[] subBytes(byte[] src, int begin, int count)
    {
        byte[] bs = new byte[count];
        for (int i = begin; i < begin + count; i++)
            bs[i - begin] = src[i];
        return bs;
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
    private byte[] encrypt(byte[] data, byte[] key) throws Exception
    {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES1);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    public String byteArr2HexStr(byte[] arrB) throws Exception
    {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++)
        {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0)
            {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16)
            {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
    private byte[] decrypt(byte[] data, byte[] key) throws Exception
    {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES2);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }
}
