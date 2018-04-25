package com.example.kzvda.menumanagementsystem.db.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.kzvda.menumanagementsystem.db.Model.MenuModel;

@Entity()
public class MenuEntity implements MenuModel {
    @PrimaryKey(autoGenerate = true)
    private int dishId;

    @ForeignKey(entity = RestaurantEntity.class, parentColumns = "id", childColumns = "restaurantId")
    private int restaurantId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "photoSrc")
    private int photoSrc;

    @ColumnInfo(name = "price")
    private int price;

    @ColumnInfo(name = "shown")
    private boolean shown;

    public MenuEntity(int restaurantId, String name, String description, int photoSrc, int price, boolean shown) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.photoSrc = photoSrc;
        this.price = price;
        this.shown = shown;
    }

    @Ignore
    public MenuEntity(int dishId, int restaurantId, String name, String description, int photoSrc, int price, boolean shown) {
        this.dishId = dishId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.photoSrc = photoSrc;
        this.price = price;
        this.shown = shown;
    }

    @Override
    public int getDishId() {
        return dishId;
    }

    @Override
    public int getRestaurantId() {
        return restaurantId;
    }

    @Override
    public int getPhotoSrc() {
        return photoSrc;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public boolean isShown() {
        return shown;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhotoSrc(int photoSrc) {
        this.photoSrc = photoSrc;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setShown(boolean show) {
        this.shown = show;
    }
}