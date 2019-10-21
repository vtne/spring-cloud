package com.sdxm.information.controller;

import com.github.pagehelper.PageInfo;
import com.sdxm.information.entity.Comment;
import com.sdxm.information.entity.Information;
import com.sdxm.information.service.InformationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/information")
public class InformationController {

    @Autowired
    InformationService informationService;


    @ApiOperation(value = "查询上墙新闻")
    @GetMapping("/queryUpWall")
    public Object queryUpWall(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Information> list = informationService.queryUpWall(request);
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
     * 查询新闻评论 (后台管理系统接口)
     *
     * @return
     */
    @ApiOperation(value = "查询新闻评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页的总条数", required = true, paramType = "query"),
    })
    @GetMapping("/queryInfoComment")
    public Object queryInfoType(Integer audit, String param, Integer pageNum, Integer pageSize) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            PageInfo<Comment> list = informationService.queryInfoComment(audit, param, pageNum, pageSize);
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

    @ApiOperation(value = "点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "用户的openid", required = true, paramType = "query"),
            @ApiImplicitParam(name = "infoId", value = "新闻的id", required = true, paramType = "query"),
    })
    @GetMapping("/admire")
    public Object admire(@RequestParam String openId, @RequestParam Integer infoId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = informationService.admire(openId, infoId);
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

    @ApiOperation(value = "修改评论状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "评论的id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "audit", value = "评论", required = true, paramType = "query"),
    })
    @PostMapping("/updateInfoComment")
    public Object updateInfoComment(Comment comment) {
        Map<String, Object> resultMap = new HashMap<>();
        if (comment.getAudit() == null || comment.getId() == null) {
            resultMap.put("errmsg", "id 和 修改的状态不能为空!");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        try {
            int success = informationService.updateInfoComment(comment);
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


    @ApiOperation(value = "发表评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "用户的openId", required = true, paramType = "query"),
            @ApiImplicitParam(name = "infoId", value = "新闻的id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "comment", value = "评论", required = true, paramType = "query"),
    })
    @PostMapping("/infoComment")
    public Object infoComment(@RequestBody Comment comment) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = informationService.infoComment(comment);
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

    @ApiOperation(value = "删除评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "评论的id", required = true, paramType = "query"),
    })
    @GetMapping("/infoCommentDelete")
    public Object infoCommentDelete(@RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = informationService.infoCommentDelete(id);
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
        //return resultMap;
    }


    @Cacheable("Information")
    @ApiOperation(value = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页的总条数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "infoType", value = "新闻类型", required = false, paramType = "query"),
    })
    @GetMapping("/query")
    public Object query(@RequestParam String infoType, @RequestParam(value = "pageNum") Integer pageNum,
                        @RequestParam(value = "pageSize") Integer pageSize, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Information> list = informationService.query(infoType, pageNum, pageSize, request);
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

    @Cacheable("Information")
    @ApiOperation(value = "查询新闻 (后台管理接口)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页的总条数", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "status", value = "审核状态(0未发布1已发布2已下架)", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "infoType", value = "类型", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "info", value = "条件", required = false, paramType = "query", dataType = "String"),
    })
    @GetMapping("/queryInformation")
    public Object queryInformation(@RequestParam(value = "infoType", required = false) String infoType,
                                   @RequestParam(value = "pageNum") Integer pageNum,
                                   @RequestParam(value = "pageSize") Integer pageSize,
                                   @RequestParam(value = "info", required = false) String info,
                                   @RequestParam(value = "status", required = false) String status, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            PageInfo<Information> pageInfo = informationService.query(infoType, pageNum, pageSize, info, status, request);
            resultMap.put("data", pageInfo);
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
     * 评论和点赞数量
     *
     * @return
     */
    @Cacheable("Information")
    @ApiOperation(value = "查询评论和点赞数量")
    @GetMapping("/commentAndAdmireCount")
    public Object commentAndAdmireCount(@RequestParam Integer id, @RequestParam(defaultValue = "") String openId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            HashMap<String, Object> map = informationService.commentAndAdmireCount(id, openId);
            resultMap.put("data", map);
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
     * 查询新闻详情
     *
     * @param id
     * @return
     */
    @Cacheable("Information")
    @ApiOperation(value = "查询新闻详情 前端")
    @GetMapping("/detail")
    public Object detail(@RequestParam Integer id, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Information info = informationService.detail(id, request);
            if (info != null) {
                //修改浏览量
                info.setScanNum(info.getScanNum() + 1);
                //修改自定义浏览量
                if (info.getScanNumCustom() != null) {
                    info.setScanNumCustom(info.getScanNumCustom() + 1);
                }
                informationService.update(info);
                resultMap.put("data", info);
                resultMap.put("success", true);
            } else {
                resultMap.put("errmsg", "找不到该记录");
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
     * 查询新闻详情 (后台管理接口)
     *
     * @param id
     * @return
     */
    @Cacheable("Information")
    @ApiOperation(value = "查询新闻详情 (后台管理接口)")
    @GetMapping("/queryInfoById")
    public Object queryInfoById(@RequestParam Integer id, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Information info = informationService.detail(id, request);
            if (info != null) {
                resultMap.put("data", info);
                resultMap.put("success", true);
            } else {
                resultMap.put("errmsg", "找不到该记录");
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
    @Cacheable("Information")
    @ApiOperation(value = "搜索")
    @GetMapping("/search")
    public Object search(@RequestParam String text) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Information> list = informationService.search(text);
            if (list != null) {
                resultMap.put("data", list);
                resultMap.put("success", true);
            } else {
                resultMap.put("errmsg", "找不到该记录");
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

    @CachePut(cacheNames = "Information")
    @ApiOperation("新增")
    @PostMapping("/create")
    public Object create(@RequestBody Information information) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Date date = new Date();
            information.setCreateTime(date);
            information.setUpdateTime(date);
            int success = informationService.add(information);
            if (success > 0) {
                resultMap.put("success", true);
                resultMap.put("info", "新增成功");
                return resultMap;
            } else {
                resultMap.put("errmsg", "新增失败");
                resultMap.put("success", false);
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("errmsg", "新增失败");
            resultMap.put("success", false);
            return resultMap;
        }

    }

    @CachePut(cacheNames = "Information")
    @ApiOperation("更新")
    @PostMapping("/update")
    public Object update(@RequestBody Information information) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Information> informations = informationService.isThere(information);
            if (informations.size() == 0) {
                resultMap.put("errmsg", "此条信息不存在");
                resultMap.put("success", false);
                return resultMap;
            }
            information.setUpdateTime(new Date());
            int success = informationService.update(information);
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

    @CachePut(cacheNames = "Information")
    @ApiOperation("更新发布状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "新闻的id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "audit", value = "状态", required = true, paramType = "query", dataType = "int"),
    })
    @PostMapping("/updateStatus")
    public Object updateStatus(@RequestBody Information information) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Information> informations = informationService.isThere(information);
            if (informations.size() == 0) {
                resultMap.put("errmsg", "此条信息不存在");
                resultMap.put("success", false);
                return resultMap;
            }
            information.setUpdateTime(new Date());
            int success = informationService.updateStatus(information);
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


    @CachePut(cacheNames = "Information")
    @ApiOperation("删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query"),
    })
    @GetMapping("/delete")
    public Object delete(@RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = informationService.delete(id);
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