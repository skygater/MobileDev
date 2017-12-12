package com.a000webhostapp.desocialize.desocialize.java;

/**
 * Created by djordjekalezic on 11/12/2017.
 */

public class User {

    private  int idu;
    private String username;
    private String email;
    private String password;
    private String qr;
    private String imgp;

    public User(int idu, String username, String email, String password, String qr, String imgp) {
        this.idu = idu;
        this.username = username;
        this.email = email;
        this.password = password;
        this.qr = qr;
        this.imgp = imgp;
    }

    public User(int idu, String username, String email, String password) {
        this.idu = idu;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getImgp() {
        return imgp;
    }

    public void setImgp(String imgp) {
        this.imgp = imgp;
    }
}
