package com.example.kzvda.menumanagementsystem;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

public class User {
    private int type;
    private String username;
    private String phone_number;
    private Drawable photo;

    public User(HashMap<String, Object> user) {
        this.type = (int) user.get("type");
        this.username = (String) user.get("username");
        this.phone_number = (String) user.get("phone_num");
    }

    public User(int type, String username, String phone_number) {
        this.type = type;
        this.username = username;
        this.phone_number = phone_number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
