package com.a000webhostapp.desocialize.desocialize.java;

/**
 * Created by djordjekalezic on 10/01/2018.
 */

public class CheckList {

    int user;
    int checkNo;

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(int checkNo) {
        this.checkNo = checkNo;
    }

    public CheckList(int user, int checkNo) {
        this.user = user;
        this.checkNo = checkNo;
    }
}
