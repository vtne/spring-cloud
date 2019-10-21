package com.anrong.urpm.controller;

import com.anrong.urpm.dao.SysEmployeeMapper;
import com.anrong.urpm.dao.SysUserMapper;
import com.anrong.urpm.entity.SysEmployee;
import com.anrong.urpm.entity.SysLogWithBLOBs;
import com.anrong.urpm.entity.SysUser;
import com.anrong.urpm.service.SysEmployeeService;
import com.anrong.urpm.service.SysLogService;
import com.anrong.urpm.util.JacksonUtil;
import com.anrong.urpm.util.MD5Tools;
import com.anrong.urpm.util.WXBizDataCrypt;
import com.anrong.urpm.vo.EmployeeVO;
import com.anrong.urpm.vo.WxUser;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/employee")
public class SysEmployeeController {

    @Autowired
    SysEmployeeService sysEmployeeService;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysLogService sysLogService;

    @Autowired
    SysEmployeeMapper sysEmployeeMapper;

    @ApiOperation(value = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "results", value = "每页的总条数", required = true, paramType = "query"),
    })
    @GetMapping("/query")
    public Object query(@RequestParam Integer page, @RequestParam Integer results,
                        @RequestParam(required = false) String param,
                        @RequestParam(required = false) String startTime,
                        @RequestParam(required = false) String endTime) {
        Map<String, Object> resultMap = new HashMap<>();
        if (startTime != null && !startTime.equals("")) {
            if (endTime == null || "".equals(endTime)) {
                resultMap.put("errmsg", "结束时间不能为空!");
                resultMap.put("errcode", "Error001");
                resultMap.put("success", false);
                return resultMap;
            }
        }
        if (endTime != null && !endTime.equals("")) {
            if (startTime == null || "".equals(startTime)) {
                resultMap.put("errmsg", "开始时间不能为空!");
                resultMap.put("errcode", "Error001");
                resultMap.put("success", false);
                return resultMap;
            }
        }
        try {
            PageInfo<SysEmployee> list = sysEmployeeService.query(page, results, param, startTime, endTime);
            resultMap.put("data", list);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    @ApiOperation("新增")
    @PostMapping("/create")
    public Object create(@RequestBody SysEmployee sysEmployee) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            SysUser sysUser = new SysUser();
            sysUser.setLoginCode(sysEmployee.getName());
            sysUser.setMobile(sysEmployee.getMobile());
            sysUser.setCreateTime(new Date());
            sysUser.setUpdateTime(new Date());
            /*设置默认密码为123456*/
            sysUser.setPassword(MD5Tools.MD5("123456"));
            sysUser.setDeleteFlag(0);
            int success = sysUserMapper.insertSelective(sysUser);

            if (success > 0) {
                List<SysEmployee> sysEmployees = sysEmployeeService.isThere(sysEmployee);
                if (sysEmployees.size() > 0) {
                    resultMap.put("errmsg", "此条信息已经存在");
                    resultMap.put("success", false);
                    return resultMap;
                }
                success = sysEmployeeService.add(sysEmployee);
            }

            if (success > 0) {
                resultMap.put("success", true);
                resultMap.put("info", "新增成功");
                return resultMap;
            } else {
                resultMap.put("errmsg", "新增失败");
                resultMap.put("success", false);
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("errmsg", "新增失败");
            resultMap.put("success", false);
            return resultMap;
        }

    }

    @ApiOperation("新增和更新人员信息")
    @PostMapping("/createByWx")
    public Object createByWx(@RequestBody WxUser user) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            System.out.println("getAppId:" + user.getAppId());
            System.out.println("getSessionKey:" + user.getSessionKey());
            System.out.println("getEncryptedData:" + user.getEncryptedData());
            System.out.println("getIv:" + user.getIv());
            WXBizDataCrypt biz = new WXBizDataCrypt(user.getAppId(), user.getSessionKey());
            //json={"phoneNumber":"18610348075","purePhoneNumber":"18610348075","countryCode":"86","watermark":{"timestamp":1535545065,"appid":"wx90c686a8d5f38c1c"}}
            String json = biz.decryptData(user.getEncryptedData(), user.getIv());
            System.out.println("json=" + json);
            try {
                LinkedHashMap map = JacksonUtil.readValue(json, LinkedHashMap.class);
                if (map != null) {
                    user.setMobile((String) map.get("phoneNumber"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                resultMap.put("errmsg", "解密后data非json格式," + json);
                resultMap.put("success", false);
                return resultMap;
            }
            Date date = new Date();
            //为1 新增人员
            if ("1".equals(user.getFlag())) {
                user.setCreateTime(date);
                user.setUpdateTime(date);
                user.setDeleteFlag(0);
                List<SysEmployee> sysEmployees = sysEmployeeService.isThere(user);
                if (sysEmployees.size() > 0) {
                    resultMap.put("errmsg", "此条信息已经存在");
                    resultMap.put("success", false);
                    return resultMap;
                }
                int success = sysEmployeeService.add(user);
                if (success > 0) {
                    resultMap.put("success", true);
                    resultMap.put("info", "新增成功");
                    return resultMap;
                } else {
                    resultMap.put("errmsg", "新增失败");
                    resultMap.put("success", false);
                    return resultMap;
                }
            } else if ("0".equals(user.getFlag())) { //更新人员
                user.setUpdateTime(date);
                List<SysEmployee> sysEmployees = sysEmployeeService.isThere(user);
                if (sysEmployees.size() <= 0) {
                    resultMap.put("errmsg", "此条信息不存在");
                    resultMap.put("success", false);
                    return resultMap;
                }
                int success = sysEmployeeService.updateEmployee(user);
                if (success > 0) {
                    resultMap.put("success", true);
                    resultMap.put("info", "更新成功");
                    return resultMap;
                } else {
                    resultMap.put("errmsg", "更新失败");
                    resultMap.put("success", false);
                    return resultMap;
                }
            } else {
                resultMap.put("success", false);
                resultMap.put("info", "flag不能为空!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            String errmsg = user.getFlag().equals("1") ? "新增" : "更新";
            resultMap.put("errmsg", errmsg + "失败");
            resultMap.put("success", false);
            return resultMap;
        }
        return resultMap;
    }

    @ApiOperation("更新手机号")
    @PostMapping("/update")
    public Object update(@RequestBody SysEmployee sysEmployee) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<SysEmployee>  sysEmployees = sysEmployeeService.isThere(sysEmployee);
            if (sysEmployees.size() == 0) {
                resultMap.put("errmsg", "此条信息不存在");
                resultMap.put("success", false);
                return resultMap;
            }
            int success = sysEmployeeService.update(sysEmployee);
            if (success == 0) {
                resultMap.put("success", false);
                resultMap.put("info", "修改的手机号和原手机号相同!!");
                return resultMap;
            }
            if (success > 0) {
                resultMap.put("success", true);
                resultMap.put("info", "更新采集项成功");
                return resultMap;
            } else {
                resultMap.put("errmsg", "更新失败");
                resultMap.put("success", false);
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("success", false);
            return resultMap;
        }
    }
    @ApiOperation("解冻")
    @GetMapping("/updateFlag0/{id}")
    public Object updateFlag0(@PathVariable Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            SysEmployee sysEmployee = sysEmployeeMapper.selectByPrimaryKey(id);
            sysEmployee.setDeleteFlag(0);
            sysEmployeeMapper.setUTF8MB4();
            int success= sysEmployeeMapper.updateByPrimaryKeySelective(sysEmployee);
            if (success > 0) {
                resultMap.put("success", true);
                resultMap.put("info", "解冻成功");
                return resultMap;
            } else {
                resultMap.put("errmsg", "解冻失败");
                resultMap.put("success", false);
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("success", false);
            return resultMap;
        }
    }
    @ApiOperation("冻结")
    @GetMapping("/updateFlag1/{id}")
    public Object updateFlag1(@PathVariable Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            SysEmployee sysEmployee = sysEmployeeMapper.selectByPrimaryKey(id);
            sysEmployee.setDeleteFlag(1);
            sysEmployeeMapper.setUTF8MB4();
            int success= sysEmployeeMapper.updateByPrimaryKeySelective(sysEmployee);
            if (success > 0) {
                resultMap.put("success", true);
                resultMap.put("info", "冻结成功");
                return resultMap;
            } else {
                resultMap.put("errmsg", "冻结失败");
                resultMap.put("success", false);
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("success", false);
            return resultMap;
        }
    }
    @ApiOperation("删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query"),
    })
    @PostMapping("/delete")
    public Object delete(@RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int success = sysEmployeeService.delete(id);
            if (success > 0) {
                resultMap.put("success", true);
                resultMap.put("info", "删除采集项成功");
                return resultMap;
            } else {
                resultMap.put("errmsg", "该采集项不存在或已删除");
                resultMap.put("success", false);
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("errmsg", "删除失败了");
            resultMap.put("success", false);
            return resultMap;
        }

    }

    @ApiOperation(value = "根据OpenId查询员工")
    @GetMapping("/queryEmployeeByOpenId")
    public Object queryEmployeeByOpenId(@RequestParam String openId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            SysEmployee sysEmployee = sysEmployeeService.queryEmployeeByOpenId(openId);
            if(sysEmployee!=null){
                SysLogWithBLOBs syslog = new SysLogWithBLOBs();
                syslog.setCreateTime(new Date());
                syslog.setWho(sysEmployee.getId());
                syslog.setData1("Login");
                sysLogService.add(syslog);
                resultMap.put("data",sysEmployee);
                resultMap.put("success", true);
            }else{
                resultMap.put("errmsg", "not found");
                resultMap.put("errcode", "Error002");
                resultMap.put("success", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
        }
        return resultMap;
    }

    @ApiOperation(value = "根据Id查询员工")
    @GetMapping("/queryEmployeeById2")
    public Object queryEmployeeById2(@RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            EmployeeVO sysEmployee = sysEmployeeService.queryById2(id);
            if(sysEmployee!=null){
                resultMap.put("data",sysEmployee);
                resultMap.put("success", true);
            }else{
                resultMap.put("errmsg", "not found");
                resultMap.put("errcode", "Error002");
                resultMap.put("success", false);
            }
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
        }
        return resultMap;
    }

    @ApiOperation(value = "根据Id查询员工")
    @GetMapping("/queryEmployeeById")
    public Object queryEmployeeById(@RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            SysEmployee sysEmployee = sysEmployeeService.queryById(id);
            if(sysEmployee!=null){
                resultMap.put("data",sysEmployee);
                resultMap.put("success", true);
            }else{
                resultMap.put("errmsg", "not found");
                resultMap.put("errcode", "Error002");
                resultMap.put("success", false);
            }
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 查询用户数量
     *
     * @return
     */
    @ApiOperation(value = "查询用户数量")
    @GetMapping("/queryUserSum")
    public Object queryEmployeeById() {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            long i = sysEmployeeService.userSum();
            resultMap.put("data", i);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
        }
        return resultMap;
    }
}