package com.anrong.urpm.service;

import com.anrong.urpm.dao.SysDepartmentMapper;
import com.anrong.urpm.dao.SysEmployeeMapper;
import com.anrong.urpm.dao.SysEnterpriseMapper;
import com.anrong.urpm.dao.SysUserMapper;
import com.anrong.urpm.entity.*;
import com.anrong.urpm.util.MD5Tools;
import com.anrong.urpm.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liuxun
 * @apiNote 登录的业务实现类
 */

@Service
@Transactional
public class LoginService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysEmployeeMapper sysEmployeeMapper;

    @Autowired
    private SysEnterpriseMapper sysEnterpriseMapper;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    public SysUser getUserByAccountAndPassword(String userName, String password, Integer type) {
        String pass = MD5Tools.MD5(password);
        SysUserExample example = new SysUserExample();
//        example.setCount(1);
        SysUserExample.Criteria criteria = example.createCriteria();
        if (type == 0) {
            criteria.andLoginCodeEqualTo(userName);
        }
        if (type == 1) {
            criteria.andMobileEqualTo(userName);
        }

        criteria.andPasswordEqualTo(pass).andDeleteFlagEqualTo(0);

        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
        return sysUsers.size() > 0 ? sysUsers.get(0) : null;
    }

    public List<EmployeeVO> getEnterpriseAndEmployeesByUserId(Integer id) {

        List<EmployeeVO> employeeVOS = new ArrayList<>();
        SysEmployeeExample exampleE = new SysEmployeeExample();
        exampleE.createCriteria().andUserIdEqualTo(id).andDeleteFlagEqualTo(0);
        exampleE.setOrderByClause("update_time desc");
        List<SysEmployee> sysEmployees = sysEmployeeMapper.selectByExample(exampleE);

        for (SysEmployee sysEmployee : sysEmployees) {
            Integer enterpriseid = sysEmployee.getEnterpriseId();
            Integer departmentid = sysEmployee.getDepartmentId();

            SysEnterprise sysEnterprise = sysEnterpriseMapper.selectByPrimaryKey(enterpriseid);
            SysDepartment sysDepartment = sysDepartmentMapper.selectByPrimaryKey(departmentid);

            if (sysEnterprise != null && sysEnterprise.getDeleteFlag() == 0) {// 首选确保企业没有被逻辑删除
                EmployeeVO employeeVO = new EmployeeVO();
                employeeVO.copyPropertiesFromEmployee(sysEmployee);
                employeeVO.setEnterpriseName(sysEnterprise.getName());
                employeeVO.setDeptName(sysDepartment == null ? null : sysDepartment.getName());

                employeeVOS.add(employeeVO);
            }
        }

//        Collections.sort(employeeVOS, new Comparator<EmployeeVO>(){
//
//            @Override
//            public int compare(EmployeeVO o1, EmployeeVO o2) {
//                return o1.getUpdatetime().compareTo(o2.getUpdatetime());
//            }
//        });

        return employeeVOS;
    }

    public void updateTimeToEmployeeById(Integer employeeId) {
        SysEmployee sysEmployee = new SysEmployee();
        sysEmployee.setUpdateTime(new Date());
        sysEmployee.setId(employeeId);
        sysEmployeeMapper.updateByPrimaryKeySelective(sysEmployee);
    }
}
