package com.example.kzvda.menumanagementsystem.serverApi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rest_name")
    @Expose
    private String restName;
    @SerializedName("sub_name")
    @Expose
    private String subName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("phone_num")
    @Expose
    private String phoneNum;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("web_site")
    @Expose
    private String webSite;
    @SerializedName("main_photo_src")
    @Expose
    private String mainPhotoSrc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getMainPhotoSrc() {
        return mainPhotoSrc;
    }

    public void setMainPhotoSrc(String mainPhotoSrc) {
        this.mainPhotoSrc = mainPhotoSrc;
    }

}