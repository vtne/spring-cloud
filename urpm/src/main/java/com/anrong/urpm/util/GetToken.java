package com.anrong.urpm.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import static java.lang.Thread.sleep;



public class GetToken{

    public static String access_tocken;

    public static void get(String appId,String secret) throws Exception {
        String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        String request_url = TOKEN_URL.replace("APPID", appId).replace("APPSECRET", secret);
        String result = HttpUtil.get(request_url);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(result);
        if(null != node){
            access_tocken = node.get("access_token").asText();
            String expires_in = node.get("expires_in").toString();
            //获取到的access_tocken值可以写入到文本文件中供其他业务逻辑调用，实例中只打印了没有保存到文件
            System.out.println("获取成功"+"access_tocken="+access_tocken+"||expires_in="+expires_in);
        }
    }
}