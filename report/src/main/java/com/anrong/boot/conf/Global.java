package com.anrong.boot.conf;

/**
 * 存放 静态全局变量，后期可以存放在资源文件中
 * 
 * @author liuxun
 *
 */
public class Global {
	public static String imageDirectory;
    /**
     * 分页数据每页的条数
     */
    public static Integer pageSize = 1000;
    /**
     * 随手拍
     */
    public static String ssp = "ssp";
    /**
     * 诉求
     */
    public static String sq = "sq";

    /**
     * 诉求类型
     */
    public static String dict_sq = "sq";
    /**
     * 处理状态
     */
    public static String dict_status = "sta";

    /**
     * 已上报的状态
     */
    public static String sta_0 = "sta_0";

}
