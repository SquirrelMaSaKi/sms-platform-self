package com.qianfeng.smsplatform.userinterface.utils;

/**
 * ---  2019/12/3 --- 10:18
 * --天神佑我：写代码，无BUG
 */
public class MyStringUtils {
    public static boolean isEmpty(String str){
        if(str==null || str.trim().length()==0){
            return true;
        }
        return false;
    }
}
