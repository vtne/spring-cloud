package com.sdxm.information.controller;

import com.sdxm.information.entity.Help;
import com.sdxm.information.service.HelpService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/help")
public class HelpController {

    @Autowired
    private HelpService helpService;

    @ApiOperation(value = "添加问题或建议")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "suggest", value = "问题或者建议", required = true, paramType = "query"),
            @ApiImplicitParam(name = "paths", value = "图片地址", required = false, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "手机号", required = false, paramType = "query"),
            @ApiImplicitParam(name = "openId", value = "openId", required = true, paramType = "query"),
    })
    @PostMapping("/create")
    public Object create(@RequestBody Help help) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int i = helpService.create(help);
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

    @ApiOperation(value = "查询反馈和回复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "用户的openId", required = true, paramType = "query"),
    })
    @GetMapping("/queryReply")
    public Object queryReply(@RequestParam String openId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Help> list = helpService.queryReply(openId);
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
}
