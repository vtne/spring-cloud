package com.anrong.urpm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anrong.urpm.service.SysUserService;
import com.anrong.urpm.util.HttpUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import com.anrong.urpm.entity.SysUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@RestController
@RequestMapping("/user")
public class SysUserController {

    @Value("${weixin.appId}")
    String _appId = "";

    @Value("${weixin.secret}")
    String _secret = "";

    @Autowired //没有添加注入注解
            SysUserService sysUserService;// = new SysUserService();

    @Cacheable("SysUser")
    @ApiOperation(value = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页的总条数", required = true, paramType = "query"),
    })
    @GetMapping("/query")
    public Object query(@RequestParam Integer page,@RequestParam Integer results) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<SysUser> list = sysUserService.query(page,results);
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

    @CachePut(cacheNames = "SysUser")
    @ApiOperation("新增")
    @PostMapping("/create")
    public Object create(@RequestBody SysUser sysUser) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //int isTure = sysUserService.isThere(sysUser);
            //if (isTure > 0) {  次查询返回的是一个集合而不是数字值 修改如下
            List<SysUser>  sysUsers = sysUserService.isThere(sysUser);
            if (sysUsers.size() > 0) {
                resultMap.put("errmsg", "此条信息已经存在");
                resultMap.put("success", false);
                return resultMap;
            }
            int success = sysUserService.add(sysUser);
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

    @CachePut(cacheNames = "SysUser")
    @ApiOperation("更新")
    @PostMapping("/update")
    public Object update(@RequestBody SysUser sysUser) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // int isTure = sysUserService.isThere(collectApp);
            //if (isTure == 0) { 同上查询是一个错误 传值参数应该是（sysUser）而不是（collectApp）
            List<SysUser>  sysUsers = sysUserService.isThere(sysUser);
            if (sysUsers.size() == 0) {
                resultMap.put("errmsg", "此条信息不存在");
                resultMap.put("success", false);
                return resultMap;
            }
            int success = sysUserService.update(sysUser);
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


    @CachePut(cacheNames = "SysUser")
    @ApiOperation("删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query"),
    })
    @PostMapping("/delete")
    public Object delete(@RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = sysUserService.delete(id);
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

    @GetMapping("get_openId_sessionKey_unionId")
    public Object get_openId_sessionKey_unionId(String code) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);

        try {
            String rtn = HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session?appid=" + _appId + "&secret=" + _secret + "&js_code=" + code + "&grant_type=authorization_code");
            JSONObject jsonObject = JSON.parseObject(rtn);
            map.put("success", true);
            map.put("data", jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}