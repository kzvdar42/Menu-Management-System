package com.example.kzvda.menumanagementsystem.db.Model;

public interface MenuModel {
    int getDishId();
    int getRestaurantId();
    String getName();
    String getDescription();
    int getPhotoSrc();
    int getPrice();
}
