package com.anrong.urpm.entity;

public class SysEnterprise {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_enterprise.id
     *
     * @mbg.generated Tue Aug 28 17:22:35 CST 2018
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_enterprise.name
     *
     * @mbg.generated Tue Aug 28 17:22:35 CST 2018
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_enterprise.delete_flag
     *
     * @mbg.generated Tue Aug 28 17:22:35 CST 2018
     */
    private Integer deleteFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_enterprise.code
     *
     * @mbg.generated Tue Aug 28 17:22:35 CST 2018
     */
    private String code;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_enterprise.id
     *
     * @return the value of sys_enterprise.id
     *
     * @mbg.generated Tue Aug 28 17:22:35 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_enterprise.id
     *
     * @param id the value for sys_enterprise.id
     *
     * @mbg.generated Tue Aug 28 17:22:35 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_enterprise.name
     *
     * @return the value of sys_enterprise.name
     *
     * @mbg.generated Tue Aug 28 17:22:35 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_enterprise.name
     *
     * @param name the value for sys_enterprise.name
     *
     * @mbg.generated Tue Aug 28 17:22:35 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_enterprise.delete_flag
     *
     * @return the value of sys_enterprise.delete_flag
     *
     * @mbg.generated Tue Aug 28 17:22:35 CST 2018
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_enterprise.delete_flag
     *
     * @param deleteFlag the value for sys_enterprise.delete_flag
     *
     * @mbg.generated Tue Aug 28 17:22:35 CST 2018
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_enterprise.code
     *
     * @return the value of sys_enterprise.code
     *
     * @mbg.generated Tue Aug 28 17:22:35 CST 2018
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_enterprise.code
     *
     * @param code the value for sys_enterprise.code
     *
     * @mbg.generated Tue Aug 28 17:22:35 CST 2018
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}