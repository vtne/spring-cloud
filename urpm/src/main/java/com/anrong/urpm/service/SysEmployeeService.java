package com.anrong.urpm.service;

import com.anrong.urpm.dao.SysEmployeeMapper;
import com.anrong.urpm.dao.SysLogMapper;
import com.anrong.urpm.entity.SysEmployee;
import com.anrong.urpm.entity.SysEmployeeExample;
import com.anrong.urpm.entity.SysLog;
import com.anrong.urpm.entity.SysLogExample;
import com.anrong.urpm.vo.EmployeeVO;
import com.anrong.urpm.vo.WxUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class SysEmployeeService {

    @Autowired
    private SysEmployeeMapper sysEmployeeMapper;

    @Autowired
    private SysLogMapper sysLogMapper;

    //增加
    public int add(SysEmployee sysEmployee) {
        sysEmployeeMapper.setUTF8MB4();
        int i = sysEmployeeMapper.insert(sysEmployee);
        return i;
    }

    //删除
    public int delete(Integer id) {
        int i = sysEmployeeMapper.deleteByPrimaryKey(id);
        return i;
    }

    //修改
    public int update(SysEmployee sysEmployee) {
        sysEmployeeMapper.setUTF8MB4();
        SysEmployee sysEmployee1 = sysEmployeeMapper.selectByPrimaryKey(sysEmployee.getId());
        if (sysEmployee1.getMobile() != null && sysEmployee1.getMobile().equals(sysEmployee.getMobile())) {
            return 0;
        }
        SysEmployeeExample sysEmployeeExample = new SysEmployeeExample();
        sysEmployeeExample.createCriteria().andIdEqualTo(sysEmployee.getId());
        return sysEmployeeMapper.updateByExampleSelective(sysEmployee, sysEmployeeExample);
    }

    //查询
    public List<SysEmployee> query(Integer page, Integer results) {
        SysEmployeeExample sysEmployeeExample = new SysEmployeeExample();
        sysEmployeeExample.setCount(results);
        sysEmployeeExample.setLimitStart((page - 1) * results);
        return sysEmployeeMapper.selectByExample(sysEmployeeExample);
    }

    //查询
    public PageInfo<SysEmployee> query(Integer page, Integer results, String param, String startTime, String endTime) throws Exception {
        SysEmployeeExample sysEmployeeExample = new SysEmployeeExample();
        sysEmployeeExample.setOrderByClause("create_time desc");
        //时间和查询条件都不为空
        if (startTime != null && !startTime.equals("") && endTime != null && !"".equals(endTime)
                && param != null && !"".equals(param)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date stime = dateFormat.parse(startTime);
            Date etime = dateFormat.parse(endTime);

            //手机号
            sysEmployeeExample.or().andMobileLike("%" + param + "%").andCreateTimeBetween(stime, etime);
            //微信昵称
            sysEmployeeExample.or().andNickNameLike("%" + param + "%").andCreateTimeBetween(stime, etime);
            PageHelper.startPage(page, results);
            List<SysEmployee> sysEmployees = sysEmployeeMapper.selectByExample(sysEmployeeExample);
            return new PageInfo<>(sysEmployees);
        }
        //判断查询事件段是否为空
        if (startTime != null && !startTime.equals("") && endTime != null && !"".equals(endTime)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date stime = dateFormat.parse(startTime);
            Date etime = dateFormat.parse(endTime);
            sysEmployeeExample.createCriteria().andCreateTimeBetween(stime, etime);
            PageHelper.startPage(page, results);
            List<SysEmployee> sysEmployees = sysEmployeeMapper.selectByExample(sysEmployeeExample);
            return new PageInfo<>(sysEmployees);
        }
        //查询条件不为空
        if (param != null && !"".equals(param)) {
            //手机号
            sysEmployeeExample.or().andMobileLike("%" + param + "%");
            //微信昵称
            sysEmployeeExample.or().andNickNameLike("%" + param + "%");
            PageHelper.startPage(page, results);
            List<SysEmployee> sysEmployees = sysEmployeeMapper.selectByExample(sysEmployeeExample);
            return new PageInfo<>(sysEmployees);

        }

        PageHelper.startPage(page, results);
        List<SysEmployee> sysEmployees = sysEmployeeMapper.selectByExample(sysEmployeeExample);
        return new PageInfo<>(sysEmployees);
    }

    //判断是否已经存在
    public List<SysEmployee> isThere(SysEmployee sysEmployee) {
        SysEmployeeExample sysEmployeeExample = new SysEmployeeExample();
        sysEmployeeExample.createCriteria().andOpenIdEqualTo(sysEmployee.getOpenId());
        return sysEmployeeMapper.selectByExample(sysEmployeeExample);
    }

    //查询
    public SysEmployee queryEmployeeByOpenId(String openId) {
        SysEmployeeExample sysEmployeeExample = new SysEmployeeExample();
        sysEmployeeExample.createCriteria().andOpenIdEqualTo(openId);
        List<SysEmployee> list = sysEmployeeMapper.selectByExample(sysEmployeeExample);
        if (list != null && list.size() == 1) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /*根据用户id查询*/
    public SysEmployee queryByUserId(Integer userId) {
        SysEmployeeExample sysEmployeeExample = new SysEmployeeExample();
        sysEmployeeExample.createCriteria().andUserIdEqualTo(userId);
        List<SysEmployee> list = sysEmployeeMapper.selectByExample(sysEmployeeExample);
        if (list != null && list.size() == 1) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /*根据员工id查询*/
    public EmployeeVO queryById2(Integer id) {
        EmployeeVO employeeVO = new EmployeeVO();
        SysEmployee sysEmployee = sysEmployeeMapper.selectByPrimaryKey(id);
        employeeVO.copyPropertiesFromEmployee(sysEmployee);

        SysLogExample sysLogExample = new SysLogExample();
        sysLogExample.createCriteria().andWhoEqualTo(id);

        List<SysLog> sysLogs = sysLogMapper.selectByExample(sysLogExample);
        employeeVO.setList(sysLogs);

        return employeeVO;
    }

    /**
     * 根据员工id查询
     * @param id
     * @return
     */
    public SysEmployee queryById(Integer id) {
        return sysEmployeeMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询用户数量
     *
     * @return
     */
    public long userSum() {
        SysEmployeeExample sysEmployeeExample = new SysEmployeeExample();
        long l = sysEmployeeMapper.countByExample(sysEmployeeExample);
        return l;
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    public int updateEmployee(WxUser user) {
        SysEmployeeExample sysEmployeeExample = new SysEmployeeExample();
        sysEmployeeExample.createCriteria().andOpenIdEqualTo(user.getOpenId());
        SysEmployee sysEmployee = new SysEmployee();
        BeanUtils.copyProperties(user, sysEmployee);
        int i = sysEmployeeMapper.updateByExampleSelective(sysEmployee, sysEmployeeExample);
        return i;
    }
}