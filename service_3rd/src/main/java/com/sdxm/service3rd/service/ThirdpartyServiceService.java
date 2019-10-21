package com.sdxm.service3rd.service;

import com.anrong.boot.conf.Global;
import com.anrong.boot.util.ImageAddress;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdxm.service3rd.dao.DictMapper;
import com.sdxm.service3rd.entity.Dict;
import com.sdxm.service3rd.entity.DictExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.sdxm.service3rd.entity.ThirdpartyServiceExample;
import com.sdxm.service3rd.entity.ThirdpartyService;
import com.sdxm.service3rd.dao.ThirdpartyServiceMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
@Transactional
public class ThirdpartyServiceService {

    @Autowired
    private ThirdpartyServiceMapper thirdpartyServiceMapper;

    @Autowired
    private DictMapper dictMapper;


    //增加
    public int add(ThirdpartyService thirdpartyService) {
        String s = ImageAddress.extractString(thirdpartyService.getLogoImage());
        thirdpartyService.setLogoImage(s);
        int i = thirdpartyServiceMapper.insert(thirdpartyService);
        return i;
    }

    //删除
    public int delete(Integer id) {
        int i = thirdpartyServiceMapper.deleteByPrimaryKey(id);
        return i;
    }

    //修改
    public int update(ThirdpartyService thirdpartyService) {
        String s = ImageAddress.extractString(thirdpartyService.getLogoImage());
        thirdpartyService.setLogoImage(s);
        ThirdpartyServiceExample thirdpartyServiceExample = new ThirdpartyServiceExample();
        thirdpartyServiceExample.createCriteria().andIdEqualTo(thirdpartyService.getId());
        return thirdpartyServiceMapper.updateByExample(thirdpartyService, thirdpartyServiceExample);
    }

    //查询
    public List<ThirdpartyService> query(Integer page, Integer results, HttpServletRequest request) {
        ThirdpartyServiceExample thirdpartyServiceExample = new ThirdpartyServiceExample();
        thirdpartyServiceExample.setCount(results);
        thirdpartyServiceExample.setLimitStart((page - 1) * results);
        List<ThirdpartyService> list = thirdpartyServiceMapper.selectByExample(thirdpartyServiceExample);
        if (list != null && list.size() > 0) {
            for (ThirdpartyService thirdpartyService : list) {
                thirdpartyService.setLogoImage(ImageAddress.getUrl(request) + thirdpartyService.getLogoImage());
            }
        }
        return list;
    }
    //查询
    public PageInfo<ThirdpartyService> query2(Integer page, Integer results, String info, String status, HttpServletRequest request) {
        ThirdpartyServiceExample thirdpartyServiceExample = new ThirdpartyServiceExample();
        ThirdpartyServiceExample.Criteria criteria = thirdpartyServiceExample.createCriteria();
        if (info != null && !"".equals(info)) {
            criteria.andNameLike("%"+info+"%");
        }
        if (status != null && !"".equals(status)) {
            criteria.andTypeEqualTo(status);
        }
        PageHelper.startPage(page, results);
        List<ThirdpartyService> list = thirdpartyServiceMapper.selectByExample(thirdpartyServiceExample);
        if (list != null) {
            for (ThirdpartyService thirdpartyService : list) {
                thirdpartyService.setLogoImage(ImageAddress.getUrl(request) + thirdpartyService.getLogoImage());
            }
        }
        return new PageInfo<>(list);
    }

    //查询
    public List<ThirdpartyService> queryById(Integer id, HttpServletRequest request) {
        ThirdpartyServiceExample thirdpartyServiceExample = new ThirdpartyServiceExample();
        thirdpartyServiceExample.createCriteria().andIdEqualTo(id);
        List<ThirdpartyService> list = thirdpartyServiceMapper.selectByExample(thirdpartyServiceExample);
        if (list != null && list.size() > 0) {
            for (ThirdpartyService thirdpartyService : list) {
                thirdpartyService.setLogoImage(ImageAddress.getUrl(request) + thirdpartyService.getLogoImage());
            }
        }
        return list;
    }

    //查询
    public List<ThirdpartyService> queryByType(String type, Integer page, Integer results, HttpServletRequest request) {
        ThirdpartyServiceExample thirdpartyServiceExample = new ThirdpartyServiceExample();
        thirdpartyServiceExample.createCriteria().andTypeEqualTo(type);
        thirdpartyServiceExample.setCount(results);
        thirdpartyServiceExample.setLimitStart((page - 1) * results);
        List<ThirdpartyService> thirdpartyServices = thirdpartyServiceMapper.selectByExample(thirdpartyServiceExample);
        if (thirdpartyServices != null) {
            for (ThirdpartyService thirdpartyService : thirdpartyServices) {
                thirdpartyService.setLogoImage(ImageAddress.getUrl(request) + thirdpartyService.getLogoImage());
            }
        }
        return thirdpartyServices;
    }

    //判断是否已经存在
    public List<ThirdpartyService> isThere(ThirdpartyService thirdpartyService){
        ThirdpartyServiceExample thirdpartyServiceExample = new ThirdpartyServiceExample();
        thirdpartyServiceExample.createCriteria().andIdEqualTo(thirdpartyService.getId());
        return thirdpartyServiceMapper.selectByExample(thirdpartyServiceExample);
    }

    public List<Dict> queryType() {
        DictExample example = new DictExample();
        example.createCriteria().andParentCodeEqualTo(Global.dict_thirdparty);
        return dictMapper.selectByExample(example);
    }
}