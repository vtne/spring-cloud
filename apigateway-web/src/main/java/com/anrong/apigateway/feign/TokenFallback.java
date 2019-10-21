package com.anrong.apigateway.feign;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TokenFallback implements TokenFeignClient {
    @Override
    public Object queryJWT() {
        return "服务调用失败";
    }
}