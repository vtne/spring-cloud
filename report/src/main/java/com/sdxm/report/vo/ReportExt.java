package com.sdxm.report.vo;

import com.sdxm.report.entity.Report;
import com.sdxm.report.entity.ReportStatus;
import com.sdxm.report.entity.SysEmployee;

import java.util.List;

public class ReportExt extends Report {
    private List<String> urlList;
    private ReportStatus newStatus;
    private SysEmployee sysEmployee;
    private List<Report> child;
    private String mobile;
    private String code;
    private String nickName;
    /**
     * 诉求类型的名称
     */
    private String appealTypeName;
    /**
     * 标记首报下是否有续报
     */
    private Boolean flag = false;

    public ReportExt copy(Report report) {
        this.setId(report.getId());
        this.setType(report.getType());
        this.setCreateTime(report.getCreateTime());
        this.setEmployeeId(report.getEmployeeId());
        this.setLatitude(report.getLatitude());
        this.setLongitude(report.getLongitude());
        this.setAppealType(report.getAppealType());
        this.setEventDescribe(report.getEventDescribe());
        this.setLocation(report.getLocation());
        this.setParentId(report.getParentId());
        return this;
    }

    public ReportToJB toJB() {
        ReportToJB report = new ReportToJB();
        report.setId(this.getId());
        report.setType(this.getType());
        report.setCreateTime(this.getCreateTime());
        report.setEmployeeId(this.getEmployeeId());
        report.setLatitude(this.getLatitude());
        report.setLongitude(this.getLongitude());
        report.setAppealType(this.getAppealType());
        report.setEventDescribe(this.getEventDescribe());
        report.setLocation(this.getLocation());
        report.setParentId(this.getParentId());
        return report;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public ReportStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(ReportStatus newStatus) {
        this.newStatus = newStatus;
    }

    public List<Report> getChild() {
        return child;
    }

    public void setChild(List<Report> child) {
        this.child = child;
    }

    public SysEmployee getSysEmployee() {
        return sysEmployee;
    }

    public void setSysEmployee(SysEmployee sysEmployee) {
        this.sysEmployee = sysEmployee;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getAppealTypeName() {
        return appealTypeName;
    }

    public void setAppealTypeName(String appealTypeName) {
        this.appealTypeName = appealTypeName;
    }
}
