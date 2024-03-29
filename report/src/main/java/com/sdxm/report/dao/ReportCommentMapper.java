package com.sdxm.report.dao;

import com.sdxm.report.entity.ReportComment;
import com.sdxm.report.entity.ReportCommentExample;
import java.util.List;

import com.sdxm.report.vo.ReportCommentExt;
import org.apache.ibatis.annotations.Param;

public interface ReportCommentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_comment
     *
     * @mbg.generated Sat Sep 01 10:50:06 CST 2018
     */
    long countByExample(ReportCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_comment
     *
     * @mbg.generated Sat Sep 01 10:50:06 CST 2018
     */
    int deleteByExample(ReportCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_comment
     *
     * @mbg.generated Sat Sep 01 10:50:06 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_comment
     *
     * @mbg.generated Sat Sep 01 10:50:06 CST 2018
     */
    int insert(ReportComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_comment
     *
     * @mbg.generated Sat Sep 01 10:50:06 CST 2018
     */
    int insertSelective(ReportComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_comment
     *
     * @mbg.generated Sat Sep 01 10:50:06 CST 2018
     */
    List<ReportComment> selectByExampleWithBLOBs(ReportCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_comment
     *
     * @mbg.generated Sat Sep 01 10:50:06 CST 2018
     */
    List<ReportComment> selectByExample(ReportCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_comment
     *
     * @mbg.generated Sat Sep 01 10:50:06 CST 2018
     */
    ReportComment selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_comment
     *
     * @mbg.generated Sat Sep 01 10:50:06 CST 2018
     */
    int updateByExampleSelective(@Param("record") ReportComment record, @Param("example") ReportCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_comment
     *
     * @mbg.generated Sat Sep 01 10:50:06 CST 2018
     */
    int updateByExampleWithBLOBs(@Param("record") ReportComment record, @Param("example") ReportCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_comment
     *
     * @mbg.generated Sat Sep 01 10:50:06 CST 2018
     */
    int updateByExample(@Param("record") ReportComment record, @Param("example") ReportCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_comment
     *
     * @mbg.generated Sat Sep 01 10:50:06 CST 2018
     */
    int updateByPrimaryKeySelective(ReportComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_comment
     *
     * @mbg.generated Sat Sep 01 10:50:06 CST 2018
     */
    int updateByPrimaryKeyWithBLOBs(ReportComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_comment
     *
     * @mbg.generated Sat Sep 01 10:50:06 CST 2018
     */
    int updateByPrimaryKey(ReportComment record);

    List<ReportCommentExt> selectByExampleWithEmployee(Integer reportId);
}