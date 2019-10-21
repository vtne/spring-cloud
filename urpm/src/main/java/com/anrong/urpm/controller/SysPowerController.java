package com.anrong.urpm.controller;

import com.anrong.urpm.service.SysPowerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import com.anrong.urpm.entity.SysPower;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/power")
public class SysPowerController {

    @Autowired //没有添加注入注解
            SysPowerService sysPowerService;// = new SysPowerService();

    @Cacheable("SysPower")
    @ApiOperation(value = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页的总条数", required = true, paramType = "query"),
    })
    @GetMapping("/query")
    public Object query(@RequestParam Integer page,@RequestParam Integer results) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<SysPower> list = sysPowerService.query(page,results);
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

    @CachePut(cacheNames = "SysPower")
    @ApiOperation("新增")
    @PostMapping("/create")
    public Object create(@RequestBody SysPower sysPower) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //int isTure = sysPowerService.isThere(sysPower);
            //if (isTure > 0) {  次查询返回的是一个集合而不是数字值 修改如下
            List<SysPower>  sysPowers = sysPowerService.isThere(sysPower);
            if (sysPowers.size() > 0) {
                resultMap.put("errmsg", "此条信息已经存在");
                resultMap.put("success", false);
                return resultMap;
            }
            int success = sysPowerService.add(sysPower);
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

    @CachePut(cacheNames = "SysPower")
    @ApiOperation("更新")
    @PostMapping("/update")
    public Object update(@RequestBody SysPower sysPower) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // int isTure = sysPowerService.isThere(collectApp);
            //if (isTure == 0) { 同上查询是一个错误 传值参数应该是（sysPower）而不是（collectApp）
            List<SysPower>  sysPowers = sysPowerService.isThere(sysPower);
            if (sysPowers.size() == 0) {
                resultMap.put("errmsg", "此条信息不存在");
                resultMap.put("success", false);
                return resultMap;
            }
            int success = sysPowerService.update(sysPower);
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


    @CachePut(cacheNames = "SysPower")
    @ApiOperation("删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query"),
    })
    @PostMapping("/delete")
    public Object delete(@RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = sysPowerService.delete(id);
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