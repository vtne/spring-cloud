package com.sdxm.information.service;

import com.sdxm.information.dao.ExplainMapper;
import com.sdxm.information.entity.Explain;
import com.sdxm.information.entity.ExplainExample;
import com.sdxm.information.entity.Information;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExplainService {

    @Autowired
    private ExplainMapper explainMapper;

    //查询
    public List<Explain> query() {
        ExplainExample explainExample = new ExplainExample();

        return explainMapper.selectByExample(explainExample);

    }

    public Explain queryById(Integer id) {
        return explainMapper.selectByPrimaryKey(id);
    }
}
