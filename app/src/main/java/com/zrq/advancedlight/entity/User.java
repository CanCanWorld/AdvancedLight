package com.zrq.advancedlight.entity;

public class User {
    private int id;
    private String username;
    private String sex;
    private String password;

    public User() {
    }

    public User(String username,  String password,String sex) {
        this.username = username;
        this.password = password;
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
