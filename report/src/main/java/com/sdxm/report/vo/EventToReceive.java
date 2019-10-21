package com.sdxm.report.vo;

public class EventToReceive {
    /**
     * 消息id
     */
    private String messageId;
    /**
     * 发送系统的标记
     */
    private String sendFlag = "WECHAT";
    /**
     * 消息内容
     */
    private Object message;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
