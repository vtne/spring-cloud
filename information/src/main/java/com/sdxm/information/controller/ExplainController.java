package com.sdxm.information.controller;

import com.sdxm.information.entity.Explain;
import com.sdxm.information.service.ExplainService;
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
@RequestMapping("/explain")
public class ExplainController {

    @Autowired
    private ExplainService explainService;

    @Cacheable("explain")
    @ApiOperation(value = "查询")
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页的总条数", required = true, paramType = "query"),
    })*/
    @GetMapping("/query")
    public Object query() {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Explain> list = explainService.query();
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

    @Cacheable("explain")
    @ApiOperation(value = "查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "热点解答的id", required = true, paramType = "query"),
    })
    @GetMapping("/queryById")
    public Object queryById(@RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Explain explain = explainService.queryById(id);
            resultMap.put("data", explain);
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
