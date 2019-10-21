package com.sdxm.report.service;

import com.anrong.boot.conf.Global;
import com.sdxm.report.dao.DictMapper;
import com.sdxm.report.entity.Dict;
import com.sdxm.report.entity.DictExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author:mengweifan
 * @info:字典表
 */
@Service
@Transactional
public class DictService {

    @Autowired
    private DictMapper dictMapper;


    /**
     * 查询诉求类型
     *
     * @return
     */
    public List<Dict> queryType(String dict) {
        DictExample dictExample = new DictExample();
        dictExample.createCriteria().andParentCodeEqualTo(dict);
        List<Dict> dicts = dictMapper.selectByExample(dictExample);
        return dicts;
    }
}
