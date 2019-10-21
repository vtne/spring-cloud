package com.sdxm.service3rd.controller;

import com.github.pagehelper.PageInfo;
import com.sdxm.service3rd.entity.Dict;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import com.sdxm.service3rd.service.ThirdpartyServiceService;
import com.sdxm.service3rd.entity.ThirdpartyService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ThirdpartyServiceController {

    @Autowired
    ThirdpartyServiceService thirdpartyServiceService;

    @Cacheable("ThirdpartyService")
    @ApiOperation(value = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页的总条数", required = true, paramType = "query"),
    })
    @GetMapping("/query")
    public Object query(@RequestParam Integer page, @RequestParam Integer size, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<ThirdpartyService> list = thirdpartyServiceService.query(page, size, request);
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

    @Cacheable("ThirdpartyService")
    @ApiOperation(value = "查询类型")
    @GetMapping("/queryType")
    public Object queryType() {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Dict> list = thirdpartyServiceService.queryType();
            resultMap.put("data", list);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }
    @Cacheable("ThirdpartyService")
    @ApiOperation(value = "条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页的总条数", required = true, paramType = "query"),
    })
    @GetMapping("/query2")
    public Object query2(@RequestParam Integer page, @RequestParam Integer size, @RequestParam(required = false) String info,
                         @RequestParam(required = false) String status, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            PageInfo<ThirdpartyService> pageInfo = thirdpartyServiceService.query2(page, size, info, status, request);
            resultMap.put("data", pageInfo);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    @Cacheable("ThirdpartyService")
    @ApiOperation(value = "根据类型查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页的总条数", required = true, paramType = "query"),
    })
    @GetMapping("/queryByType")
    public Object queryByType(@RequestParam String type, @RequestParam Integer page, @RequestParam Integer size, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<ThirdpartyService> list = thirdpartyServiceService.queryByType(type, page, size, request);
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

    @Cacheable("ThirdpartyService")
    @ApiOperation(value = "根据id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "query"),
    })
    @GetMapping("/queryById")
    public Object queryById(@RequestParam Integer id, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<ThirdpartyService> list = thirdpartyServiceService.queryById(id, request);
            if(list.size()!=0){
                resultMap.put("data",list.get(0));
                resultMap.put("success", true);
            }
            //outMap.put("data",list); 错误 修改如下
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    @CachePut(cacheNames = "ThirdpartyService")
    @ApiOperation("新增")
    @PostMapping("/create")
    public Object create(@RequestBody ThirdpartyService thirdpartyService) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = thirdpartyServiceService.add(thirdpartyService);
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

    @CachePut(cacheNames = "ThirdpartyService")
    @ApiOperation("更新")
    @PostMapping("/update")
    public Object update(@RequestBody ThirdpartyService thirdpartyService) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // int isTure = thirdpartyServiceService.isThere(collectApp);
            //if (isTure == 0) { 同上查询是一个错误 传值参数应该是（thirdpartyService）而不是（collectApp）
            int success = thirdpartyServiceService.update(thirdpartyService);
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


    @CachePut(cacheNames = "ThirdpartyService")
    @ApiOperation("删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query"),
    })
    @GetMapping("/delete")
    public Object delete(@RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = thirdpartyServiceService.delete(id);
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