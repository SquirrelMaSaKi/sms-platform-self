package com.qianfeng.smsplatform.userinterface.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ---  2019/12/3 --- 11:41
 * --天神佑我：写代码，无BUG
 * MD5 工具类
 */
public class MD5Utils {
    public static String toMD5(String s) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = s.getBytes();
            messageDigest.update(bytes);
            byte[] digest = messageDigest.digest();
            String string = new String(digest);
            return string;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
