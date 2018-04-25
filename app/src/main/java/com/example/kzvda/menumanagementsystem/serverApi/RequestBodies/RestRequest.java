package com.example.kzvda.menumanagementsystem.serverApi.RequestBodies;

public class RestRequest {

    private String type;
    private int id;

    public RestRequest(String type) {
        this.type = type;
    }

    public RestRequest(String type, int id) {
        this.type = type;
        this.id = id;
    }
}