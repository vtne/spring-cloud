package com.sdxm.information.service;

import com.anrong.boot.util.ImageAddress;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdxm.information.dao.ShowLocationMapper;
import com.sdxm.information.dao.SlideshowMapper;
import com.sdxm.information.entity.ShowLocation;
import com.sdxm.information.entity.ShowLocationExample;
import com.sdxm.information.entity.Slideshow;
import com.sdxm.information.entity.SlideshowExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SlideshownService {

    @Resource
    private SlideshowMapper slideshowMapper;

    @Resource
    private ShowLocationMapper showLocationMapper;
    /**
     * 查询轮播图管理列表(后台管理)
     *
     * @param request
     * @param audit
     * @param param
     * @return
     */
    public PageInfo<Slideshow> querySlideShownList(HttpServletRequest request, Integer pageNum, Integer pageSize, String param, Integer audit, Integer location) {
        SlideshowExample slideshowExample = new SlideshowExample();
        SlideshowExample.Criteria criteria = slideshowExample.createCriteria();
        if (audit != null) {
            criteria.andAuditEqualTo(audit);
        }
        if (param != null && !param.equals("")) {
            criteria.andPicNameLike("%" + param + "%");
        }
        if (location != null) {
            criteria.andPicLocationEqualTo(location);
        }
        slideshowExample.setOrderByClause("create_time desc");
        PageHelper.startPage(pageNum, pageSize);
        List<Slideshow> slideshows = slideshowMapper.selectByExample(slideshowExample);
        if (slideshows != null && slideshows.size() > 0) {
            for (Slideshow slideshow : slideshows) {
                slideshow.setPicPath(ImageAddress.getUrl(request) + slideshow.getPicPath());
            }
        }
        return new PageInfo<>(slideshows);
    }

    /**
     * 新建
     *
     * @param slideshow
     * @return
     */
    public int add(Slideshow slideshow) {
        slideshow.setPicPath(ImageAddress.extractString(slideshow.getPicPath()));
        slideshow.setCreateTime(new Date());
        return slideshowMapper.insertSelective(slideshow);
    }

    /**
     * 修改
     *
     * @param slideshow
     * @return
     */
    public int update(Slideshow slideshow) {
        if (slideshow.getPicPath() != null && !slideshow.getPicPath().equals("")) {
            slideshow.setPicPath(ImageAddress.extractString(slideshow.getPicPath()));
        }
        slideshow.setUpdateTime(new Date());
        return slideshowMapper.updateByPrimaryKeySelective(slideshow);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public int delete(Integer id) {
        return slideshowMapper.deleteByPrimaryKey(id);
    }

    /**
     * 微信端查询轮播图
     *
     * @return
     */
    public List querySlideshow(HttpServletRequest request) {
        SlideshowExample slideshowExample = new SlideshowExample();
        ShowLocationExample showLocationExample = new ShowLocationExample();
        showLocationExample.createCriteria().andLocationNameEqualTo("小程序首页");
        List<ShowLocation> showLocations = showLocationMapper.selectByExample(showLocationExample);
        slideshowExample.createCriteria().andAuditEqualTo(1).andPicLocationEqualTo(showLocations.get(0).getId());
        List<Slideshow> slideshows = slideshowMapper.selectByExample(slideshowExample);

        if (slideshows != null && slideshows.size() > 0) {
            Collections.sort(slideshows, new Comparator<Slideshow>() {
                @Override
                public int compare(Slideshow o1, Slideshow o2) {
                    if (o1.getSequence() > o2.getSequence()) {
                        return 1;
                    }
                    if (o1.getSequence().intValue() == o2.getSequence().intValue()) {
                        if (o1.getCreateTime().compareTo(o2.getCreateTime()) == 1) {
                            return -1;
                        } else {
                            return 1;
                        }
                        //return 0;
                    }
                    return -1;
                }
            });
            for (Slideshow slideshow : slideshows) {
                if (slideshow.getPicPath() != null && !"".equals(slideshow.getPicPath())) {
                    slideshow.setPicPath(ImageAddress.getUrl(request) + slideshow.getPicPath());
                }
            }
        }
        return slideshows;
    }
}
