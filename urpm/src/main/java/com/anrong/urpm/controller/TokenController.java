package com.anrong.urpm.controller;

import com.anrong.urpm.util.GetToken;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TokenController{

    @ApiOperation(value = "查询Token")
    @GetMapping("/getToken")
    public Object getToken() {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            if(GetToken.access_tocken!=null){
                resultMap.put("data",GetToken.access_tocken);
                resultMap.put("success", true);
            }else{
                resultMap.put("errmsg", "access_tocken is null");
                resultMap.put("success", false);
            }
        } catch (Exception e) {
            resultMap.put("errmsg", "失败了");
            resultMap.put("success", false);
        }
        return resultMap;
    }

}
