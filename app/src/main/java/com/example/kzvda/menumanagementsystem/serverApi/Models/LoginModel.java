package com.example.kzvda.menumanagementsystem.serverApi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("configrmed")
    @Expose
    private Integer configrmed;
    @SerializedName("rights")
    @Expose
    private Integer rights;
    @SerializedName("rest")
    @Expose
    private Integer rest;
    @SerializedName("phone_num")
    @Expose
    private String phoneNum;
    @SerializedName("info")
    @Expose
    private String info;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean getConfigrmed() {
        return configrmed == 1;
    }

    public void setConfigrmed(Integer configrmed) {
        this.configrmed = configrmed;
    }

    public Integer getRights() {
        return rights;
    }

    public void setRights(Integer rights) {
        this.rights = rights;
    }

    public Integer getRest() {
        return rest;
    }

    public void setRest(Integer rest) {
        this.rest = rest;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
