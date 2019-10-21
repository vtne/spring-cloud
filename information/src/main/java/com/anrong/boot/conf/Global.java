package com.anrong.boot.conf;

/**
 * 存放 静态全局变量，后期可以存放在资源文件中
 * 
 * @author liuxun
 *
 */
public class Global {
	public static String imageDirectory = "/Users/alansze/Downloads/imgs/";
	public static Integer pageSize = 1000; // 分页数据每页的条数
	public static Integer audit0 = 0; //未审核
    public static Integer audit1 = 1; //评论审核通过
}
