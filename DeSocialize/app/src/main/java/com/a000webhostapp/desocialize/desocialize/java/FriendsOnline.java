package com.a000webhostapp.desocialize.desocialize.java;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by djordjekalezic on 07/01/2018.
 */

public class FriendsOnline implements Serializable {

     public int idpy;
     public String username;
     public String imgp;
     public int points;

    public FriendsOnline(int idpy, String username,String imgp, int points) {
        this.idpy = idpy;
        this.imgp = imgp;
        this.points = points;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdpy() {
        return idpy;
    }

    public void setIdpy(int idpy) {
        this.idpy = idpy;
    }

    public String getImgp() {
        return imgp;
    }

    public void setImgp(String imgp) {
        this.imgp = imgp;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


}
