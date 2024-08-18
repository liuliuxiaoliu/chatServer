package com.lcl.qqserver.service;

import com.lcl.qqcommon.Message;
import com.lcl.qqcommon.MessageType;
import com.lcl.qqcommon.User;
import com.lcl.ui.ServerFrame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class QQServer {
    private ServerSocket serverSocket = null;
    public static ServerFrame serverFrame;
    public static String tip ="";
    public static void main(String[] args) {
        serverFrame = new ServerFrame();
        new QQServer();
    }
    public QQServer() {
        try {
            serverSocket = new ServerSocket(8888);
            System.out.println("服务器已启动。");
            tip = "服务器已启动。";
            serverFrame.addToPanel(tip);
            while(true) {//需要一直监听
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //首先拿到用户对象
                User user = (User) ois.readObject();
                //验证用户,创建Message回复客户端
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Message message = new Message();
                QQUser qqUser=new QQUser();
                if(qqUser.checkUser(user.getUserID(),user.getPassword())){
                    //验证成功
                    System.out.println(user.getUserID()+"登录成功。");
                    tip = user.getUserID()+"登录成功。";
                    serverFrame.addToPanel(tip);
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(message);
                    //创建一个线程和客户端保持通信
                    ServerThread serverThread = new ServerThread(user.getUserID(),socket);
                    //启动线程,并放入集合中管理
                    serverThread.start();
                    ManageThreads.addThread(user.getUserID(),serverThread);
                    
                }else{//验证失败
                    System.out.println(user.getUserID()+"验证失败。");
                    tip = user.getUserID()+"验证失败。";
                    serverFrame.addToPanel(tip);
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    //需要关闭Socket
                    socket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
