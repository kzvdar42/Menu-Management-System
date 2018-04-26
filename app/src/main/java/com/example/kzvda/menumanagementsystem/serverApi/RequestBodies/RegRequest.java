package com.example.kzvda.menumanagementsystem.serverApi.RequestBodies;

public class RegRequest {
    private String type;
    private String login;
    private String password;
    private String phoneNumber;
    private int admin;

    public RegRequest(String type, String login, String password) {
        this.type = type;
        this.login = login;
        this.password = password;
    }

    public RegRequest(String type, String login, String password, String phoneNumber) {
        this.type = type;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.admin = 1;
    }
}
