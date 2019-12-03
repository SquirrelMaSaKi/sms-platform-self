package com.qianfeng.smsplatform.userinterface.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

/**
 * ---  2019/12/3 --- 11:41
 * --天神佑我：写代码，无BUG
 * MD5 工具类
 */
public class MD5Utils {
    public static String toMD5(String s) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            String newstr = base64en.encode(md5.digest(s.getBytes("utf-8")));
            return newstr;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
