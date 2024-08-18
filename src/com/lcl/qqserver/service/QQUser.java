package com.lcl.qqserver.service;

import com.lcl.qqcommon.User;

import java.util.concurrent.ConcurrentHashMap;

public class QQUser {
    //private static HashMap<String, User> users = new HashMap<>();
    //使用ConcurrentHashMap避免并发问题
    private static ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    //静态代码块，初始化一些用户信息
    static{
        users.put("100",new User("100","12345"));
        users.put("200",new User("200","12345"));
        users.put("300",new User("300","12345"));
        users.put("小刘",new User("小刘","12345"));
        users.put("小乘",new User("小乘","12345"));
    }
    public boolean checkUser(String userId,String pwd){
        User user = users.get(userId);
        if(user==null){//用户不存在
            return false;
        }
        if(!user.getPassword().equals(pwd)){//密码错误
            return false;
        }
        return true;
    }
}
