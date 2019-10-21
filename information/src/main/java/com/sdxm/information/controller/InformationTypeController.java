package com.sdxm.information.controller;

import com.github.pagehelper.PageInfo;
import com.sdxm.information.entity.InformationType;
import com.sdxm.information.service.InformationTypeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/informationType")
public class InformationTypeController {

    @Autowired
    private InformationTypeService informationTypeService;

    /**
     * 查询新闻类型
     *
     * @return
     */
    @ApiOperation(value = "查询新闻类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页数", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页的总条数", required = false, paramType = "query"),
    })
    @GetMapping("/queryInfoType")
    public Object queryInfoType(Integer pageNum, Integer pageSize) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            PageInfo<InformationType> list = informationTypeService.queryInfoType(pageNum, pageSize);
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

    /**
     * 查询新闻类型
     *
     * @return
     */
    @ApiOperation(value = "查询一条新闻类型")
    @PostMapping("/queryInfoTypeById")
    public Object queryInfoTypeById(InformationType informationType) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            InformationType infor = informationTypeService.queryInfoTypeById(informationType);
            resultMap.put("data", infor);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    /**
     * 修改新闻类型
     *
     * @return
     */
    @ApiOperation(value = "修改新闻类型")
    @PostMapping("/updateInfoType")
    public Object updateInfoType(InformationType informationType) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int i = informationTypeService.updateInfoType(informationType);
            if (i > 0) {
                resultMap.put("message", "更新成功");
                resultMap.put("success", true);
            } else {
                resultMap.put("message", "更新失败");
                resultMap.put("success", false);
            }

        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    /**
     * 删除新闻类型
     *
     * @return
     */
    @ApiOperation(value = "删除新闻类型")
    @PostMapping("/deleteById")
    public Object deleteById(@RequestParam(value = "id") Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int i = informationTypeService.deleteById(id);
            if (i > 0) {
                resultMap.put("message", "删除成功");
                resultMap.put("success", true);
            } else if (i == 0) {
                resultMap.put("message", "此类型下有绑定的新闻!!");
                resultMap.put("success", false);
            } else {
                resultMap.put("message", "删除失败");
                resultMap.put("success", false);
            }
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    /**
     * 新建新闻类型
     *
     * @return
     */
    @ApiOperation(value = "新建新闻类型")
    @PostMapping("/createInforType")
    public Object createInforType(InformationType informationType) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int i = informationTypeService.createInforType(informationType);
            if (i > 0) {
                resultMap.put("message", "新建成功");
                resultMap.put("success", true);
            } else {
                resultMap.put("message", "新建失败");
                resultMap.put("success", false);
            }
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }
}
