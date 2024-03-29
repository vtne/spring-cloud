package com.anrong.urpm.dao;

import com.anrong.urpm.entity.SysPower;
import com.anrong.urpm.entity.SysPowerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysPowerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_power
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    long countByExample(SysPowerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_power
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    int deleteByExample(SysPowerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_power
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_power
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    int insert(SysPower record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_power
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    int insertSelective(SysPower record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_power
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    List<SysPower> selectByExample(SysPowerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_power
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    SysPower selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_power
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    int updateByExampleSelective(@Param("record") SysPower record, @Param("example") SysPowerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_power
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    int updateByExample(@Param("record") SysPower record, @Param("example") SysPowerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_power
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    int updateByPrimaryKeySelective(SysPower record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_power
     *
     * @mbg.generated Tue Aug 28 15:59:59 CST 2018
     */
    int updateByPrimaryKey(SysPower record);
}