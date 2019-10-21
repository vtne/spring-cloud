package com.anrong.urpm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.anrong.urpm.entity.SysUserExample;
import com.anrong.urpm.entity.SysUser;
import com.anrong.urpm.dao.SysUserMapper;

import java.util.List;


@Service
@Transactional
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    //增加
    public int add(SysUser sysUser) {
        int i = sysUserMapper.insert(sysUser);
        return i;
    }

    //删除
    public int delete(Integer id) {
        int i = sysUserMapper.deleteByPrimaryKey(id);
        return i;
    }

    //修改
    public int update(SysUser sysUser) {
        //int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria().andIdEqualTo(sysUser.getId());
        return sysUserMapper.updateByExample(sysUser, sysUserExample);
    }

    //查询
    public List<SysUser> query(Integer page,Integer results){
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.setCount(results);
        sysUserExample.setLimitStart((page - 1) * results);
        return sysUserMapper.selectByExample(sysUserExample);
    }

    //判断是否已经存在
    public List<SysUser> isThere(SysUser sysUser){
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria().andIdEqualTo(sysUser.getId());
        return sysUserMapper.selectByExample(sysUserExample);
    }
}