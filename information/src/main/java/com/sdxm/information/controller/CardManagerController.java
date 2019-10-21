package com.sdxm.information.controller;

import com.sdxm.information.entity.Manager;
import com.sdxm.information.service.ManagerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manager")
public class CardManagerController {

    @Autowired
    private ManagerService managerService;

    //@Cacheable("help")
    @ApiOperation(value = "添加问题或建议")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别 1男2女", required = false, paramType = "query"),
            @ApiImplicitParam(name = "number", value = "证件号", required = false, paramType = "query"),
            @ApiImplicitParam(name = "organ", value = "发证机关", required = false, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query"),
            @ApiImplicitParam(name = "credentialName", value = "卡名称", required = false, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型1身份证2驾驶证3其他", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pic1Path", value = "正面", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pic2Path", value = "反面", required = false, paramType = "query"),
            @ApiImplicitParam(name = "openId", value = "openid", required = true, paramType = "query"),
    })

    @PostMapping("/create")
    public Object create(@RequestBody Manager manager) {
        System.out.println("*------------------------------------------      " + manager);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int i = managerService.create(manager);
            if (i > 0) {
                resultMap.put("success", true);
                resultMap.put("info", "提交成功");
            } else {
                resultMap.put("success", false);
                resultMap.put("errorMsg", "失败");
            }
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    @GetMapping("/queryByUserId")
    public Object create(@RequestParam String openId, HttpServletRequest request) {
        System.out.println("************************************************openId         " + openId);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Manager> list = managerService.query(openId,request);
            resultMap.put("success", true);
            resultMap.put("data", list);
            resultMap.put("info", "查新成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("errmsg", "失败了");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    @GetMapping("/delete")
    public Object delete(@RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = managerService.delete(id);
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
