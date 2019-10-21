package com.anrong.urpm.service;

import com.anrong.urpm.dao.SysRolePowerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.anrong.urpm.entity.SysRolePowerExample;
import com.anrong.urpm.entity.SysRolePower;

import java.util.List;


@Service
@Transactional
public class SysRolePowerService {

    @Autowired
    private SysRolePowerMapper sysRolePowerMapper;

    //增加
    public int add(SysRolePower sysRolePower) {
        int i = sysRolePowerMapper.insert(sysRolePower);
        return i;
    }

    //删除
    public int delete(Integer id) {
        int i = sysRolePowerMapper.deleteByPrimaryKey(id);
        return i;
    }

    //修改
    public int update(SysRolePower sysRolePower) {
        //int i = sysRolePowerMapper.updateByPrimaryKeySelective(sysRolePower);
        SysRolePowerExample sysRolePowerExample = new SysRolePowerExample();
        sysRolePowerExample.createCriteria().andIdEqualTo(sysRolePower.getId());
        return sysRolePowerMapper.updateByExample(sysRolePower, sysRolePowerExample);
    }

    //查询
    public List<SysRolePower> query(Integer page,Integer results){
        SysRolePowerExample sysRolePowerExample = new SysRolePowerExample();
        sysRolePowerExample.setCount(results);
        sysRolePowerExample.setLimitStart((page - 1) * results);
        return sysRolePowerMapper.selectByExample(sysRolePowerExample);
    }

    //判断是否已经存在
    public List<SysRolePower> isThere(SysRolePower sysRolePower){
        SysRolePowerExample sysRolePowerExample = new SysRolePowerExample();
        sysRolePowerExample.createCriteria().andIdEqualTo(sysRolePower.getId());
        return sysRolePowerMapper.selectByExample(sysRolePowerExample);
    }
}