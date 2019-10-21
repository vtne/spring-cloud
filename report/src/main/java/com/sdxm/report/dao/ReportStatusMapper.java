package com.sdxm.report.dao;

import com.sdxm.report.entity.ReportStatus;
import com.sdxm.report.entity.ReportStatusExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReportStatusMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_status
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    long countByExample(ReportStatusExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_status
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    int deleteByExample(ReportStatusExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_status
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_status
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    int insert(ReportStatus record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_status
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    int insertSelective(ReportStatus record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_status
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    List<ReportStatus> selectByExample(ReportStatusExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_status
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    ReportStatus selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_status
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    int updateByExampleSelective(@Param("record") ReportStatus record, @Param("example") ReportStatusExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_status
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    int updateByExample(@Param("record") ReportStatus record, @Param("example") ReportStatusExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_status
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    int updateByPrimaryKeySelective(ReportStatus record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_status
     *
     * @mbg.generated Sat Sep 01 10:50:59 CST 2018
     */
    int updateByPrimaryKey(ReportStatus record);
}