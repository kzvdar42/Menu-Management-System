package com.example.kzvda.menumanagementsystem.serverApi.RequestBodies;

public class SimpleRequest {
    private String type;
    private String login;

    public SimpleRequest(String type, String login) {
        this.type = type;
        this.login = login;
    }
}
