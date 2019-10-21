package com.anrong.apigateway.controller;

import com.anrong.apigateway.cache.CacheKit;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
public class Controller {

    @PostMapping("/insertJWT")
    @ResponseBody
    public boolean cacheJwt(@RequestParam(value = "jwt", required = true) String jwt,
                            @RequestParam(value = "userId", required = true) String userId) {
        try {
            CacheKit.put("jwt", userId, jwt);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/logout")
    @ResponseBody
    public boolean logout(@RequestParam(value = "userId", required = true) String userId) {
        try {
            CacheKit.remove("jwt", userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
