package com.lcl.qqcommon;

public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1";//表示登录成功
    String MESSAGE_LOGIN_FAIL = "2";//表示登录失败
    String MESSAGE_SEND_FAIL = "3";//表示消息发送失败
    String MESSAGE_GET_ONLINE_FRIEND = "4";//表示要求得到在线用户列表
    String MESSAGE_RET_ONLINE_FRIEND = "5";//表示返回在线用户列表
    String MESSAGE_INTERACTIVE_SINGLE = "6";//表示私聊消息
    String MESSAGE_INTERACTIVE_GROUP = "7";//表示群聊消息
    String MESSAGE_FILE_SINGLE = "8";//表示私发文件消息
    String MESSAGE_FILE_GROUP = "9";//表示群发文件消息
    String MESSAGE_CLIENT_EXIT = "0";//表示客户端请求退出
}
