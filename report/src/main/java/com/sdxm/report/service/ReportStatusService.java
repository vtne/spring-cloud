package com.sdxm.report.service;

import com.sdxm.report.dao.ReportStatusMapper;
import com.sdxm.report.entity.ReportStatus;
import com.sdxm.report.entity.ReportStatusExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ReportStatusService {

    @Autowired
    private ReportStatusMapper reportStatusMapper;

    //增加
    public int add(ReportStatus reportStatus) {
        int i = reportStatusMapper.insert(reportStatus);
        return i;
    }

    //删除
    public int delete(Integer id) {
        int i = reportStatusMapper.deleteByPrimaryKey(id);
        return i;
    }

    //修改
    public int update(ReportStatus reportStatus) {
        //int i = reportStatusMapper.updateByPrimaryKeySelective(reportStatus);
        ReportStatusExample reportStatusExample = new ReportStatusExample();
        reportStatusExample.createCriteria().andIdEqualTo(reportStatus.getId());
        return reportStatusMapper.updateByExample(reportStatus, reportStatusExample);
    }

    //查询
    public List<ReportStatus> query(Integer page,Integer results){
        ReportStatusExample reportStatusExample = new ReportStatusExample();
        reportStatusExample.setCount(results);
        reportStatusExample.setLimitStart((page - 1) * results);
        return reportStatusMapper.selectByExample(reportStatusExample);
    }

    //判断是否已经存在
    public List<ReportStatus> isThere(ReportStatus reportStatus){
        ReportStatusExample reportStatusExample = new ReportStatusExample();
        reportStatusExample.createCriteria().andIdEqualTo(reportStatus.getId());
        return reportStatusMapper.selectByExample(reportStatusExample);
    }

    //查询
    public List<ReportStatus> queryByReportId(Integer reportId){
        ReportStatusExample reportStatusExample = new ReportStatusExample();
        reportStatusExample.createCriteria().andReportIdEqualTo(reportId);
        reportStatusExample.setOrderByClause("create_time desc");
        List<ReportStatus> statuslist =  reportStatusMapper.selectByExample(reportStatusExample);
        return statuslist;
    }
}