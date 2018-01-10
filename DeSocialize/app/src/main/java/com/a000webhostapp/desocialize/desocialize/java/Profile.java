package com.a000webhostapp.desocialize.desocialize.java;

/**
 * Created by Dario on 10-Jan-18.
 */

public class Profile {

    public String username;
    public String imgp;
    public int points;
    public int count;

    public Profile(String username, String imgp, int points, int count) {
        this.username = username;
        this.imgp = imgp;
        this.points = points;
        this.count = count;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        this.imgp = imgp;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }



}
