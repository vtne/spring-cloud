package com.sdxm.information.service;

import com.sdxm.information.dao.ShowLocationMapper;
import com.sdxm.information.entity.ShowLocationExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShowLocationService {

    @Autowired
    private ShowLocationMapper showLocationMapper;


    public List queryLocation() {
        ShowLocationExample showLocationExample = new ShowLocationExample();
        return showLocationMapper.selectByExample(showLocationExample);
    }
}
