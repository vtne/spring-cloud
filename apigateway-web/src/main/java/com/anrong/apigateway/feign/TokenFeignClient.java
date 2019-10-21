package com.anrong.apigateway.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * 标明feign调用的微服务名称
 */
//通过@ FeignClient（“服务名”），来指定调用哪个服务。
// 比如在代码中调用了service-hi服务的“/hi”接口，还可以使用url参数指定一个URL
// fallback  出现错误回调类
@FeignClient(name = "urpm-wx",fallback = TokenFallback.class)
public interface TokenFeignClient {
    /**
     * 对应service-user微服务中的URL
     * @return
     */
    @GetMapping("/getTokenJwt")
    Object queryJWT();
}
