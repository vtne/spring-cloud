package com.sdxm.information.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdxm.information.dao.InformationMapper;
import com.sdxm.information.dao.InformationTypeMapper;
import com.sdxm.information.entity.Information;
import com.sdxm.information.entity.InformationExample;
import com.sdxm.information.entity.InformationType;
import com.sdxm.information.entity.InformationTypeExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class InformationTypeService {

    @Autowired
    private InformationTypeMapper informationTypeMapper;

    @Autowired
    private InformationMapper informationMapper;

    /**
     * 查询 新闻类型
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<InformationType> queryInfoType(Integer pageNum, Integer pageSize) {
        InformationTypeExample example = new InformationTypeExample();
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
            List<InformationType> informationTypes = informationTypeMapper.selectByExample(example);
            PageInfo<InformationType> pageInfo = new PageInfo<>(informationTypes);
            return pageInfo;
        } else {
            List<InformationType> informationTypes = informationTypeMapper.selectByExample(example);
            Collections.sort(informationTypes, new Comparator<InformationType>() {
                @Override
                public int compare(InformationType o1, InformationType o2) {
                    if (o1.getSequence() > o2.getSequence()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
            PageInfo<InformationType> pageInfo = new PageInfo<>(informationTypes);
            return pageInfo;
        }

    }

    /**
     * 修改 新闻类型
     *
     * @return
     */
    public int updateInfoType(InformationType informationType) {
        informationType.setUpdateTime(new Date());
        int i = informationTypeMapper.updateByPrimaryKeySelective(informationType);
        return i;
    }

    /**
     * 查询详情
     *
     * @param informationType
     * @return
     */
    public InformationType queryInfoTypeById(InformationType informationType) {
        InformationType informationType1 = informationTypeMapper.selectByPrimaryKey(informationType.getId());
        return informationType1;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public int deleteById(Integer id) {
        InformationExample informationExample = new InformationExample();
        informationExample.createCriteria().andTypeEqualTo(String.valueOf(id));
        List<Information> list = informationMapper.selectByExample(informationExample);
        //判断是否此类型下有已发布的新闻
        if (list != null && list.size() > 0) {
            return 0;
        }
        int i = informationTypeMapper.deleteByPrimaryKey(id);
        return i;
    }

    /**
     * 新建
     *
     * @param informationType
     * @return
     */
    public int createInforType(InformationType informationType) {
        //设置创建时间
        informationType.setCreateTime(new Date());

        int insert = informationTypeMapper.insert(informationType);
        return insert;
    }
}
