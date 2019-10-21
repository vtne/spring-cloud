package com.sdxm.report.feign;

import org.springframework.stereotype.Component;

@Component
public class DictionaryFallback implements DictionaryFeignClient {
    @Override
    public Object queryByPCode(String code) {
        return "服务调用失败";
    }
}
