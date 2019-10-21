package com.sdxm.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.sdxm.report.entity.ReportAttachmentExample;
import com.sdxm.report.entity.ReportAttachment;
import com.sdxm.report.dao.ReportAttachmentMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ReportAttachmentService {

    @Autowired
    private ReportAttachmentMapper reportAttachmentMapper;

    //增加
    public int add(ReportAttachment reportAttachment) {
        int i = reportAttachmentMapper.insert(reportAttachment);
        return i;
    }

    //删除
    public int delete(Integer id) {
        int i = reportAttachmentMapper.deleteByPrimaryKey(id);
        return i;
    }

    //修改
    public int update(ReportAttachment reportAttachment) {
        //int i = reportAttachmentMapper.updateByPrimaryKeySelective(reportAttachment);
        ReportAttachmentExample reportAttachmentExample = new ReportAttachmentExample();
        reportAttachmentExample.createCriteria().andIdEqualTo(reportAttachment.getId());
        return reportAttachmentMapper.updateByExample(reportAttachment, reportAttachmentExample);
    }

    //查询
    public List<ReportAttachment> query(Integer page,Integer results){
        ReportAttachmentExample reportAttachmentExample = new ReportAttachmentExample();
        reportAttachmentExample.setCount(results);
        reportAttachmentExample.setLimitStart((page - 1) * results);
        return reportAttachmentMapper.selectByExample(reportAttachmentExample);
    }

    //判断是否已经存在
    public List<ReportAttachment> isThere(ReportAttachment reportAttachment){
        ReportAttachmentExample reportAttachmentExample = new ReportAttachmentExample();
        reportAttachmentExample.createCriteria().andIdEqualTo(reportAttachment.getId());
        return reportAttachmentMapper.selectByExample(reportAttachmentExample);
    }

    public List<ReportAttachment> queryReportId(Integer reportId){
        ReportAttachmentExample reportAttachmentExample = new ReportAttachmentExample();
        reportAttachmentExample.createCriteria().andReportIdEqualTo(reportId);
        return reportAttachmentMapper.selectByExample(reportAttachmentExample);
    }
}