package com.sdxm.report.service;

import heartbeat.monitor.starter.processors.business.MonitorCommonSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleTask {
    private  static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    @Autowired
    private MonitorCommonSender monitorCommonSender;


    @Scheduled(fixedRate = 10000)
    public void addListenerData(){
        //logger.info("======start {} ......", new Date());
        sender();
        //logger.info("======end {} ......", new Date());
    }

    private void sender(){
        monitorCommonSender.sendUserCount(10L,20L);
    }
}
