package com.sdxm.information.dao;

import com.sdxm.information.entity.Help;
import com.sdxm.information.entity.HelpExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HelpMapper {
    long countByExample(HelpExample example);

    int deleteByExample(HelpExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Help record);

    int insertSelective(Help record);

    List<Help> selectByExample(HelpExample example);

    Help selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Help record, @Param("example") HelpExample example);

    int updateByExample(@Param("record") Help record, @Param("example") HelpExample example);

    int updateByPrimaryKeySelective(Help record);

    int updateByPrimaryKey(Help record);
}