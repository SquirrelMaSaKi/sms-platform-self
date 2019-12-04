package com.qianfeng.smsplatform.userinterface.utils;

import com.qianfeng.smsplatform.common.model.Standard_Report;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * ---  2019/12/4 --- 10:57
 * --天神佑我：写代码，无BUG
 */

public class HttpClientUtils {
    public static void doGetWay(Standard_Report pushMessage){
        //获得Http客户端（相当于浏览器）
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        //参数的拼接
        StringBuffer params = new StringBuffer();
        params.append("srcID=" + pushMessage.getSrcID());
        params.append("&mobile=" + pushMessage.getMobile());
        params.append("&state=" + pushMessage.getState());
        params.append("&errorCode=" + pushMessage.getErrorCode());

        //创建Get请求
        HttpGet httpGet = new HttpGet("http:10.9.21.230:8080/smsInterface?" + params);
        //响应模型
        CloseableHttpResponse response = null;
        //配置信息
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .setSocketTimeout(60000)
                .setRedirectsEnabled(true)
                .build();
        //
        httpGet.setConfig(requestConfig);


        try {
            //
            response = httpClient.execute(httpGet);

            //
            HttpEntity responseEntity = response.getEntity();
            System.err.println("响应状态为》》》》  " + response.getStatusLine());
            if (responseEntity != null) {
                System.err.println("响应内容的长度为：" + responseEntity.getContentLength());
                System.err.println("响应内容为：" + EntityUtils.toString(responseEntity));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
