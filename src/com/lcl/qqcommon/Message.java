package com.lcl.qqcommon;
//qq网络通信时传输的消息对象

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;//增加兼容性
    private String sender;//发送者
    private String receiver;//接收者
    private String content;//消息内容
    private String sendTime;//发送时间
    private String MesType;//消息类型
    private String GroupId;//群聊消息有群号
    private String fileName;//文件名
    private byte[] fileBytes;//传输文件的字节数组
    private int fileLen;//文件长度

    public String getGroupId() {return GroupId;}

    public void setGroupId(String groupId) {GroupId = groupId;}

    public String getFileName() {return fileName;}

    public void setFileName(String fileName) {this.fileName = fileName;}

    public byte[] getFileBytes() {return fileBytes;}

    public void setFileBytes(byte[] fileBytes) {this.fileBytes = fileBytes;}

    public int getFileLen() {return fileLen;}

    public void setFileLen(int fileLen) {this.fileLen = fileLen;}

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMesType() {
        return MesType;
    }

    public void setMesType(String mesType) {
        MesType = mesType;
    }
}
