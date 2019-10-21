package com.sdxm.information.entity;

import java.util.Date;

public class Information {
    private Integer id;

    private String type;

    private String logoImage;

    private String summary;

    private String detail;

    private Date createTime;

    private Date updateTime;

    private Integer sequence;

    private Integer audit;

    private Integer scanNum;

    private Integer scanNumCustom;

    private Integer upWall;

    private Date upTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage == null ? null : logoImage.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public Integer getScanNum() {
        return scanNum;
    }

    public void setScanNum(Integer scanNum) {
        this.scanNum = scanNum;
    }

    public Integer getScanNumCustom() {
        return scanNumCustom;
    }

    public void setScanNumCustom(Integer scanNumCustom) {
        this.scanNumCustom = scanNumCustom;
    }

    public Integer getUpWall() {
        return upWall;
    }

    public void setUpWall(Integer upWall) {
        this.upWall = upWall;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }
}