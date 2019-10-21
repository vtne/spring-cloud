package com.anrong.urpm.feign;

import org.springframework.stereotype.Component;

@Component
public class TokenFallback implements TokenFeignClient {
    @Override
    public Object insertJWT(String jwt, String userId) {
        return "服务调用失败";
    }

    @Override
    public Object logout(String userId) {
        return "服务调用失败";
    }

}