package com.sdxm.dictionary.controller;

import com.sdxm.dictionary.entity.Valuelist;
import com.sdxm.dictionary.service.ValuelistService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @date 2019-03-22
 * @describe 字典表空值器
 */
@RestController
@RequestMapping("/value")
public class ValuelistController {

    @Autowired
    private ValuelistService valuelistService;

    @Cacheable("Valuelist")
    @ApiOperation(value = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页的总条数", required = true, paramType = "query"),
    })
    @GetMapping("/query")
    public Object query(@RequestParam Integer page, @RequestParam Integer size) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Valuelist> list = valuelistService.query(page,size);
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

    @Cacheable("Valuelist")
    @ApiOperation(value = "根据code查询")
    @GetMapping("/queryByPCode")
    public Object queryByPCode(@RequestParam String code) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Valuelist> list = valuelistService.queryByPCode(code);
            if(list!=null&&list.size()>0){
                //outMap.put("data",list); 错误 修改如下
                resultMap.put("data",list);
                resultMap.put("success", true);
            }else{
                resultMap.put("errmsg", "找不到 paraent code对应的记录");
                resultMap.put("success", false);
            }

        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    @Cacheable("Valuelist")
    @ApiOperation(value = "根据code查询")
    @GetMapping("/queryByCode")
    public Object queryByCode(@RequestParam String code) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Valuelist value = valuelistService.queryByCode(code);
            if(value!=null){
                //outMap.put("data",list); 错误 修改如下
                resultMap.put("data",value);
                resultMap.put("success", true);
            }else{
                resultMap.put("errmsg", "找不到code对应的记录");
                resultMap.put("success", false);
            }

        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    @CachePut(cacheNames = "Valuelist")
    @ApiOperation("新增")
    @PostMapping("/create")
    public Object create(@RequestBody Valuelist valuelist) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = valuelistService.add(valuelist);
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

    @CachePut(cacheNames = "Valuelist")
    @ApiOperation("更新")
    @PostMapping("/update")
    public Object update(@RequestBody Valuelist valuelist) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // int isTure = valuelistService.isThere(collectApp);
            //if (isTure == 0) { 同上查询是一个错误 传值参数应该是（valuelist）而不是（collectApp）
            List<Valuelist>  valuelists = valuelistService.isThere(valuelist);
            if (valuelists.size() == 0) {
                resultMap.put("errmsg", "此条信息不存在");
                resultMap.put("success", false);
                return resultMap;
            }
            int success = valuelistService.update(valuelist);
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


    @CachePut(cacheNames = "Valuelist")
    @ApiOperation("删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query"),
    })
    @GetMapping("/delete")
    public Object delete(@RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = valuelistService.delete(id);
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