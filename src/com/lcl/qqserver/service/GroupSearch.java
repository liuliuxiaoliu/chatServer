package com.lcl.qqserver.service;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class GroupSearch {
    private static ConcurrentHashMap<String, Group> groups = new ConcurrentHashMap<>();
    //静态代码块，初始化一个群的信息
    static{
        ArrayList<String> groupMember = new ArrayList<>();
        groupMember.add("100");
        groupMember.add("200");
        groupMember.add("300");
        //群号123
        groups.put("123", new Group("123",3,groupMember));

        ArrayList<String> groupMember2 = new ArrayList<>();
        groupMember2.add("小刘");
        groupMember2.add("小乘");
        //群号321
        groups.put("321", new Group("321",2,groupMember2));
    }

    public ArrayList<String> searchMembers(String groupId){
        Group group = groups.get(groupId);
        if(group==null){//群不存在
            return null;
        }else{
            return group.getGroupMembers();
        }
    }

}
