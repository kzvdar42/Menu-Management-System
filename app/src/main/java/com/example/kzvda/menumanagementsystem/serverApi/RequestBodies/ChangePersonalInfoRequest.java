package com.example.kzvda.menumanagementsystem.serverApi.RequestBodies;

public class ChangePersonalInfoRequest {
    private String type;
    private String login;
    private String new_login;
    private String password;
    private String phone_number;

    public ChangePersonalInfoRequest(String type, String login, String new_login, String password, String phone_number) {
        this.type = type;
        this.login = login;
        this.new_login = new_login;
        this.password = password;
        this.phone_number = phone_number;
    }

    public ChangePersonalInfoRequest(String type, String login, String new_login, String phone_number) {
        this.type = type;
        this.login = login;
        this.new_login = new_login;
        this.phone_number = phone_number;
    }

}
