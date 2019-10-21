package com.sdxm.report.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dictionary", fallback = DictionaryFallback.class)
public interface DictionaryFeignClient {

    /**
     * 对应service-user微服务中的URL
     *
     * @return
     */
    @GetMapping("/value/queryByPCode")
    Object queryByPCode(@RequestParam("code") String code);
}
