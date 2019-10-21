package com.anrong.urpm.service;

import com.anrong.urpm.dao.SysDepartmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.anrong.urpm.entity.SysDepartmentExample;
import com.anrong.urpm.entity.SysDepartment;

import java.util.List;


@Service
@Transactional
public class SysDepartmentService {

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    //增加
    public int add(SysDepartment sysDepartment) {
        int i = sysDepartmentMapper.insert(sysDepartment);
        return i;
    }

    //删除
    public int delete(Integer id) {
        int i = sysDepartmentMapper.deleteByPrimaryKey(id);
        return i;
    }

    //修改
    public int update(SysDepartment sysDepartment) {
        //int i = sysDepartmentMapper.updateByPrimaryKeySelective(sysDepartment);
        SysDepartmentExample sysDepartmentExample = new SysDepartmentExample();
        sysDepartmentExample.createCriteria().andIdEqualTo(sysDepartment.getId());
        return sysDepartmentMapper.updateByExample(sysDepartment, sysDepartmentExample);
    }

    //查询
    public List<SysDepartment> query(Integer page,Integer results){
        SysDepartmentExample sysDepartmentExample = new SysDepartmentExample();
        sysDepartmentExample.setCount(results);
        sysDepartmentExample.setLimitStart((page - 1) * results);
        return sysDepartmentMapper.selectByExample(sysDepartmentExample);
    }

    //判断是否已经存在
    public List<SysDepartment> isThere(SysDepartment sysDepartment){
        SysDepartmentExample sysDepartmentExample = new SysDepartmentExample();
        sysDepartmentExample.createCriteria().andIdEqualTo(sysDepartment.getId());
        return sysDepartmentMapper.selectByExample(sysDepartmentExample);
    }
}