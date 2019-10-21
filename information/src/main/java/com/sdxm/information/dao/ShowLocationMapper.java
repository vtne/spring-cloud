package com.sdxm.information.dao;

import com.sdxm.information.entity.ShowLocation;
import com.sdxm.information.entity.ShowLocationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShowLocationMapper {
    long countByExample(ShowLocationExample example);

    int deleteByExample(ShowLocationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ShowLocation record);

    int insertSelective(ShowLocation record);

    List<ShowLocation> selectByExample(ShowLocationExample example);

    ShowLocation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ShowLocation record, @Param("example") ShowLocationExample example);

    int updateByExample(@Param("record") ShowLocation record, @Param("example") ShowLocationExample example);

    int updateByPrimaryKeySelective(ShowLocation record);

    int updateByPrimaryKey(ShowLocation record);
}