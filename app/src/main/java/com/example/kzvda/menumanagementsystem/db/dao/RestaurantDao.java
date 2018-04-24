package com.example.kzvda.menumanagementsystem.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.kzvda.menumanagementsystem.db.Entity.RestaurantEntity;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public interface RestaurantDao {

    @Query("SELECT * FROM RestaurantEntity")
    LiveData<List<RestaurantEntity>> getAll();

    @Insert
    void insert(RestaurantEntity... restaurants);

    @Insert
    void insertRests(List<RestaurantEntity> restList);

    @Delete
    void delete(RestaurantEntity restaurant);

    @Query("DELETE FROM RestaurantEntity")
    void deleteAll();

    @Query("SELECT * FROM RestaurantEntity WHERE id = :id")
    LiveData<RestaurantEntity> get(int id);
}

