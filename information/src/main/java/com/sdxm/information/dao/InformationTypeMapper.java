package com.sdxm.information.dao;

import com.sdxm.information.entity.InformationType;
import com.sdxm.information.entity.InformationTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InformationTypeMapper {
    long countByExample(InformationTypeExample example);

    int deleteByExample(InformationTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InformationType record);

    int insertSelective(InformationType record);

    List<InformationType> selectByExample(InformationTypeExample example);

    InformationType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InformationType record, @Param("example") InformationTypeExample example);

    int updateByExample(@Param("record") InformationType record, @Param("example") InformationTypeExample example);

    int updateByPrimaryKeySelective(InformationType record);

    int updateByPrimaryKey(InformationType record);
}