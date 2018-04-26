package com.example.kzvda.menumanagementsystem.serverApi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestMenu {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rest_id")
    @Expose
    private Integer restId;
    @SerializedName("dish_name")
    @Expose
    private String dishName;
    @SerializedName("description")
    @Expose
    private String description = "";
    @SerializedName("photo_src")
    @Expose
    private String photoSrc;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("onoff")
    @Expose
    private Integer onoff = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRestId() {
        return restId;
    }

    public void setRestId(Integer restId) {
        this.restId = restId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoSrc() {
        return photoSrc;
    }

    public void setPhotoSrc(String photoSrc) {
        this.photoSrc = photoSrc;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public boolean getOnoff() {
        return onoff == 1;
    }

    public void setOnoff(Integer onoff) {
        this.onoff = onoff;
    }

}