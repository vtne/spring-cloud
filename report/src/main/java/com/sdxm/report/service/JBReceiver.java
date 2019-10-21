package com.sdxm.report.service;

import com.anrong.boot.util.JacksonUtil;
import com.sdxm.report.entity.ReportStatus;
import com.sdxm.report.vo.ReceiveToWechat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import projects.rabbitmq.starter.processors.AbstractCustomReceiver;

import java.util.Date;
import java.util.List;

@Component
public class JBReceiver extends AbstractCustomReceiver {
    private static final Logger logger = LoggerFactory.getLogger(JBReceiver.class);

    @Autowired
    ReportStatusService reportStatusService;

    @Override
    public void resolvePrePositionCustomMessage(String jsonMessage, String objectType, String prePositionId) {
        logger.info("===处理部委前置发来消息 json={} objectType={} prePositionId={}", jsonMessage, objectType, prePositionId);
    }

    @Override
    public void resolveOtherSystemCustomMessage(String jsonMessage, String objectType, String source) {
        logger.info("===接收别的系统自定义消息 json={} objectType={} source={}", jsonMessage, objectType, source);
        if(objectType.equals("ReceiveToWechat")){
            ReceiveToWechat res = JacksonUtil.readValue(jsonMessage,ReceiveToWechat.class);
            if (res.getState() == null || res.getState().equals("")) {
                logger.info("接报系统返回的处理状态为空!");
                return;
            }
            ReportStatus status = new ReportStatus();
            String[] ss = res.getEventId().split("_");
            if(ss.length==2){
                int reportId = Integer.parseInt(ss[1]);
                List<ReportStatus>  list = reportStatusService.queryByReportId(reportId);
                if(list.size()>0){
                    String code = "";
                    ReportStatus lastStatus = list.get(0);
                    //1.事件合并2.新增事件3.续报4.忽略5.建言建策
                    if ("1".equals(res.getState())) {
                        code = "sta_1";
                    } else if ("2".equals(res.getState())) {
                        code = "sta_2";
                    } else if ("3".equals(res.getState())) {
                        code = "sta_3";
                    } else if ("4".equals(res.getState())) {
                        code = "sta_4";
                    } else if ("5".equals(res.getState())) {
                        code = "sta_5";
                    }
                    if(!lastStatus.getCode().equals(code)){
                        status.setReportId(reportId);
                        status.setCode(code);
                        status.setCreateTime(new Date());
                        reportStatusService.add(status);
                        logger.info("===更新report"+reportId+"状态为已受理");
                    }
                }

            }
        }
    }
}
