package com.qianfeng.smsplatform.userinterface.servcie;

/**
 * ---  2019/12/3 --- 11:45
 * --天神佑我：写代码，无BUG
 */
public interface CheckService {
    String check(String remoteAddr,String clientID, String srcID, String mobile, String content, String pwd);
}
