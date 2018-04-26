package com.example.kzvda.menumanagementsystem.db.Model;

public interface MenuModel {
    int getDishId();
    int getRestaurantId();
    String getName();
    String getDescription();

    String getPhotoSrc();
    int getPrice();
    boolean isShown();
}
