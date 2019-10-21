package com.anrong.boot.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 图片上传的工具类
 *
 * @author liuxun
 */
public class FileUploadUtils {

    // 得到上传文件真实名称 c:\a.txt a.txt
    // 如果参数为c:\a.txt 得到a.txt 如果为a.txt index=-1+1=0; 仍然正确
    private static String getRealName(String filename) {
        int index = filename.lastIndexOf("\\") + 1;
        return filename.substring(index);
    }

    // 获取随机名称
    private static String getUUIDFileName(String filename) {
        int index = filename.lastIndexOf(".");
        if (index != -1) {
            return UUIDUtil.getUUID() + filename.substring(index);
        }
        else {
            return UUIDUtil.getUUID();
        }

    }

    // 目录分离算法
    private static String getRandomDirectory(String filename) {
        int hashcode = filename.hashCode();
        //System.out.println(hashcode);

        //int数据类型在内存中占32位字节,转换成16进制数(4位)，就得到8个16进制数
        String hex = Integer.toHexString(hashcode);
        //System.out.println(hex);
        return "/" + hex.charAt(0) + "/" + hex.charAt(1);

    }

    public static String uploadAndGetPath(MultipartFile file, String imgGlobalDir, String modelDir) {
        // 得到文件真实名称
        String filename = file.getOriginalFilename();

        // 得到随机名称
        String uuidname = FileUploadUtils.getUUIDFileName(filename);

        // 得到随机目录
        if(modelDir==null){
            modelDir = FileUploadUtils.getRandomDirectory(filename);
        }

        // 注意：随机目录可能不存在,需要创建
        File imgDir = new File(imgGlobalDir, modelDir);
        File imgFile = new File(imgDir, uuidname);
        //File
        if (!imgDir.exists()) {
            imgDir.mkdirs();
        }

        try {
            file.transferTo(imgFile);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String path = "/imgs/"+imgFile.getPath().substring(imgGlobalDir.length());
        return path;
    }

}

