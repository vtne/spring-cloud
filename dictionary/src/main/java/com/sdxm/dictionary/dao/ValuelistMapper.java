package com.sdxm.dictionary.dao;

import com.sdxm.dictionary.entity.Valuelist;
import com.sdxm.dictionary.entity.ValuelistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ValuelistMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table valuelist
     *
     * @mbg.generated Thu Aug 30 21:26:13 CST 2018
     */
    long countByExample(ValuelistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table valuelist
     *
     * @mbg.generated Thu Aug 30 21:26:13 CST 2018
     */
    int deleteByExample(ValuelistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table valuelist
     *
     * @mbg.generated Thu Aug 30 21:26:13 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table valuelist
     *
     * @mbg.generated Thu Aug 30 21:26:13 CST 2018
     */
    int insert(Valuelist record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table valuelist
     *
     * @mbg.generated Thu Aug 30 21:26:13 CST 2018
     */
    int insertSelective(Valuelist record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table valuelist
     *
     * @mbg.generated Thu Aug 30 21:26:13 CST 2018
     */
    List<Valuelist> selectByExample(ValuelistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table valuelist
     *
     * @mbg.generated Thu Aug 30 21:26:13 CST 2018
     */
    Valuelist selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table valuelist
     *
     * @mbg.generated Thu Aug 30 21:26:13 CST 2018
     */
    int updateByExampleSelective(@Param("record") Valuelist record, @Param("example") ValuelistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table valuelist
     *
     * @mbg.generated Thu Aug 30 21:26:13 CST 2018
     */
    int updateByExample(@Param("record") Valuelist record, @Param("example") ValuelistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table valuelist
     *
     * @mbg.generated Thu Aug 30 21:26:13 CST 2018
     */
    int updateByPrimaryKeySelective(Valuelist record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table valuelist
     *
     * @mbg.generated Thu Aug 30 21:26:13 CST 2018
     */
    int updateByPrimaryKey(Valuelist record);
}