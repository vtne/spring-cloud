package com.sdxm.information.dao;

import com.sdxm.information.entity.Slideshow;
import com.sdxm.information.entity.SlideshowExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SlideshowMapper {
    long countByExample(SlideshowExample example);

    int deleteByExample(SlideshowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Slideshow record);

    int insertSelective(Slideshow record);

    List<Slideshow> selectByExample(SlideshowExample example);

    Slideshow selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Slideshow record, @Param("example") SlideshowExample example);

    int updateByExample(@Param("record") Slideshow record, @Param("example") SlideshowExample example);

    int updateByPrimaryKeySelective(Slideshow record);

    int updateByPrimaryKey(Slideshow record);
}