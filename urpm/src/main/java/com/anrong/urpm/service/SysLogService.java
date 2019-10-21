package com.anrong.urpm.service;

import com.anrong.urpm.dao.SysLogMapper;
import com.anrong.urpm.entity.SysLogWithBLOBs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.anrong.urpm.entity.SysLogExample;
import com.anrong.urpm.entity.SysLog;

import java.util.List;


@Service
@Transactional
public class SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    //增加
    public int add(SysLogWithBLOBs sysLog) {
        int i = sysLogMapper.insert(sysLog);
        return i;
    }

    //删除
    public int delete(Integer id) {
        int i = sysLogMapper.deleteByPrimaryKey(id);
        return i;
    }

    //修改
    public int update(SysLog sysLog) {
        //int i = sysLogMapper.updateByPrimaryKeySelective(sysLog);
        SysLogExample sysLogExample = new SysLogExample();
        sysLogExample.createCriteria().andIdEqualTo(sysLog.getId());
        return sysLogMapper.updateByExample(sysLog, sysLogExample);
    }

    //查询
    public List<SysLog> query(Integer page,Integer results){
        SysLogExample sysLogExample = new SysLogExample();
        sysLogExample.setCount(results);
        sysLogExample.setLimitStart((page - 1) * results);
        return sysLogMapper.selectByExample(sysLogExample);
    }

    //判断是否已经存在
    public List<SysLog> isThere(SysLog sysLog){
        SysLogExample sysLogExample = new SysLogExample();
        sysLogExample.createCriteria().andIdEqualTo(sysLog.getId());
        return sysLogMapper.selectByExample(sysLogExample);
    }
}