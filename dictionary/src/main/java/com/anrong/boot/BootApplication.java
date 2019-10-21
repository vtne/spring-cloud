package com.anrong.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletResponse;

/**
 * @author liuxun
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@RestController
@ComponentScan(basePackages = {"com.anrong.boot","com.sdxm.dictionary"})
@EnableSwagger2
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

    @ApiIgnore
    @RequestMapping("/doc")
    public void doc(HttpServletResponse response) {
        try {
            response.sendRedirect("swagger-ui.html");

        }
        catch (Exception e) {
            ;
        }
    }
}
