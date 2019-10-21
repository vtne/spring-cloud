package com.anrong.boot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;


public class SystemUtil {


    private static Logger LOG = LoggerFactory.getLogger(SystemUtil.class);


    /**
     * 返回本机IP的字符串形式
     *
     * @return
     * @throws Exception
     */
    public static String getLocalIp() throws Exception {
        InetAddress inet;


        inet = getSystemLocalIP();
        if (null != inet) {
            String ip = inet.getHostAddress();
            LOG.info("获取的本机IP为{}", ip);
            return ip;
        }
        throw new Exception("获取ip失败");
    }


    /**
     * 获取本机ip的InetAddress形式
     *
     * @return
     * @throws Exception
     */
    private static InetAddress getSystemLocalIP() throws Exception {


        InetAddress inet = null;
        String osName = getSystemOsName();
        /*if ("Windows".compareToIgnoreCase(osName) < 0) {
            inet = getWinLocalIp();
        } else {
            inet = getUnixLocalIp();
        }*/
        if (osName.toLowerCase().indexOf("windows") > -1) {
            inet = getWinLocalIp();
        } else {
            inet = getUnixLocalIp();
        }
        return inet;
    }


    /**
     * 获取类Unix系统的IP
     *
     * @return
     * @throws Exception
     */
    private static InetAddress getUnixLocalIp() throws Exception {
        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        if (null == netInterfaces) {
            throw new Exception("获取类Unix系统的IP失败");
        }
        InetAddress ip = null;
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = netInterfaces.nextElement();
            if (ni.isUp()) {
                Enumeration<InetAddress> addressEnumeration = ni.getInetAddresses();
                while (addressEnumeration.hasMoreElements()) {
                    ip = addressEnumeration.nextElement();
                    if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                        return ip;
                    }
                }
            }
        }
        throw new Exception("获取类Unix系统的IP失败");
    }


    /**
     * 获取window系统的ip，貌似不会返回null
     *
     * @return
     * @throws UnknownHostException
     */
    private static InetAddress getWinLocalIp() throws UnknownHostException {
        InetAddress inet = InetAddress.getLocalHost();
        return inet;
    }


    /**
     * 获取操作系统名称
     *
     * @return
     */
    private static String getSystemOsName() {
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name");
        return osName;
    }


    /**
     * 获取内存信息
     *
     * @return
     */
    public static final String getRamInfo() {
        Runtime rt = Runtime.getRuntime();
        return "RAM:" + rt.totalMemory() / 1024 / 1024 + "MB total," + rt.freeMemory() / 1024 / 1024 + "MB free.";
    }


    public static void main(String[] args) {
        try {
            getLocalIp();
        } catch (Exception e) {
            LOG.error("failed");
        }
    }
}

