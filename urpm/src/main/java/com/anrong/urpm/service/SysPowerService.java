package com.anrong.urpm.service;

import com.anrong.urpm.dao.SysPowerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.anrong.urpm.entity.SysPowerExample;
import com.anrong.urpm.entity.SysPower;

import java.util.List;


@Service
@Transactional
public class SysPowerService {

    @Autowired
    private SysPowerMapper sysPowerMapper;

    //增加
    public int add(SysPower sysPower) {
        int i = sysPowerMapper.insert(sysPower);
        return i;
    }

    //删除
    public int delete(Integer id) {
        int i = sysPowerMapper.deleteByPrimaryKey(id);
        return i;
    }

    //修改
    public int update(SysPower sysPower) {
        //int i = sysPowerMapper.updateByPrimaryKeySelective(sysPower);
        SysPowerExample sysPowerExample = new SysPowerExample();
        sysPowerExample.createCriteria().andIdEqualTo(sysPower.getId());
        return sysPowerMapper.updateByExample(sysPower, sysPowerExample);
    }

    //查询
    public List<SysPower> query(Integer page,Integer results){
        SysPowerExample sysPowerExample = new SysPowerExample();
        sysPowerExample.setCount(results);
        sysPowerExample.setLimitStart((page - 1) * results);
        return sysPowerMapper.selectByExample(sysPowerExample);
    }

    //判断是否已经存在
    public List<SysPower> isThere(SysPower sysPower){
        SysPowerExample sysPowerExample = new SysPowerExample();
        sysPowerExample.createCriteria().andIdEqualTo(sysPower.getId());
        return sysPowerMapper.selectByExample(sysPowerExample);
    }
}