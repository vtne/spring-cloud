package com.sdxm.information.service;

import com.anrong.boot.util.ImageAddress;
import com.sdxm.information.dao.ManagerMapper;
import com.sdxm.information.entity.Manager;
import com.sdxm.information.entity.ManagerExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ManagerService {

    @Autowired
    private ManagerMapper managerMapper;


    /**
     * 创建
     *
     * @param manager
     * @return
     */
    public int create(Manager manager) {
        String pic1Path = manager.getPic1Path();
        if (pic1Path != null) {
            manager.setPic1Path(ImageAddress.extractString(pic1Path));
        }
        String pic2Path = manager.getPic2Path();
        if (pic2Path != null) {
            manager.setPic2Path(ImageAddress.extractString(pic2Path));
        }
        manager.setCreateTime(new Date());
        int i = managerMapper.insert(manager);
        return i;
    }

    /**
     * 通过openid 查询拥有的卡片
     *
     * @param openId
     * @return
     */
    public List<Manager> query(String openId, HttpServletRequest request) {
        if ("".equals(openId) || openId == null) {
            return new ArrayList<Manager>();
        }
        ManagerExample example = new ManagerExample();
        example.createCriteria().andOpenIdEqualTo(openId);
        List<Manager> list = managerMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            for (Manager manager : list) {
                if (manager.getPic1Path() != null)
                    manager.setPic1Path(ImageAddress.getUrl(request) + manager.getPic1Path());
                if (manager.getPic2Path() != null)
                    manager.setPic2Path(ImageAddress.getUrl(request) + manager.getPic2Path());
            }
        }
        return list;
    }

    public int delete(Integer id) {
        int i = managerMapper.deleteByPrimaryKey(id);
        return i;
    }
}
