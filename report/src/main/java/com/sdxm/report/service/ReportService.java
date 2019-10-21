package com.sdxm.report.service;

import com.anrong.boot.conf.Global;
import com.anrong.boot.util.ImageAddress;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdxm.report.dao.ReportAttachmentMapper;
import com.sdxm.report.dao.ReportMapper;
import com.sdxm.report.dao.ReportStatusMapper;
import com.sdxm.report.dao.SysEmployeeMapper;
import com.sdxm.report.entity.*;
import com.sdxm.report.vo.ReportExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Service
@Transactional
public class ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private ReportAttachmentMapper reportAttachmentMapper;

    @Autowired
    private ReportStatusMapper reportStatusMapper;

    @Autowired
    private SysEmployeeMapper sysEmployeeMapper;
    //增加
    @Transactional
    public int add(ReportExt reportExt) {

        int flag = reportMapper.insert(reportExt);
        if(flag>0){
            if(reportExt.getUrlList()!=null) {
                for(String url : reportExt.getUrlList()){
                    ReportAttachment reportAttachment = new ReportAttachment();
                    reportAttachment.setCreateTime(new Date());
                    reportAttachment.setName(ImageAddress.extractString(url));
                    reportAttachment.setReportId(reportExt.getId());
                    reportAttachment.setUrl(ImageAddress.extractString(url));
                    flag = reportAttachmentMapper.insert(reportAttachment);
                }
            }
            if(flag>0&&reportExt.getParentId()==0) {
                ReportStatus reportStatus = new ReportStatus();
                reportStatus.setCode(Global.sta_0);
                reportStatus.setCreateTime(new Date());
                reportStatus.setReportId(reportExt.getId());
                flag = reportStatusMapper.insert(reportStatus);
            }
        }

        return flag;
    }

    //删除
    public int delete(Integer id) {
        int i = reportMapper.deleteByPrimaryKey(id);
        return i;
    }

    //修改
    public int update(Report report) {
        //int i = reportMapper.updateByPrimaryKeySelective(report);
        ReportExample reportExample = new ReportExample();
        reportExample.createCriteria().andIdEqualTo(report.getId());
        return reportMapper.updateByExampleSelective(report, reportExample);
    }

    //查询
    public List<Report> query(Integer page,Integer results){
        ReportExample reportExample = new ReportExample();
        reportExample.setCount(results);
        reportExample.setLimitStart((page - 1) * results);
        return reportMapper.selectByExample(reportExample);
    }

    //查询
    public List<ReportExt> queryAll(Integer page,Integer results){
        List<ReportExt> list=new ArrayList<>();

        ReportExample reportExample = new ReportExample();
        reportExample.setCount(results);
        reportExample.setLimitStart((page - 1) * results);
        List<Report> reports = reportMapper.selectByExampleWithBLOBs(reportExample);
        for (Report r:reports) {
            ReportExt reportExt=new ReportExt();
            reportExt.copy(r);
            SysEmployee sysEmployee = sysEmployeeMapper.selectByPrimaryKey(r.getEmployeeId());
            reportExt.setSysEmployee(sysEmployee);

            ReportStatusExample reportStatusExample=new ReportStatusExample();
            reportStatusExample.createCriteria().andReportIdEqualTo(r.getId());
            reportStatusExample.setOrderByClause("create_time desc");
            List<ReportStatus> reportStatuses = reportStatusMapper.selectByExample(reportStatusExample);
            if(reportStatuses.size()!=0){
                ReportStatus reportStatus = reportStatuses.get(0);
                reportExt.setNewStatus(reportStatus);
            }
            list.add(reportExt);
        }

        return list;
    }
    //查询
    public PageInfo<ReportExt> queryAll(Integer page, Integer results, String info, String type, String status, Integer pid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", type);
        if (info != null && !"".equals(info)) {
            map.put("info", info);
        }

        if (status != null && !"".equals(status)) {
            map.put("status", status);
        }
        map.put("pid", 0);
        if (pid != null && pid != 0) {
            map.put("pid", pid);
        }
        PageHelper.startPage(page, results);
        List<ReportExt> reportExts = reportMapper.select(map);
        //查询状态
        if (reportExts != null && reportExts.size() > 0) {
            for (ReportExt reportExt : reportExts) {
                ReportStatusExample example = new ReportStatusExample();
                example.createCriteria().andReportIdEqualTo(reportExt.getId());
                example.setOrderByClause("create_time desc");
                List<ReportStatus> reportStatuses = reportStatusMapper.selectByExample(example);
                if (reportStatuses != null && reportStatuses.size() > 0) {
                    reportExt.setCode(reportStatuses.get(0).getCode());
                }
                if (pid == null || pid == 0) {
                    ReportExample reportExample = new ReportExample();
                    reportExample.createCriteria().andParentIdEqualTo(reportExt.getId());
                    List<Report> reports = reportMapper.selectByExample(reportExample);
                    if (reports != null && reports.size() > 0) {
                        reportExt.setFlag(true);
                    }
                }
            }
        }
        return new PageInfo<>(reportExts);
    }

    /**
     * 判断是否已经存在
     *
     * @param report
     * @return
     */
    public List<Report> isThere(Report report){
        ReportExample reportExample = new ReportExample();
        reportExample.createCriteria().andIdEqualTo(report.getId());
        return reportMapper.selectByExample(reportExample);
    }

    /**
     * 查询
     *
     * @param reportId
     * @return
     */
    public List<ReportExt> queryDetail(Integer reportId, HttpServletRequest request){
        ReportExample reportExample = new ReportExample();
        reportExample.createCriteria().andIdEqualTo(reportId);
        reportExample.or().andParentIdEqualTo(reportId);
        List<Report> rlist = reportMapper.selectByExampleWithBLOBs(reportExample);
        List<ReportExt> rext_list = new ArrayList<>();
        for(Report report : rlist){
            ReportExt rext = new ReportExt().copy(report);
            ReportAttachmentExample reportAttachmentExample = new ReportAttachmentExample();
            reportAttachmentExample.createCriteria().andReportIdEqualTo(rext.getId());
            List<ReportAttachment> ralist = reportAttachmentMapper.selectByExample(reportAttachmentExample);
            List<String> urllist = new ArrayList<>();
            for(ReportAttachment ra : ralist) {
                //拼接图片地址
                urllist.add(ImageAddress.getUrl(request) + ra.getUrl());
            }
            rext.setUrlList(urllist);
            SysEmployee sysEmployee = sysEmployeeMapper.selectByPrimaryKey(report.getEmployeeId());
            rext.setSysEmployee(sysEmployee);
            rext_list.add(rext);
        }
        return rext_list;
    }

    /**
     * 查询根据员工id查上报、诉求
     *
     * @param employeeId
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<Report> queryMainReport(Integer employeeId, String type, Integer pageNum, Integer pageSize) {
        ReportExample reportExample = new ReportExample();
        ReportExample.Criteria criteria = reportExample.createCriteria().andEmployeeIdEqualTo(employeeId).andParentIdEqualTo(0);
        if(type!=null){
            criteria.andTypeEqualTo(type);
        }
        reportExample.setOrderByClause("CREATE_TIME DESC");
        PageHelper.startPage(pageNum, pageSize, false);
        List<Report> reports = reportMapper.selectByExampleWithBLOBs(reportExample);
        return new PageInfo<>(reports);
    }

    /**
     * 查询根据parentid
     *
     * @param parentId
     * @return
     */
    public List<Report> querySubReport(Integer parentId){
        ReportExample reportExample = new ReportExample();
        reportExample.createCriteria().andParentIdEqualTo(parentId);
        reportExample.setOrderByClause("create_time desc");
        return reportMapper.selectByExampleWithBLOBs(reportExample);
    }

    public Report queryById(Integer id){
        return reportMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询 事件 和 诉求 的数量
     *
     * @return
     */
    public Map<String, Long> queryEventSum() {
        ReportExample reportExample = new ReportExample();
        reportExample.createCriteria().andTypeEqualTo(Global.ssp);
        long ssp = reportMapper.countByExample(reportExample);
        ReportExample reportExample1 = new ReportExample();
        reportExample1.createCriteria().andTypeEqualTo(Global.sq);
        long sq = reportMapper.countByExample(reportExample1);

        HashMap<String, Long> map = new HashMap<>();
        map.put("eventSum", ssp);
        map.put("appealSum", sq);
        return map;
    }

    /**
     * 查询 通知
     *
     * @return
     */
    public PageInfo<ReportExt> queryMessage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ReportExt> list = reportMapper.queryMessage();
        return new PageInfo<>(list);
    }
}