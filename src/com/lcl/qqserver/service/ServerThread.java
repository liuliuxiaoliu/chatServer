package com.lcl.qqserver.service;

import com.lcl.qqcommon.Message;
import com.lcl.qqcommon.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

//和某个客户端保持通信
public class ServerThread extends Thread{
    private String userId;
    private Socket socket;

    public ServerThread(String userId, Socket socket) {
        this.userId = userId;
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        while(true){//一直读取客户端信息
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();//服务器没有发送消息的时候就阻塞在这里
                //根据Message类型应答客户端
                if(message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)){
                    //请求在线用户列表，利用线程确定在线用户列表
                    System.out.println(userId + "请求在线用户列表。");
                    QQServer.tip=userId + "请求在线用户列表。";
                    QQServer.serverFrame.addToPanel(QQServer.tip);
                    String onlineUsers = ManageThreads.getOnlineUsers();
                    //新建Message对象回复
                    Message reMessage = new Message();
                    reMessage.setReceiver(message.getSender());
                    reMessage.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    reMessage.setContent(onlineUsers);
                    //发送给客户端
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(reMessage);

                }else if(message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)){
                    //请求退出
                    System.out.println(message.getSender() + "请求退出。");
                    QQServer.tip=message.getSender() + "请求退出。";
                    QQServer.serverFrame.addToPanel(QQServer.tip);
                    //新建Message对象回复确认退出消息
                    Message reMessage = new Message();
                    reMessage.setReceiver(message.getSender());
                    reMessage.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
                    //发送给客户端
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(reMessage);
                    //删除对应线程,关闭socket,退出线程
                    ManageThreads.removeClientThread(message.getSender());
                    socket.close();
                    break;//退出循环，线程结束
                }else if(message.getMesType().equals(MessageType.MESSAGE_INTERACTIVE_SINGLE)||message.getMesType().equals(MessageType.MESSAGE_FILE_SINGLE)) {
                    //请求私聊,找到目标用户的线程，获取对应socket，做一个消息转发即可
                    if(message.getMesType().equals(MessageType.MESSAGE_INTERACTIVE_SINGLE)) {
                        System.out.print(message.getSender() + "请求与" + message.getReceiver() + "通信");
                        QQServer.tip=message.getSender() + "请求与" + message.getReceiver() + "通信";
                    }else{//文件消息
                        System.out.print(message.getSender() + "请求发送给" + message.getReceiver() + "文件"+message.getFileName());
                        QQServer.tip=message.getSender() + "请求发送给" + message.getReceiver() + "文件"+message.getFileName();
                    }
                    String destUserId = message.getReceiver();
                    ServerThread serverThread = ManageThreads.getThread(destUserId);
                    if(serverThread!=null) {//判断目标用户是否上线（或是否存在），否则返回一个发送失败的消息回去
                        System.out.println("成功。");
                        QQServer.tip+="成功。";
                        QQServer.serverFrame.addToPanel(QQServer.tip);
                        ObjectOutputStream oos = new ObjectOutputStream(serverThread.getSocket().getOutputStream());
                        oos.writeObject(message);
                    }else{
                        System.out.println("失败。");
                        QQServer.tip+="失败。";
                        QQServer.serverFrame.addToPanel(QQServer.tip);
                        message.setMesType(MessageType.MESSAGE_SEND_FAIL);
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(message);
                    }
                }else if(message.getMesType().equals(MessageType.MESSAGE_INTERACTIVE_GROUP)||message.getMesType().equals(MessageType.MESSAGE_FILE_GROUP)) {
                    //请求群聊,找到群里用户的线程，获取对应socket，做消息转发
                    if(message.getMesType().equals(MessageType.MESSAGE_INTERACTIVE_GROUP)){
                        System.out.println(message.getSender()+"在群聊"+message.getGroupId()+"发送了一条群聊消息");
                        QQServer.tip=message.getSender()+"在群聊"+message.getGroupId()+"发送了一条群聊消息";
                        QQServer.serverFrame.addToPanel(QQServer.tip);
                    }else{
                        System.out.println(message.getSender()+"群发文件"+message.getFileName()+"到群聊"+message.getGroupId());
                        QQServer.tip=message.getSender()+"群发文件"+message.getFileName()+"到群聊"+message.getGroupId();
                        QQServer.serverFrame.addToPanel(QQServer.tip);
                    }
                    ArrayList<String> destUsersId = new ArrayList<>();
                    GroupSearch groupSearch = new GroupSearch();
                    destUsersId = groupSearch.searchMembers(message.getGroupId());
                    if(destUsersId!=null){
                        for(String destUserId:destUsersId){
                            if(!destUserId.equals(message.getSender())){//排除发送者
                                ServerThread serverThread = ManageThreads.getThread(destUserId);
                                if(serverThread!=null) {//保证给在线用户发
                                    ObjectOutputStream oos = new ObjectOutputStream(serverThread.getSocket().getOutputStream());
                                    oos.writeObject(message);
                                }
                            }
                        }
                    }else{
                        System.out.println("发送失败。");
                        QQServer.tip="发送失败。";
                        QQServer.serverFrame.addToPanel(QQServer.tip);
                        message.setMesType(MessageType.MESSAGE_SEND_FAIL);
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
