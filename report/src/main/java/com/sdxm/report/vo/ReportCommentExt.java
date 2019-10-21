package com.sdxm.report.vo;

import com.sdxm.report.entity.Report;
import com.sdxm.report.entity.ReportComment;
import com.sdxm.report.entity.ReportStatus;

import java.util.List;

public class ReportCommentExt extends ReportComment {

    List<ReportCommentExt> child;

    private String employeeName;
    private String employeeHeaderUrl;

    public List<ReportCommentExt> getChild() {
        return child;
    }

    public void setChild(List<ReportCommentExt> child) {
        this.child = child;
    }

    public ReportCommentExt copy(ReportComment comment){
        this.setId(comment.getId());
        this.setEmployeeId(comment.getEmployeeId());
        this.setCreateTime(comment.getCreateTime());
        this.setHappinessCode(comment.getHappinessCode());
        this.setParentId(comment.getParentId());
        this.setHappinessLv(comment.getHappinessLv());
        this.setContent(comment.getContent());
        return this;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeHeaderUrl() {
        return employeeHeaderUrl;
    }

    public void setEmployeeHeaderUrl(String employeeHeaderUrl) {
        this.employeeHeaderUrl = employeeHeaderUrl;
    }
}
