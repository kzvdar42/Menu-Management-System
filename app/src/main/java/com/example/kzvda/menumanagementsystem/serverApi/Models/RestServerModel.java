package com.example.kzvda.menumanagementsystem.serverApi.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestServerModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("rest_list")
    @Expose
    private List<RestList> restList;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<RestList> getRestList() {
        return restList;
    }

    public void setRestList(List<RestList> restList) {
        this.restList = restList;
    }

}