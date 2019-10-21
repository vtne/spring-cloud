package com.sdxm.dictionary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.sdxm.dictionary.entity.ValuelistExample;
import com.sdxm.dictionary.entity.Valuelist;
import com.sdxm.dictionary.dao.ValuelistMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ValuelistService {

    @Autowired
    private ValuelistMapper valuelistMapper;

    //增加
    public int add(Valuelist valuelist) {
        int i = valuelistMapper.insert(valuelist);
        return i;
    }

    //删除
    public int delete(Integer id) {
        int i = valuelistMapper.deleteByPrimaryKey(id);
        return i;
    }

    //修改
    public int update(Valuelist valuelist) {
        //int i = valuelistMapper.updateByPrimaryKeySelective(valuelist);
        ValuelistExample valuelistExample = new ValuelistExample();
        valuelistExample.createCriteria().andIdEqualTo(valuelist.getId());
        return valuelistMapper.updateByExample(valuelist, valuelistExample);
    }

    //查询
    public List<Valuelist> query(Integer page,Integer results){
        ValuelistExample valuelistExample = new ValuelistExample();
        valuelistExample.setCount(results);
        valuelistExample.setLimitStart((page - 1) * results);
        return valuelistMapper.selectByExample(valuelistExample);
    }

    //判断是否已经存在
    public List<Valuelist> isThere(Valuelist valuelist){
        ValuelistExample valuelistExample = new ValuelistExample();
        valuelistExample.createCriteria().andIdEqualTo(valuelist.getId());
        return valuelistMapper.selectByExample(valuelistExample);
    }

    //查询
    public Valuelist queryByCode(String code){
        ValuelistExample valuelistExample = new ValuelistExample();
        valuelistExample.createCriteria().andCodeEqualTo(code);
        List<Valuelist> list = valuelistMapper.selectByExample(valuelistExample);
        if(list!=null && list.size()==1){
            return list.get(0);
        }else{
            return null;
        }
    }

    //查询
    public List<Valuelist> queryByPCode(String code){
        ValuelistExample valuelistExample = new ValuelistExample();
        valuelistExample.createCriteria().andParentCodeEqualTo(code);
        List<Valuelist> list = valuelistMapper.selectByExample(valuelistExample);
        return list;
    }
}