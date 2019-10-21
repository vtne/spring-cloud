package com.anrong.urpm.vo;

import com.anrong.urpm.entity.SysEmployee;
import com.anrong.urpm.entity.SysLog;

import java.util.List;

/**
 * @apiNote 企业信息的封装类
 */
public class EmployeeVO extends SysEmployee {

    private List<SysLog> list;

    public List<SysLog> getList() {
        return list;
    }

    public void setList(List<SysLog> list) {
        this.list = list;
    }

    private String deptName; // 部门名称
    private String enterpriseName; // 企业名称

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public void copyPropertiesFromEmployee(SysEmployee sysEmployee) {
        this.setCreateTime(sysEmployee.getCreateTime());
        this.setDeleteFlag(sysEmployee.getDeleteFlag());
        this.setDepartmentId(sysEmployee.getDepartmentId());
        this.setEmail(sysEmployee.getEmail());
        this.setEnterpriseId(sysEmployee.getEnterpriseId());
        this.setId(sysEmployee.getId());
        this.setMobile(sysEmployee.getMobile());
        this.setName(sysEmployee.getName());
        this.setUpdateTime(sysEmployee.getUpdateTime());
        this.setUserId(sysEmployee.getUserId());
        this.setHeadUrl(sysEmployee.getHeadUrl());
        this.setNickName(sysEmployee.getNickName());
        this.setSex(sysEmployee.getSex());
    }

}
