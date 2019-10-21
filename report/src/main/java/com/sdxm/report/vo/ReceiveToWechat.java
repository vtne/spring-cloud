package com.sdxm.report.vo;

/**
 * @Auther:
 * @Despriction:
 * @Date: 2018/9/12 21:16
 */
public class ReceiveToWechat {
    private String eventId;

    /**
     * 1.事件合并2.新增事件3.续报4.忽略5.建言建策
     */
    private String state;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
