package com.anrong.urpm.controller;

import com.alibaba.fastjson.JSON;
import com.anrong.urpm.entity.SysUser;
import com.anrong.urpm.feign.TokenFeignClient;
import com.anrong.urpm.service.LoginService;
import com.anrong.urpm.service.RedisService;
import com.anrong.urpm.service.SysEmployeeService;
import com.anrong.urpm.service.TokenSerevice;
import com.anrong.urpm.vo.LoginUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @apiNote 处理登录的控制器
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TokenSerevice tokenSerevice;

    @Autowired
    SysEmployeeService sysEmployeeService;

    @Autowired
    private RedisService redisService;

    @Resource
    private TokenFeignClient tokenFeignClient;

    @GetMapping("/getMenu")
    public Object getMenu(@RequestHeader String token) {
        System.out.println(token);
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();


        List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("text", "开发配置");
        map3.put("icon", "icon-speedometer");
        map3.put("link", "/dashboard");

        Map<String, Object> map4 = new HashMap<String, Object>();

        map4.put("text", "模块管理");
        map4.put("link", "/user/list");
        list2.add(map4);

        map4 = new HashMap<String, Object>();
        map4.put("text", "应用管理");
        map4.put("link", "/dashboard/workplace");
        list2.add(map4);

        map4 = new HashMap<String, Object>();
        map4.put("text", "值列表管理");
        map4.put("link", "/dashboard/v1");
        list2.add(map4);

        map3.put("children", list2);
        list.add(map3);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("text", "主导航");
        map2.put("children", list);

        List<Map<String, Object>> list0 = new ArrayList<Map<String, Object>>();
        list0.add(map2);
        map.put("menu", list0);

        return map;

        //return "{\"user\":{\"name\":\"Admin\",\"avatar\":\"./assets/img/zorro.svg\",\"email\":\"cipchk@qq.com\"},\"menu\":[{\"text\":\"主导航\",\"children\":[{\"text\":\"开发配饰\",\"link\":\"/dashboard\",\"icon\":\"icon-speedometer\",\"children\":[{\"text\":\"模块管理\",\"link\":\"/user/list\"},{\"text\":\"应用管理\",\"link\":\"/dashboard/workplace\"},{\"text\":\"值列表管理\",\"link\":\"/dashboard/workplace\"}]}]}]}";
    }


    @ApiOperation("登录提交接口")
    @RequestMapping(value = "/login", method = {RequestMethod.POST/*, RequestMethod.GET*/})
    public Object login(@RequestBody LoginUser inJson, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<>();


        if (inJson.getLoginType() == 0 && inJson.getUserName() == null) {
            resultMap.put("errmsg", "登录账号不能为空");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        else if (inJson.getLoginType() == 1 && inJson.getMobile() == null) {
            resultMap.put("errmsg", "手机号不能为空");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        if (inJson.getPassword() == null || inJson.getPassword().trim().length() == 0) {
            resultMap.put("errmsg", "密码不能为空");
            resultMap.put("errcode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        String userName = inJson.getLoginType() == 0 ? inJson.getUserName().trim() : inJson.getMobile().trim();

        String password = inJson.getPassword().trim();

        // 根据用户名和密码获取用户
        SysUser sysUser = loginService.getUserByAccountAndPassword(userName, password, inJson.getLoginType());
        if (sysUser == null) {
            resultMap.put("errmsg", "该用户不存在");
            resultMap.put("errcode", "Error002");
            resultMap.put("success", false);
            return resultMap;
        }

        if(sysEmployeeService.queryByUserId(sysUser.getId()).getDeleteFlag()==1){
            resultMap.put("errmsg", "该用户已被禁用！");
            resultMap.put("success", false);
            return resultMap;
        }
        // 查询当前用户对应所有员工的所在的所有公司的所有的权限URL
        //ActiveUserVO activeUserVO = new ActiveUserVO();
        //activeUserVO.setSysUser(sysUser);


        // 查询该用户在的所有的企业以及在企业中的员工信息
        //List<EmployeeVO> enterpriseVOS = loginService.getEnterpriseAndEmployeesByUserId(sysUser.getId());
        // activeUserVO.setEmployeeId(enterpriseVOS.get(0).getId());
        //activeUserVO.setEmployeeVO(enterpriseVOS.get(0));


        String token = tokenSerevice.genToken("user", JSON.toJSONString(sysUser));

        resultMap.put("success", true);
        resultMap.put("token", token);
        resultMap.put("data", sysUser);

        //redis中缓存token
        redisService.set(sysUser.getId().toString(), token, 3600);
        response.setHeader("userId", sysUser.getId().toString());
        response.setHeader("token", token);
        System.out.println(resultMap);
        return resultMap;
    }

   /* // 解密Token
    @ApiOperation("解密Token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "Token字符串", required = true, paramType = "path"),
    })
    @RequestMapping(value = "/deToken", method = {RequestMethod.GET})
//    @GetMapping("/deToken")
    public String decodeToken(String token) {
        return tokenSerevice.deCodeToken(token, "user");
    }

    @RequestMapping("/enterprises")
    public Object testInfos(Integer userId) {
        return loginService.getEnterpriseAndEmployeesByUserId(2);
    }*/

    /**
     * 后台管理退出接口
     *
     * @param id
     * @return
     */
    @ApiOperation("后台管理退出接口")
    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public Object logout(@RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            boolean b = (boolean) tokenFeignClient.logout(id.toString());
            if (b) {
                resultMap.put("success", true);
                resultMap.put("data", "退出成功!");
                return resultMap;
            } else {
                resultMap.put("success", true);
                resultMap.put("data", "session超时!");
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("success", true);
            resultMap.put("data", "session超时!");
            return resultMap;
        }

    }


}
