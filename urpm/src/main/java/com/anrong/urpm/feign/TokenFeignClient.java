package com.anrong.urpm.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 标明feign调用的微服务名称
 */
//通过@ FeignClient（“服务名”），来指定调用哪个服务。
// 比如在代码中调用了service-hi服务的“/hi”接口，还可以使用url参数指定一个URL
// fallback  出现错误回调类
@FeignClient(name = "zuulservice-web", fallback = TokenFallback.class)
public interface TokenFeignClient {
    /**
     * 对应service-user微服务中的URL
     *
     * @return
     */
    @PostMapping("/insertJWT")
    Object insertJWT(@RequestParam(value = "jwt", required = true) String jwt,
                     @RequestParam(value = "userId", required = true) String userId);

    @PostMapping("/logout")
    Object logout(@RequestParam(value = "userId", required = true) String userId);
}
