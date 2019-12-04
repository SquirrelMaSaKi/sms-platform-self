package com.qianfeng.smsplatform.userinterface.servlet;

import com.qianfeng.smsplatform.common.constants.InterfaceExceptionDict;
import com.qianfeng.smsplatform.common.constants.RabbitMqConsants;
import com.qianfeng.smsplatform.common.model.Standard_Submit;
import com.qianfeng.smsplatform.userinterface.servcie.CheckService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;

/**
 * ---  2019/12/3 --- 10:06
 * --天神佑我：写代码，无BUG
 */
@WebServlet(name = "smsInterfaceServlet",urlPatterns = "/smsInterface")
public class SmsInterfaceServlet extends HttpServlet {
    @Autowired
    private CheckService checkService;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String clientID = request.getParameter("clientID");
        String srcID = request.getParameter("srcID");
        String mobile = request.getParameter("mobile");
        String content = request.getParameter("content");
        String pwd = request.getParameter("pwd");
        String remoteAddr = getRemoteAddr(request);
        System.err.println(remoteAddr);

        String code = checkService.check(remoteAddr,clientID, srcID, mobile, content, pwd);
        if (code.equals(InterfaceExceptionDict.RETURN_STATUS_SUCCESS)) {
            String[] split = mobile.split(",");
            for (String s : split) {
                Standard_Submit standard_submit = new Standard_Submit();
                //1 代表是http发送方式
                standard_submit.setSource(1);

                standard_submit.setClientID(Integer.parseInt(clientID));
                standard_submit.setDestMobile(s);
                amqpTemplate.convertAndSend(RabbitMqConsants.TOPIC_PRE_SEND, standard_submit);

                System.err.println("发送成功");
            }

            System.err.println("code>>>>>>>>>" + code);
        }

        response.getWriter().write(code);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    //获取请求的IP地址
    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = "";
        try {
            ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                    try {
                        InetAddress inet = InetAddress.getLocalHost();
                        ip = inet.getHostAddress();
                    } catch (Exception e) {

                    }
                }
            }
            return ip.split(",")[0];
        } catch (Exception e) {
        }
        return ip;
    }
}
