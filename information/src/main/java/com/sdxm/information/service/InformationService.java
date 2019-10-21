package com.sdxm.information.service;

import com.anrong.boot.conf.Global;
import com.anrong.boot.util.ImageAddress;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdxm.information.dao.AdmireMapper;
import com.sdxm.information.dao.CommentMapper;
import com.sdxm.information.dao.InformationMapper;
import com.sdxm.information.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class InformationService {

    @Autowired
    private InformationMapper informationMapper;

    @Autowired
    private AdmireMapper AdmireMapper;

    @Autowired
    private CommentMapper CommentMapper;

    /**
     * 增加
     *
     * @param information
     * @return
     */
    public int add(Information information) {
        //新增的都是未审核
        information.setAudit(Global.audit0);
        //截取图片路径的IP
        if (information.getLogoImage() != null && !"".equals(information.getLogoImage())) {
            String s = ImageAddress.extractString(information.getLogoImage());
            information.setLogoImage(s);
        }
        int i = informationMapper.insert(information);
        return i;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public int delete(Integer id) {
        int i = informationMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            CommentExample example = new CommentExample();
            example.createCriteria().andInfoIdEqualTo(id);
            CommentMapper.deleteByExample(example);
        }
        return i;
    }

    /**
     * 修改
     * @param information
     * @return
     */
    public int update(Information information) {
        InformationExample informationExample = new InformationExample();
        informationExample.createCriteria().andIdEqualTo(information.getId());
        if (information.getLogoImage() != null && !information.getLogoImage().equals("")) {
            information.setLogoImage(ImageAddress.extractString(information.getLogoImage()));
        }
        return informationMapper.updateByExampleSelective(information, informationExample);
    }

    /**
     * 查询
     * @param type
     * @param page
     * @param results
     * @return
     */
    public List<Information> query(String type, Integer page, Integer results, HttpServletRequest request) {
        InformationExample informationExample = new InformationExample();
        if (type != null && !type.equals("null")) {
            informationExample.createCriteria().andTypeEqualTo(type);
        }
        List<Information> informationList = informationMapper.selectByExample(informationExample);
        if (informationList != null && informationList.size() > 0) {
            for (Information information : informationList) {
                if (information.getLogoImage() != null && !information.getLogoImage().equals("")) {
                    information.setLogoImage(ImageAddress.getUrl(request) + information.getLogoImage());
                }
            }
        }
        return informationList;
    }

    /**
     * 查询
     * @param type
     * @param pageNum
     * @param pageSize
     * @param info
     * @param status
     * @return
     */
    public PageInfo<Information> query(String type, Integer pageNum, Integer pageSize, String info, String status, HttpServletRequest request) {
        InformationExample informationExample = new InformationExample();
        InformationExample.Criteria criteria = informationExample.createCriteria();
        if (type != null && !type.equals("")) {
            criteria.andTypeEqualTo(type);
        }

        if (info != null && !"".equals(info)) {
            criteria.andSummaryLike("%" + info + "%");
        }

        if (status != null && !"".equals(status)) {
            criteria.andAuditEqualTo(Integer.parseInt(status));
        }
        informationExample.setOrderByClause("up_time desc");
        PageHelper.startPage(pageNum, pageSize);
        List<Information> informations = informationMapper.selectByExample(informationExample);
        if (informations != null && informations.size() > 0) {
            for (Information information : informations) {
                if (null != information.getLogoImage() && !"".equals(information.getLogoImage()) && !"/".equals(information.getLogoImage())) {
                    information.setLogoImage(ImageAddress.getUrl(request) + information.getLogoImage());
                }
            }
        }
        return new PageInfo<>(informations);
    }

    /**
     * 判断是否存在
     * @param information
     * @return
     */
    public List<Information> isThere(Information information) {
        InformationExample informationExample = new InformationExample();
        informationExample.createCriteria().andIdEqualTo(information.getId());
        return informationMapper.selectByExample(informationExample);
    }

    /**
     * 详情
     * @param id
     * @param id
     * @return
     */
    public Information detail(Integer id, HttpServletRequest request) {
        Information information = informationMapper.selectByPrimaryKey(id);
        if (information != null && information.getLogoImage() != null && !"".equals(information.getLogoImage())) {
            information.setLogoImage(ImageAddress.getUrl(request) + information.getLogoImage());
        }
        return information;
    }

    public List<Information> search(String text) {
        InformationExample informationExample = new InformationExample();
        informationExample.createCriteria().andDetailLike("%" + text + "%");
        informationExample.or().andSummaryLike("%" + text + "%");
        return informationMapper.selectByExample(informationExample);
    }


    /**
     * 点赞 或者取消赞
     *
     * @param openId
     * @param infoId
     * @return
     */
    public int admire(String openId, Integer infoId) {
        AdmireExample example = new AdmireExample();
        AdmireExample.Criteria criteria = example.createCriteria();
        criteria.andInfoIdEqualTo(infoId);
        criteria.andOpenIdEqualTo(openId);
        int i = AdmireMapper.countByExample(example);
        if (i > 0) {
            int i1 = AdmireMapper.deleteByExample(example);
            return i1;
        } else {
            Admire admire = new Admire();
            admire.setOpenId(openId);
            admire.setInfoId(infoId);
            return AdmireMapper.insert(admire);
        }
    }

    /**
     * 添加评论
     *
     * @param comment
     * @return
     */
    public int infoComment(Comment comment) {
        comment.setCreateTime(new Date());
        comment.setAudit(Global.audit0);
        return CommentMapper.insert(comment);
    }

    /**
     * 查询上墙的新闻
     *
     * @return
     */
    public List<Information> queryUpWall(HttpServletRequest request) {
        InformationExample example = new InformationExample();
        example.createCriteria().andUpWallEqualTo(1);
        List<Information> informationList = informationMapper.selectByExample(example);
        if (informationList != null && informationList.size() > 0) {
            for (Information information : informationList) {
                if (information.getLogoImage() != null && !information.getLogoImage().equals("")) {
                    information.setLogoImage(ImageAddress.getUrl(request) + information.getLogoImage());
                }
            }
        }
        return informationList;
    }

    public int infoCommentDelete(Integer id) {
        int i = CommentMapper.deleteByPrimaryKey(id);
        return i;
    }

    /**
     * 查询评论和点赞数量
     * @param id
     * @param openId
     * @return
     */
    public HashMap<String, Object> commentAndAdmireCount(Integer id, String openId) {
        HashMap<String, Object> map = new HashMap<>();
        AdmireExample admireExample = new AdmireExample();
        AdmireExample.Criteria criteria = admireExample.createCriteria();
        criteria.andInfoIdEqualTo(id);
        int i = AdmireMapper.countByExample(admireExample);
        map.put("count", i);
        criteria.andOpenIdEqualTo(openId);
        int status = AdmireMapper.countByExample(admireExample);
        map.put("status", false);
        if (status == 1) {
            map.put("status", true);
        }
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria1 = commentExample.createCriteria();
        //criteria1.andOpenIdEqualTo(openId);
        criteria1.andInfoIdEqualTo(id);
        commentExample.setOrderByClause("create_time desc");
        criteria1.andAuditEqualTo(Global.audit1);
        List<Comment> comments = CommentMapper.selectByExample(commentExample);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Comment comment : comments) {
            comment.setDateTime(format.format(comment.getCreateTime()));
        }
        map.put("comments", comments);
        return map;
    }

    /**
     * 评论列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<Comment> queryInfoComment(Integer audit, String param, Integer pageNum, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        //判断条件是否为空
        if (audit != null) {
            map.put("audit", audit);
        }
        if (param != null && !"".equals(param)) {
            map.put("param", param);
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = CommentMapper.queryByLike(map);
        return new PageInfo<>(comments);
    }


    /**
     * 修改评论状态
     *
     * @param comment
     * @return
     */
    public int updateInfoComment(Comment comment) {
        comment.setUpdateTime(new Date());
        int i = CommentMapper.updateByPrimaryKeySelective(comment);
        return i;
    }

    /**
     * 修改新闻发布状态
     *
     * @param information
     * @return
     */
    public int updateStatus(Information information) {
        System.out.println("audit      ************　　" + information.getAudit());
        if (information.getAudit() == 1) {
            information.setUpTime(new Date());
        }
        int i = informationMapper.updateByPrimaryKeySelective(information);
        return i;
    }
}