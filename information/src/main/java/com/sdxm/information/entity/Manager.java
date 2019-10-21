package com.sdxm.information.entity;

import java.util.Date;

public class Manager {
    private Integer id;

    private String name;

    private Integer sex;

    private String number;

    private String organ;

    private String remark;

    private Integer type;

    private String credentialName;

    private Date createTime;

    private Date updateTime;

    private String pic1Path;

    private String pic2Path;

    private String openId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ == null ? null : organ.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCredentialName() {
        return credentialName;
    }

    public void setCredentialName(String credentialName) {
        this.credentialName = credentialName == null ? null : credentialName.trim();
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

    public String getPic1Path() {
        return pic1Path;
    }

    public void setPic1Path(String pic1Path) {
        this.pic1Path = pic1Path == null ? null : pic1Path.trim();
    }

    public String getPic2Path() {
        return pic2Path;
    }

    public void setPic2Path(String pic2Path) {
        this.pic2Path = pic2Path == null ? null : pic2Path.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", number='" + number + '\'' +
                ", organ='" + organ + '\'' +
                ", remark='" + remark + '\'' +
                ", type=" + type +
                ", credentialName='" + credentialName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", pic1Path='" + pic1Path + '\'' +
                ", pic2Path='" + pic2Path + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }
}