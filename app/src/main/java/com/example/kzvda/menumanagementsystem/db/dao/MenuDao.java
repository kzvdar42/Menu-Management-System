package com.example.kzvda.menumanagementsystem.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.kzvda.menumanagementsystem.db.Entity.MenuEntity;

import java.util.List;

@Dao
public interface MenuDao {
    @Query("SELECT * FROM MenuEntity")
    LiveData<List<MenuEntity>> getAll();

    @Query("SELECT * FROM MenuEntity WHERE restaurantId LIKE :rest_id AND shown = 1")
    LiveData<List<MenuEntity>> getRestaurantMenu(int rest_id);

    @Query("SELECT * FROM MenuEntity WHERE restaurantId LIKE :rest_id")
    LiveData<List<MenuEntity>> getRestaurantTemplates(int rest_id);

    @Insert
    void insert(MenuEntity... dishes);

    @Insert
    void insertList(List<MenuEntity> dishes);

    @Delete
    void delete(MenuEntity dish);

    @Query("DELETE FROM MenuEntity WHERE restaurantId = :id")
    void deleteAllFromRest(int id);

    @Query("DELETE FROM MenuEntity")
    void deleteAll();
}