package com.anrong.urpm.service;

import com.anrong.urpm.dao.SysRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.anrong.urpm.entity.SysRoleExample;
import com.anrong.urpm.entity.SysRole;

import java.util.List;


@Service
@Transactional
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    //增加
    public int add(SysRole sysRole) {
        int i = sysRoleMapper.insert(sysRole);
        return i;
    }

    //删除
    public int delete(Integer id) {
        int i = sysRoleMapper.deleteByPrimaryKey(id);
        return i;
    }

    //修改
    public int update(SysRole sysRole) {
        //int i = sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        SysRoleExample sysRoleExample = new SysRoleExample();
        sysRoleExample.createCriteria().andIdEqualTo(sysRole.getId());
        return sysRoleMapper.updateByExample(sysRole, sysRoleExample);
    }

    //查询
    public List<SysRole> query(Integer page,Integer results){
        SysRoleExample sysRoleExample = new SysRoleExample();
        sysRoleExample.setCount(results);
        sysRoleExample.setLimitStart((page - 1) * results);
        return sysRoleMapper.selectByExample(sysRoleExample);
    }

    //判断是否已经存在
    public List<SysRole> isThere(SysRole sysRole){
        SysRoleExample sysRoleExample = new SysRoleExample();
        sysRoleExample.createCriteria().andIdEqualTo(sysRole.getId());
        return sysRoleMapper.selectByExample(sysRoleExample);
    }
}