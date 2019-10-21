package com.sdxm.report.controller;

import com.anrong.boot.conf.Global;
import com.anrong.boot.util.ImageAddress;
import com.anrong.boot.util.JacksonUtil;
import com.anrong.boot.util.SystemUtil;
import com.github.pagehelper.PageInfo;
import com.sdxm.report.dao.SysEmployeeMapper;
import com.sdxm.report.entity.*;
import com.sdxm.report.feign.EmployeeFeignClient;
import com.sdxm.report.service.*;
import com.sdxm.report.vo.EventToReceive;
import com.sdxm.report.vo.ReportCommentExt;
import com.sdxm.report.vo.ReportExt;
import com.sdxm.report.vo.ReportToJB;
import heartbeat.monitor.starter.domain.msgs.WeChatSendAppeal;
import heartbeat.monitor.starter.domain.msgs.WeChatSendReport;
import heartbeat.monitor.starter.processors.business.MonitorWeChatSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import projects.rabbitmq.starter.domain.ProjectsFlags;
import projects.rabbitmq.starter.processors.WeChatSender;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Api(description = "事件控制器")
@RestController
@ConditionalOnProperty(prefix = "projects.system", value = "flag", havingValue = ProjectsFlags.WECHAT_FLAG)
public class ReportController {

    @Autowired
    ReportService reportService;
    @Autowired
    ReportStatusService reportStatusService;
    @Autowired
    ReportCommentService reportCommentService;
    @Autowired
    ReportAttachmentService reportAttachmentService;
    @Autowired
    EmployeeFeignClient employeeFeignClient;
    @Autowired
    SysEmployeeMapper sysEmployeeMapper;

    @Autowired
    private WeChatSender weChatSender;

    @Autowired
    private MonitorWeChatSender monitorWeChatSender;

    @Autowired
    private DictService dictService;

    /**
     * 查询事件 和 诉求的数量
     *
     * @return
     */
    @ApiOperation("查询事件 和 诉求的数量")
    @GetMapping("/queryEventSum")
    public Object queryEventSum() {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Map<String, Long> map = reportService.queryEventSum();
            resultMap.put("data", map);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    /**
     * 查询诉求类型
     *
     * @return
     */
    @ApiOperation("查询诉求类型")
    @GetMapping("/queryType")
    public Object queryType() {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Dict> list = dictService.queryType(Global.dict_sq);
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

    /**
     * 查询处理状态
     *
     * @return
     */
    @ApiOperation("查询处理状态")
    @GetMapping("/queryStatus")
    public Object queryStatus() {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Dict> list = dictService.queryType(Global.dict_status);
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

    @Cacheable("Report")
    @ApiOperation(value = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页的总条数", required = true, paramType = "query"),
    })
    @GetMapping("/query")
    public Object query(@RequestParam Integer page, @RequestParam Integer size) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Report> list = reportService.query(page, size);
            //outMap.put("data",list); 错误 修改如下
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

    @Cacheable("Report")
    @ApiOperation(value = "查询ALL")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页的总条数", required = true, paramType = "query"),
    })
    @GetMapping("/queryAll")
    public Object queryAll(@RequestParam Integer page, @RequestParam Integer size) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<ReportExt> list = reportService.queryAll(page, size);
            resultMap.put("data", list);
            resultMap.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    @Cacheable("Report")
    @ApiOperation(value = "查询上报列表,诉求列表,续报列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页的总条数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "info", value = "条件", required = false, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "区分事件还是诉求", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pid", value = "首报的id", required = false, paramType = "query"),
    })
    @GetMapping("/queryEventList")
    public Object queryEvent(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(required = false) Integer pid,
                             @RequestParam(required = false) String info, @RequestParam(required = false) String type, @RequestParam(required = false) String status) {
        Map<String, Object> resultMap = new HashMap<>();
        if (type == null || "".equals(type)) {
            resultMap.put("errmsg", "类型不能为空");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        try {
            PageInfo<ReportExt> list = reportService.queryAll(pageNum, pageSize, info, type, status, pid);
            resultMap.put("data", list);
            resultMap.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    @Cacheable("Report")
    @ApiOperation(value = "查询通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页的总条数", required = true, paramType = "query"),
    })
    @GetMapping("/queryMessage")
    public Object queryMessage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            PageInfo<ReportExt> list = reportService.queryMessage(pageNum, pageSize);
            resultMap.put("data", list);
            resultMap.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    @CachePut(cacheNames = "Report")
    @ApiOperation("上报/补报")
    @PostMapping("/create")
    public Object create(@RequestBody ReportExt report) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            report.setCreateTime(new Date());
            if (report.getParentId() == null) {
                report.setParentId(0);
            }
            int success = reportService.add(report);
            if (success > 0) {
                //发送report到接报
                ReportToJB jb = report.toJB();
                Map map = (Map) employeeFeignClient.queryEmployeeById(report.getEmployeeId());
                if ((Boolean) map.get("success")) {
                    Map employ = (Map) map.get("data");
                    String name = (String) employ.get("name");
                    String mobile = (String) employ.get("mobile");
                    if (mobile == null || "".equals(mobile) || !mobile.equals(report.getMobile())) {
                        mobile = report.getMobile();
                    }
                    jb.setUserName(name);
                    jb.setUserMobile(mobile);
                }

                List<String> pictures = new ArrayList<>();
                List<ReportAttachment> reportAttachments = reportAttachmentService.queryReportId(report.getId());
                for (ReportAttachment reportAttachment : reportAttachments) {
                    pictures.add(reportAttachment.getUrl());
                }
                jb.setPictures(pictures);
                jb.setEventId("WX" + System.currentTimeMillis() + "_" + jb.getId());
                EventToReceive eventToReceive = new EventToReceive();
                eventToReceive.setMessageId(UUID.randomUUID().toString());
                eventToReceive.setMessage(jb);
                Boolean aBoolean = weChatSender.sendWeChatEvent(JacksonUtil.toJSon(eventToReceive), EventToReceive.class);
                //给监控发送诉求
                if ("sq".equals(report.getType())) {
                    WeChatSendAppeal appeal = new WeChatSendAppeal();
                    appeal.setSendTime(report.getCreateTime());
                    appeal.setSuccess(aBoolean);
                    appeal.setAppealId(jb.getEventId());
                    appeal.setSendIp(SystemUtil.getLocalIp());
                    appeal.setMessageId(eventToReceive.getMessageId());
                    appeal.setSendFlag(eventToReceive.getSendFlag());
                    monitorWeChatSender.sendSendedAppeal(appeal);
                } else {
                    //发送监控消息
                    WeChatSendReport sendReport = new WeChatSendReport();
                    sendReport.setSendTime(report.getCreateTime());
                    sendReport.setTaskId(jb.getEventId());
                    sendReport.setSuccess(aBoolean);
                    sendReport.setSendIp(SystemUtil.getLocalIp());
                    sendReport.setSendFlag(eventToReceive.getSendFlag());
                    sendReport.setMessageId(eventToReceive.getMessageId());
                    monitorWeChatSender.sendSendedReport(sendReport);
                }


                resultMap.put("success", true);
                resultMap.put("info", "新增成功");
                return resultMap;
            } else {
                resultMap.put("errmsg", "新增失败");
                resultMap.put("success", false);
                return resultMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("errmsg", "新增失败");
            resultMap.put("success", false);
            return resultMap;
        }

    }

    @CachePut(cacheNames = "Report")
    @ApiOperation("更新")
    @PostMapping("/update")
    public Object update(@RequestBody Report report) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Report> reports = reportService.isThere(report);
            if (reports.size() == 0) {
                resultMap.put("errmsg", "此条信息不存在");
                resultMap.put("success", false);
                return resultMap;
            }
            int success = reportService.update(report);
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


    @CachePut(cacheNames = "Report")
    @ApiOperation("删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query"),
    })
    @GetMapping("/delete")
    public Object delete(@RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = reportService.delete(id);
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

    /**
     * 手机端 我的上报 详情
     *
     * @param reportId
     * @param request
     * @return
     */
    @GetMapping("/detail")
    public Object detail(@RequestParam Integer reportId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Map<String, Object> data = new HashMap<>();
            List<ReportExt> rlist = reportService.queryDetail(reportId,request);
            List<ReportStatus> rslist = reportStatusService.queryByReportId(reportId);
            List<ReportCommentExt> rclist = reportCommentService.queryByReportId(reportId);
            ArrayList<HashMap> hashMaps = new ArrayList<>();
            if (rslist != null && rslist.size() > 0) {
                List<Dict> dicts = dictService.queryType(Global.dict_status);
                    if (dicts != null && dicts.size() > 0) {
                        for (ReportStatus reportStatus : rslist) {
                            for (Dict datum : dicts) {
                                if (datum.getCode().equals(reportStatus.getCode())) {
                                    HashMap<Object, Object> hashMap = new HashMap<>();
                                    hashMap.put("desc", datum.getRemark());
                                    hashMap.put("ReportStatus", reportStatus);
                                    hashMaps.add(hashMap);
                                    break;
                                }
                            }
                        }
                    }

            }
            data.put("report", rlist);
            data.put("status", hashMaps);
            data.put("comment", rclist);
            resultMap.put("data", data);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    @GetMapping("/detailById")
    public Object detailById(@RequestParam Integer reportId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Map<String, Object> data = new HashMap<>();
            List<ReportExt> rlist = reportService.queryDetail(reportId,request);
            List<ReportStatus> rslist = reportStatusService.queryByReportId(reportId);
            List<ReportCommentExt> rclist = reportCommentService.queryByReportId(reportId);
            List<ReportAttachment> ralist = reportAttachmentService.queryReportId(reportId);
            //判断内外网拼接图片
            if (ralist != null){
                for (ReportAttachment reportAttachment : ralist) {
                    reportAttachment.setUrl(ImageAddress.getUrl(request) + reportAttachment.getUrl());
                }
            }
            for (ReportExt reportExt : rlist) {
                SysEmployee sysEmployee = sysEmployeeMapper.selectByPrimaryKey(reportExt.getEmployeeId());
                reportExt.setSysEmployee(sysEmployee);
            }
            if (rlist.size() != 0){
                data.put("report", rlist.get(0));
            }
            data.put("status", rslist.get(0));
            data.put("comment", rclist);
            data.put("attachment", ralist);
            resultMap.put("data", data);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    /**
     * 微信端查询
     *
     * @param employeeId
     * @param type
     * @return
     */
    @CachePut(cacheNames = "Report")
    @ApiOperation("/我的列表")
    @GetMapping("/queryList")
    public Object queryList(@RequestParam Integer employeeId, @RequestParam String type, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<ReportExt> relist = new ArrayList<>();
            PageInfo<Report> pageInfo = reportService.queryMainReport(employeeId, type, pageNum, pageSize);
            List<Dict> dicts = dictService.queryType(Global.sq);
            for (Report r : pageInfo.getList()) {
                ReportExt reportExt = new ReportExt();
                for (Dict dict : dicts) {
                    if (dict.getCode().equals(r.getAppealType())) {
                        reportExt.setAppealTypeName(dict.getName());
                    }
                }
                reportExt.copy(r);
                List<ReportStatus> rslist = reportStatusService.queryByReportId(r.getId());
                reportExt.setNewStatus(rslist.get(0));
                List<Report> reports = reportService.querySubReport(r.getId());
                reportExt.setChild(reports);
                relist.add(reportExt);
            }

            resultMap.put("data", relist);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    @GetMapping("/sendToJB")
    public Object sendToJB(@RequestParam Integer rid) {

        Report report = reportService.queryById(rid);
        ReportExt ext = new ReportExt();
        ReportToJB jb = ext.copy(report).toJB();

        Map map = (Map) employeeFeignClient.queryEmployeeById(report.getEmployeeId());
        if ((Boolean) map.get("success")) {
            Map employ = (Map) map.get("data");
            String name = (String) employ.get("name");
            String mobile = (String) employ.get("mobile");
            jb.setUserName(name);
            jb.setUserMobile(mobile);
        }

        List<String> pictures = new ArrayList<>();
        List<ReportAttachment> reportAttachments = reportAttachmentService.queryReportId(report.getId());
        for (ReportAttachment reportAttachment : reportAttachments) {
            pictures.add(reportAttachment.getUrl());
        }
        jb.setPictures(pictures);
        jb.setEventId("WX" + System.currentTimeMillis() + "_" + jb.getId());

        weChatSender.sendWeChatEvent(JacksonUtil.toJSon(jb), ReportToJB.class);

        return jb;
    }
}