package com.lcl.qqserver.service;

import java.util.ArrayList;

public class Group {
    private String groupId;
    private int number;
    private ArrayList<String> groupMembers;

    public Group(String groupId, int number, ArrayList<String> groupMembers) {
        this.groupId = groupId;
        this.number = number;
        this.groupMembers = groupMembers;
    }
    public void addMember(String userId){
        this.groupMembers.add(userId);
        this.number++;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<String> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(ArrayList<String> groupMembers) {
        this.groupMembers = groupMembers;
    }
}
