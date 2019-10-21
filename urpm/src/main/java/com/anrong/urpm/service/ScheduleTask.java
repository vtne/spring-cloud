package com.anrong.urpm.service;

import com.anrong.urpm.util.GetToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleTask {
    private  static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    @Value("${weixin.appId}")
    String _appId = "";

    @Value("${weixin.secret}")
    String _secret = "";

    @Scheduled(fixedRate = 7020000)
    public void addListenerData(){
        try {
            GetToken.get(_appId,_secret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
