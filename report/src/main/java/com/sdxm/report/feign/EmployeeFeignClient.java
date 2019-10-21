package com.sdxm.report.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 标明feign调用的微服务名称
 */
//通过@ FeignClient（“服务名”），来指定调用哪个服务。
// 比如在代码中调用了service-hi服务的“/hi”接口，还可以使用url参数指定一个URL
// fallback  出现错误回调类
@FeignClient(name = "urpm-wx",fallback = EmployeeFallback.class)
//@FeignClient(value = "urpm-wx")
public interface EmployeeFeignClient {
    /**
     * 对应service-user微服务中的URL
     * @return
     */
    @GetMapping("/employee/queryEmployeeById")
    //@RequestMapping(value = "/employee/queryEmployeeById", method = RequestMethod.GET)
    Object queryEmployeeById(@RequestParam("id") Integer id);
}
