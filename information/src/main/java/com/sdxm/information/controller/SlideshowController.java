package com.sdxm.information.controller;

import com.github.pagehelper.PageInfo;
import com.sdxm.information.entity.Slideshow;
import com.sdxm.information.service.ShowLocationService;
import com.sdxm.information.service.SlideshownService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/slideshown")
public class SlideshowController {

    @Autowired
    private SlideshownService slideshownService;

    @Autowired
    private ShowLocationService showLocationService;

    /**
     * 微信端的接口
     *
     * @return
     */
    @ApiOperation(value = "微信端查询轮播图的接口")
    @GetMapping("/querySlideshow")
    public Object querySlideshow(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List list = slideshownService.querySlideshow(request);
            resultMap.put("data", list);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }


    @ApiOperation(value = "查询展示位置(后台管理)")
    @GetMapping("/queryLocation")
    public Object queryLocation() {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List list = showLocationService.queryLocation();
            resultMap.put("data", list);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }


    @ApiOperation(value = "查询轮播图管理列表(后台管理)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页的总条数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "param", value = "条件", required = false, paramType = "query"),
            @ApiImplicitParam(name = "audit", value = "状态", required = false, paramType = "query"),
            @ApiImplicitParam(name = "location", value = "位置", required = false, paramType = "query"),
    })
    @GetMapping("/SlideShownList")
    public Object SlideShownList(HttpServletRequest request, @RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                 @RequestParam(required = false) String param, @RequestParam(required = false) Integer audit
            , @RequestParam(required = false) Integer location) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            PageInfo<Slideshow> list = slideshownService.querySlideShownList(request, pageNum, pageSize, param, audit, location);
            resultMap.put("data", list);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    @ApiOperation(value = "新建")
    @PostMapping("/add")
    public Object add(@RequestBody Slideshow slideshow) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = slideshownService.add(slideshow);
            if (success > 0) {
                resultMap.put("success", true);
                resultMap.put("info", "操作成功!");
                return resultMap;
            } else {
                resultMap.put("errmsg", "操作失败!");
                resultMap.put("success", false);
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
    }

    @ApiOperation(value = "修改")
    @PostMapping("/update")
    public Object update(@RequestBody Slideshow slideshow) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = slideshownService.update(slideshow);
            if (success > 0) {
                resultMap.put("success", true);
                resultMap.put("info", "操作成功!");
                return resultMap;
            } else {
                resultMap.put("errmsg", "操作失败!");
                resultMap.put("success", false);
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
    }

    @ApiOperation(value = "删除")
    @GetMapping("/delete")
    public Object delete(@RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = slideshownService.delete(id);
            if (success > 0) {
                resultMap.put("success", true);
                resultMap.put("info", "操作成功!");
                return resultMap;
            } else {
                resultMap.put("errmsg", "操作失败!");
                resultMap.put("success", false);
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
    }
}
