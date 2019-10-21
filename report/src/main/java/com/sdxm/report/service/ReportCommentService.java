package com.sdxm.report.service;

import com.sdxm.report.vo.ReportCommentExt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.sdxm.report.entity.ReportCommentExample;
import com.sdxm.report.entity.ReportComment;
import com.sdxm.report.dao.ReportCommentMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ReportCommentService {

    @Autowired
    private ReportCommentMapper reportCommentMapper;

    //增加
    public int add(ReportComment reportComment) {
        int i = reportCommentMapper.insert(reportComment);
        return i;
    }

    //删除
    public int delete(Integer id) {
        int i = reportCommentMapper.deleteByPrimaryKey(id);
        return i;
    }

    //修改
    public int update(ReportComment reportComment) {
        //int i = reportCommentMapper.updateByPrimaryKeySelective(reportComment);
        ReportCommentExample reportCommentExample = new ReportCommentExample();
        reportCommentExample.createCriteria().andIdEqualTo(reportComment.getId());
        return reportCommentMapper.updateByExample(reportComment, reportCommentExample);
    }

    //查询
    public List<ReportComment> query(Integer page,Integer results){
        ReportCommentExample reportCommentExample = new ReportCommentExample();
        reportCommentExample.setCount(results);
        reportCommentExample.setLimitStart((page - 1) * results);
        return reportCommentMapper.selectByExample(reportCommentExample);
    }

    //判断是否已经存在
    public List<ReportComment> isThere(ReportComment reportComment){
        ReportCommentExample reportCommentExample = new ReportCommentExample();
        reportCommentExample.createCriteria().andIdEqualTo(reportComment.getId());
        return reportCommentMapper.selectByExample(reportCommentExample);
    }

    public List<ReportCommentExt> queryByReportId(Integer reportId){
        List<ReportCommentExt> rclist =  reportCommentMapper.selectByExampleWithEmployee(reportId);
        List<ReportCommentExt>  rcextlist = null;
        if(rclist!=null&&rclist.size()>0){

            HashMap<Integer,List<ReportCommentExt>> map = new HashMap<>();
            for(ReportCommentExt comment:rclist){
                List<ReportCommentExt> list = map.get(comment.getParentId());
                if(list==null){
                    list = new ArrayList<>();
                    map.put(comment.getParentId(),list);
                }
                list.add(comment);
            }

            rcextlist = map.get(0);
            for(ReportCommentExt commentExt:rcextlist){
                setChild(commentExt,map);
            }
        }

        return rcextlist;
    }

    private void setChild(ReportCommentExt commentExt,HashMap<Integer,List<ReportCommentExt>> map){
        List<ReportCommentExt> child = map.get(commentExt.getId());
        if(child!=null && child.size()>0){
            commentExt.setChild(map.get(commentExt.getId()));
            for(ReportCommentExt ext : child){
                setChild(ext,map);
            }
        }
    }
}