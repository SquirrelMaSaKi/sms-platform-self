package com.qianfeng.smsplatform.userinterface.servcie.Impl;

import com.qianfeng.smsplatform.common.constants.InterfaceExceptionDict;
import com.qianfeng.smsplatform.userinterface.feign.CacheServcie;
import com.qianfeng.smsplatform.userinterface.servcie.CheckService;
import com.qianfeng.smsplatform.userinterface.utils.MD5Utils;
import com.qianfeng.smsplatform.userinterface.utils.MyStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ---  2019/12/3 --- 11:57
 * --天神佑我：写代码，无BUG
 */
@Service
public class CheckServiceImpl implements CheckService {
    @Autowired
    private CacheServcie cacheServcie;
    @Override
    public String check( String remoteAddr,String clientID, String srcID, String mobile, String content, String pwd) {
        Map<String, Object> map = cacheServcie.hGet(clientID);
       /* Map<String, Object> map = new HashMap<>();
        map.put("pwd", "ISMvKXpXpadDiUoOSoAfww==");
        map.put("ipAddress", "127.0.0.1");*/
        //检查是否有ClientID

        if (MyStringUtils.isEmpty(clientID)) {
            return InterfaceExceptionDict.RETURN_STATUS_CLIENTID_ERROR;
        }
        if (map == null) {
            return InterfaceExceptionDict.RETURN_STATUS_CLIENTID_ERROR;
        }

        //检查传过来的mobile的大小
        if (!MyStringUtils.isEmpty(mobile)) {
            String[] mobiles = mobile.split(",");
            if (mobiles.length > 100) {
                return InterfaceExceptionDict.RETURN_STATUS_MOBILE_ERROR;
            } else {
                String maches = "^(1[3-9])\\d{9}$";
                for (String s : mobiles) {
                    boolean b = s.matches(maches);
                    if (!b) {
                        return InterfaceExceptionDict.RETURN_STATUS_MOBILE_ERROR;
                    }
                }
            }
        } else {
            return InterfaceExceptionDict.RETURN_STATUS_MOBILE_ERROR;
        }
        //检查传过来的内容大小
        if (MyStringUtils.isEmpty(content) || content.length() > 500) {
            return InterfaceExceptionDict.RETURN_STATUS_MESSAGE_ERROR;
        }
        //检查SRCID（要有）
        if (MyStringUtils.isEmpty(srcID)) {
            return InterfaceExceptionDict.RETURN_STATUS_SRCID_ERROR;

            //long srcID01 = Integer.parseInt(srcID);
        }
        //比对密码
        if (MyStringUtils.isEmpty(pwd)) {
            return InterfaceExceptionDict.RETURN_STATUS_PWD_ERROR;

        } else {
            String password = (String)map.get("pwd");
            String s = MD5Utils.toMD5(pwd);
            if (!password.equals(s)) {
                return InterfaceExceptionDict.RETURN_STATUS_PWD_ERROR;
            }

        }
        //匹配IP

        String ip = (String)map.get("ipAddress");
        if (ip == null) {
            System.err.println("Ipaddress.............................》》》》》》》》》》》》》》");
            return InterfaceExceptionDict.RETURN_STATUS_IP_ERROR;
        }
        if (!remoteAddr.equals(ip)) {
            return InterfaceExceptionDict.RETURN_STATUS_IP_ERROR;
        }
        return InterfaceExceptionDict.RETURN_STATUS_SUCCESS;
    }
}
