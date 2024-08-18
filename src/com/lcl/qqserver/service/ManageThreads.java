package com.lcl.qqserver.service;

import java.util.HashMap;
import java.util.Iterator;

//该类用于管理线程
public class ManageThreads {
    private static HashMap<String, ServerThread> hashMap = new HashMap<>();
    //添加线程对象到集合
    public static void addThread(String userId, ServerThread serverThread){
        hashMap.put(userId,serverThread);
    }
    //根据userId返回线程
    public static ServerThread getThread(String userId){
        return hashMap.get(userId);
    }
    //将某个线程移除
    public static void removeClientThread(String userId){
        hashMap.remove(userId);
    }
    //获取在线用户列表
    public static String getOnlineUsers(){
        //遍历HashMap即可
        Iterator<String> iterator = hashMap.keySet().iterator();
        String onlineUsers ="";
        while(iterator.hasNext()){
            onlineUsers += (iterator.next().toString() + " ");
        }
        return onlineUsers;
    }

    public static HashMap<String, ServerThread> getHashMap() {
        return hashMap;
    }
}
