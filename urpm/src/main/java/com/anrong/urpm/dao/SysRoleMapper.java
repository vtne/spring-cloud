package com.anrong.urpm.dao;

import com.anrong.urpm.entity.SysRole;
import com.anrong.urpm.entity.SysRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated Tue Aug 28 16:02:20 CST 2018
     */
    long countByExample(SysRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated Tue Aug 28 16:02:20 CST 2018
     */
    int deleteByExample(SysRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated Tue Aug 28 16:02:20 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated Tue Aug 28 16:02:20 CST 2018
     */
    int insert(SysRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated Tue Aug 28 16:02:20 CST 2018
     */
    int insertSelective(SysRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated Tue Aug 28 16:02:20 CST 2018
     */
    List<SysRole> selectByExample(SysRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated Tue Aug 28 16:02:20 CST 2018
     */
    SysRole selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated Tue Aug 28 16:02:20 CST 2018
     */
    int updateByExampleSelective(@Param("record") SysRole record, @Param("example") SysRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated Tue Aug 28 16:02:20 CST 2018
     */
    int updateByExample(@Param("record") SysRole record, @Param("example") SysRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated Tue Aug 28 16:02:20 CST 2018
     */
    int updateByPrimaryKeySelective(SysRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated Tue Aug 28 16:02:20 CST 2018
     */
    int updateByPrimaryKey(SysRole record);
}