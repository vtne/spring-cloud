package com.sdxm.report.entity;

import java.util.Date;

public class ReportStatus {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_status.id
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_status.report_id
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    private Integer reportId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_status.code
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    private String code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_status.create_time
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_status.id
     *
     * @return the value of report_status.id
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_status.id
     *
     * @param id the value for report_status.id
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_status.report_id
     *
     * @return the value of report_status.report_id
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    public Integer getReportId() {
        return reportId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_status.report_id
     *
     * @param reportId the value for report_status.report_id
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_status.code
     *
     * @return the value of report_status.code
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_status.code
     *
     * @param code the value for report_status.code
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_status.create_time
     *
     * @return the value of report_status.create_time
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_status.create_time
     *
     * @param createTime the value for report_status.create_time
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}