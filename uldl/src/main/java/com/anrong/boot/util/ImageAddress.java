package com.anrong.boot.util;


import com.anrong.boot.conf.AddressUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @Auther: 高亚鹏
 * @Date: 2019/1/14 16:51
 * @Description:
 */
@Component
public class ImageAddress {
    @Autowired private AddressUrlConfig addressUrlConfig;
    private  static String addressOut;
    private static String addressIn;
    private static boolean fdfsOpen;
    private static List<String> address;


    public  List<String> getAddress() {
        return address;
    }

    public static   boolean getFdfsOpen() {
        return fdfsOpen;
    }
    @Value("${fdfs.prefix.strip}")
    public  void setFdfsOpen(boolean fdfsOpen) {
        ImageAddress.fdfsOpen = fdfsOpen;
        address = addressUrlConfig.getAddress();

    }

    public static String getAddressOut() {
        return addressOut;
    }
    @Value("${direct.url.address.out}")
    public  void setAddressOut(String addressOut) {
        ImageAddress.addressOut = addressOut;
    }

    public static String getAddressIn() {
        return addressIn;
    }
    @Value("${direct.url.address.in}")
    public  void setAddressIn(String addressIn) {
        ImageAddress.addressIn = addressIn;
    }
    public static String  getUrl(HttpServletRequest request){
        System.out.println(addressIn);
        if(request == null){
            return addressIn;
        }
        if(!getFdfsOpen()){
            return "";
        }
        String remoteUrl = getIpAddress(request);
        System.out.println(remoteUrl);
        String url = null;
        try {
            url = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Integer reIndex = remoteUrl.indexOf(".");
        if(reIndex < 0 ){
            return addressOut;
        }
        String reStart = remoteUrl.substring(0,reIndex);
        if(address.indexOf(reStart) >= 0){
            return addressIn;
        }else{
            return addressOut;
        }
    }


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            System.out.println("first:" + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            System.out.println("second:" + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            System.out.println("third:" + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            System.out.println("forth:" + ip);
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("X-Client-Address");
            System.out.println("five:" + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    public static String extractString(String s) {

        for (int i = 0; i < 3; i++) {
            s = s.substring(s.indexOf("/") + 1);
        }

        return s;
    }
}
