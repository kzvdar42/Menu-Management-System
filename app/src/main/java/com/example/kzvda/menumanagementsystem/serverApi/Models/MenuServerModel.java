package com.example.kzvda.menumanagementsystem.serverApi.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuServerModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("rest_menu")
    @Expose
    private List<RestMenu> restMenu = null;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<RestMenu> getRestMenu() {
        return restMenu;
    }

    public void setRestMenu(List<RestMenu> restMenu) {
        this.restMenu = restMenu;
    }

}