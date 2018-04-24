package com.example.kzvda.menumanagementsystem.ServerApi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IsServerUpModel {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("status")
    @Expose
    private String status;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}