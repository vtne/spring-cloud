package com.sdxm.report.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Report {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.id
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.type
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    private String type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.create_time
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.employee_id
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    private Integer employeeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.latitude
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    private String latitude;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.longitude
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    private String longitude;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.appeal_type
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    private String appealType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.parent_id
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    private Integer parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.location
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    private String location;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.event_time
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date eventTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.event_describe
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    private String eventDescribe;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report.id
     *
     * @return the value of report.id
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report.id
     *
     * @param id the value for report.id
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report.type
     *
     * @return the value of report.type
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report.type
     *
     * @param type the value for report.type
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report.create_time
     *
     * @return the value of report.create_time
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report.create_time
     *
     * @param createTime the value for report.create_time
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report.employee_id
     *
     * @return the value of report.employee_id
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report.employee_id
     *
     * @param employeeId the value for report.employee_id
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report.latitude
     *
     * @return the value of report.latitude
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report.latitude
     *
     * @param latitude the value for report.latitude
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report.longitude
     *
     * @return the value of report.longitude
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report.longitude
     *
     * @param longitude the value for report.longitude
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report.appeal_type
     *
     * @return the value of report.appeal_type
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public String getAppealType() {
        return appealType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report.appeal_type
     *
     * @param appealType the value for report.appeal_type
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public void setAppealType(String appealType) {
        this.appealType = appealType == null ? null : appealType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report.parent_id
     *
     * @return the value of report.parent_id
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report.parent_id
     *
     * @param parentId the value for report.parent_id
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report.location
     *
     * @return the value of report.location
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public String getLocation() {
        return location;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report.location
     *
     * @param location the value for report.location
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report.event_time
     *
     * @return the value of report.event_time
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public Date getEventTime() {
        return eventTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report.event_time
     *
     * @param eventTime the value for report.event_time
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report.event_describe
     *
     * @return the value of report.event_describe
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public String getEventDescribe() {
        return eventDescribe;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report.event_describe
     *
     * @param eventDescribe the value for report.event_describe
     *
     * @mbg.generated Tue Sep 25 11:42:36 CST 2018
     */
    public void setEventDescribe(String eventDescribe) {
        this.eventDescribe = eventDescribe == null ? null : eventDescribe.trim();
    }
}