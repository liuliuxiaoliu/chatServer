package com.lcl.qqcommon;
//qq网络通信时的用户对象
//import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
    //@Serial
    private static final long serialVersionUID = 1L;
    private String userID;//用户ID
    private String password;//用户密码

    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
