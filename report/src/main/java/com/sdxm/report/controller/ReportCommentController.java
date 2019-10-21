package com.sdxm.report.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import com.sdxm.report.service.ReportCommentService;
import com.sdxm.report.entity.ReportComment;

import java.util.*;

@RestController
@RequestMapping("comment")
public class ReportCommentController {

    @Autowired //没有添加注入注解
    ReportCommentService reportCommentService;// = new ReportCommentService();

    @Cacheable("ReportComment")
    @ApiOperation(value = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页的总条数", required = true, paramType = "query"),
    })
    @GetMapping("/query")
    public Object query(@RequestParam Integer page,@RequestParam Integer size) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<ReportComment> list = reportCommentService.query(page,size);
            //outMap.put("data",list); 错误 修改如下
            resultMap.put("data",list);
            resultMap.put("success", true);
        } catch (Exception e) {
                resultMap.put("errmsg", "失败了");
                resultMap.put("errcode", "Error001");
                resultMap.put("success", false);
                return resultMap;
        }
        return resultMap;
    }

    @CachePut(cacheNames = "ReportComment")
    @ApiOperation("新增")
    @PostMapping("/create")
    public Object create(@RequestBody ReportComment reportComment) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //int isTure = reportCommentService.isThere(reportComment);
            //if (isTure > 0) {  次查询返回的是一个集合而不是数字值 修改如下
//            List<ReportComment>  reportComments = reportCommentService.isThere(reportComment);
//            if (reportComments.size() > 0) {
//                resultMap.put("errmsg", "此条信息已经存在");
//                resultMap.put("success", false);
//                return resultMap;
//            }
            reportComment.setCreateTime(new Date());
            int success = reportCommentService.add(reportComment);
            if (success > 0) {
                resultMap.put("success", true);
                resultMap.put("info", "新增成功");
                return resultMap;
            } else {
                resultMap.put("errmsg", "新增失败");
                resultMap.put("success", false);
                return resultMap;
            }
        }catch (Exception e){
            resultMap.put("errmsg", "新增失败");
            resultMap.put("success", false);
            return resultMap;
        }

    }

    @CachePut(cacheNames = "ReportComment")
    @ApiOperation("更新")
    @PostMapping("/update")
    public Object update(@RequestBody ReportComment reportComment) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // int isTure = reportCommentService.isThere(collectApp);
            //if (isTure == 0) { 同上查询是一个错误 传值参数应该是（reportComment）而不是（collectApp）
            List<ReportComment>  reportComments = reportCommentService.isThere(reportComment);
            if (reportComments.size() == 0) {
                resultMap.put("errmsg", "此条信息不存在");
                resultMap.put("success", false);
                return resultMap;
            }
            int success = reportCommentService.update(reportComment);
            if (success > 0) {
                resultMap.put("success", true);
                resultMap.put("info", "更新采集项成功");
                return resultMap;
            } else {
                resultMap.put("errmsg", "更新失败");
                resultMap.put("success", false);
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("success", false);
            return resultMap;
        }
    }


    @CachePut(cacheNames = "ReportComment")
    @ApiOperation("删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query"),
    })
    @GetMapping("/delete")
    public Object delete(@RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = reportCommentService.delete(id);
            if (success > 0) {
                resultMap.put("success", true);
                resultMap.put("info", "删除采集项成功");
                return resultMap;
            } else {
                resultMap.put("errmsg", "该采集项不存在或已删除");
                resultMap.put("success", false);
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("errmsg", "删除失败了");
            resultMap.put("success", false);
            return resultMap;
        }

    }
}