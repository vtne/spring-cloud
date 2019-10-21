package com.sdxm.report.feign;

import org.springframework.stereotype.Component;

@Component
public class EmployeeFallback implements EmployeeFeignClient {
    @Override
    public String queryEmployeeById(Integer id) {
        return "服务调用失败";
    }
}