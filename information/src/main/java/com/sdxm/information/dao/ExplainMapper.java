package com.sdxm.information.dao;

import com.sdxm.information.entity.Explain;
import com.sdxm.information.entity.ExplainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExplainMapper {
    int countByExample(ExplainExample example);

    int deleteByExample(ExplainExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Explain record);

    int insertSelective(Explain record);

    List<Explain> selectByExampleWithBLOBs(ExplainExample example);

    List<Explain> selectByExample(ExplainExample example);

    Explain selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Explain record, @Param("example") ExplainExample example);

    int updateByExampleWithBLOBs(@Param("record") Explain record, @Param("example") ExplainExample example);

    int updateByExample(@Param("record") Explain record, @Param("example") ExplainExample example);

    int updateByPrimaryKeySelective(Explain record);

    int updateByPrimaryKeyWithBLOBs(Explain record);

    int updateByPrimaryKey(Explain record);
}