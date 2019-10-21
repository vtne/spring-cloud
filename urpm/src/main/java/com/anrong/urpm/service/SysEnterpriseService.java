package com.anrong.urpm.service;

import com.anrong.urpm.dao.SysEnterpriseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.anrong.urpm.entity.SysEnterpriseExample;
import com.anrong.urpm.entity.SysEnterprise;

import java.util.List;


@Service
@Transactional
public class SysEnterpriseService {

    @Autowired
    private SysEnterpriseMapper sysEnterpriseMapper;

    //增加
    public int add(SysEnterprise sysEnterprise) {
        int i = sysEnterpriseMapper.insert(sysEnterprise);
        return i;
    }

    //删除
    public int delete(Integer id) {
        int i = sysEnterpriseMapper.deleteByPrimaryKey(id);
        return i;
    }

    //修改
    public int update(SysEnterprise sysEnterprise) {
        //int i = sysEnterpriseMapper.updateByPrimaryKeySelective(sysEnterprise);
        SysEnterpriseExample sysEnterpriseExample = new SysEnterpriseExample();
        sysEnterpriseExample.createCriteria().andIdEqualTo(sysEnterprise.getId());
        return sysEnterpriseMapper.updateByExample(sysEnterprise, sysEnterpriseExample);
    }

    //查询
    public List<SysEnterprise> query(Integer page,Integer results){
        SysEnterpriseExample sysEnterpriseExample = new SysEnterpriseExample();
        sysEnterpriseExample.setCount(results);
        sysEnterpriseExample.setLimitStart((page - 1) * results);
        return sysEnterpriseMapper.selectByExample(sysEnterpriseExample);
    }

    //判断是否已经存在
    public List<SysEnterprise> isThere(SysEnterprise sysEnterprise){
        SysEnterpriseExample sysEnterpriseExample = new SysEnterpriseExample();
        sysEnterpriseExample.createCriteria().andIdEqualTo(sysEnterprise.getId());
        return sysEnterpriseMapper.selectByExample(sysEnterpriseExample);
    }
}