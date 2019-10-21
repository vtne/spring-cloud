package com.sdxm.information.dao;

import com.sdxm.information.entity.Admire;
import com.sdxm.information.entity.AdmireExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdmireMapper {
    int countByExample(AdmireExample example);

    int deleteByExample(AdmireExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Admire record);

    int insertSelective(Admire record);

    List<Admire> selectByExample(AdmireExample example);

    Admire selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Admire record, @Param("example") AdmireExample example);

    int updateByExample(@Param("record") Admire record, @Param("example") AdmireExample example);

    int updateByPrimaryKeySelective(Admire record);

    int updateByPrimaryKey(Admire record);
}