package com.sdxm.uldl.controller;

import com.anrong.boot.conf.Global;
import com.anrong.boot.util.FileUploadUtils;
import com.anrong.boot.util.ImageAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import system.fastdfs.starter.processors.FdfsClientWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/image")
public class FileUploadController {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private FdfsClientWrapper fdfsClientWrapper;

    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    @RequestMapping(value = "/toUpload",method = {RequestMethod.POST})
    public String toUploadPage(Model model) {
        return "test";
    }

    @RequestMapping(value = "/multiUpload", method = {RequestMethod.POST})
    @ResponseBody
    public Object upload(HttpServletRequest request) throws Exception {
        String modelName = (String)request.getParameter("model");
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        List<String> paths = new ArrayList<>();
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                String filename = file.getOriginalFilename();
                log.info("=======originName:{}=============", filename);
                paths.add(FileUploadUtils.uploadAndGetPath(file,Global.imageDirectory,modelName));
            }
            else {
                return "You failed to upload " + i + " because the file was empty.";
            }
        }
        return paths;
    }

    /**
     * 单文件上传 真正使用
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/singleUpload", method = {RequestMethod.POST})
    @ResponseBody
    public Object singleUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //String modelName = (String)request.getParameter("model");
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        String path = null;
        /*if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            log.info("=======originName:{}=============", filename);
            path = FileUploadUtils.uploadAndGetPath(file,Global.imageDirectory,modelName);
            if (path == null) {
                response.setStatus(500);
            }
        }
        else {
            response.setStatus(400); // 没有上传图片
        }
        return path;*/
        if (file != null) {// 判断上传的文件是否为空
            try {
                String s = fdfsClientWrapper.uploadFile(file);
                return ImageAddress.getUrl(request) + s;
            } catch (Exception e) {
                response.setStatus(500);
            }
        } else {
            response.setStatus(500);
        }
        return "";
    }

}
