package com.anrong.boot.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Auther: 高亚鹏
 * @Date: 2019/1/29 11:25
 * @Description:
 */
@Configuration
@ConfigurationProperties(prefix="list.url")
public class AddressUrlConfig {
       private  List<String> address;

       public  List<String> getAddress() {
              return address;
       }
       public void setAddress(List<String> address) {
              this.address = address;
       }
}
