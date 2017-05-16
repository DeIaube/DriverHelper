package com.example.administrator.olddriverpromotionexam.bean;

/**
 * Created by Administrator on 2017/5/13 0013.
 */

public class User {
    private String username;
    private String password;
    private String mail;
    private String phone;
    private String province;
    private String models;

    public User(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    public User() {
        province = "黑龙江";
        models = "小车";
        mail = "";
        phone = "";
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", models='" + models + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }
}
