package com.anrong.urpm.entity;

public class SysPower {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_power.id
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_power.code
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    private String code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_power.parent_code
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    private String parentCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_power.enterprise_id
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    private Integer enterpriseId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_power.icon
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    private String icon;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_power.url
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    private Integer url;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_power.id
     *
     * @return the value of sys_power.id
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_power.id
     *
     * @param id the value for sys_power.id
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_power.code
     *
     * @return the value of sys_power.code
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_power.code
     *
     * @param code the value for sys_power.code
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_power.parent_code
     *
     * @return the value of sys_power.parent_code
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_power.parent_code
     *
     * @param parentCode the value for sys_power.parent_code
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_power.enterprise_id
     *
     * @return the value of sys_power.enterprise_id
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_power.enterprise_id
     *
     * @param enterpriseId the value for sys_power.enterprise_id
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_power.icon
     *
     * @return the value of sys_power.icon
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    public String getIcon() {
        return icon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_power.icon
     *
     * @param icon the value for sys_power.icon
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_power.url
     *
     * @return the value of sys_power.url
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    public Integer getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_power.url
     *
     * @param url the value for sys_power.url
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    public void setUrl(Integer url) {
        this.url = url;
    }
}