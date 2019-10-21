package com.sdxm.information.service;

import com.anrong.boot.util.ImageAddress;
import com.sdxm.information.dao.HelpMapper;
import com.sdxm.information.entity.Help;
import com.sdxm.information.entity.HelpExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HelpService {

    @Autowired
    private HelpMapper helpMapper;


    public int create(Help help) {
        help.setCreateTime(new Date());
        List<String> paths = help.getPaths();
        if (paths != null && paths.size() > 0) {
            String pic = "";
            for (String path : paths) {
                String s = ImageAddress.extractString(path);
                pic = pic + s + ",";
            }
            help.setPicPath(pic);
        }

        return helpMapper.insert(help);
    }

    /**
     * 查询我的回复
     *
     * @param openId
     * @return
     */
    public List<Help> queryReply(String openId) {

        HelpExample example = new HelpExample();
        example.createCriteria().andOpenIdEqualTo(openId);
        example.setOrderByClause("create_time desc");
        List<Help> list = helpMapper.selectByExample(example);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Help help : list) {
            help.setDataTime(format.format(help.getCreateTime()));
        }
        return list;
    }
}
