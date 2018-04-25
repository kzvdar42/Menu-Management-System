package com.example.kzvda.menumanagementsystem.serverApi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("id")
    @Expose
    private int id;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}