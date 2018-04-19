package com.example.kzvda.menumanagementsystem;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.kzvda.menumanagementsystem.db.Entity.MenuEntity;
import com.example.kzvda.menumanagementsystem.db.Entity.RestaurantEntity;
import com.example.kzvda.menumanagementsystem.db.Repository;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private Repository mRepository;

    public ViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
    }

    public LiveData<List<RestaurantEntity>> getRestaurantsList() {
        return mRepository.getRestaurantsList();
    }

    public LiveData<List<MenuEntity>> getRestaurantMenu(int id) {
        return mRepository.getRestaurantMenu(id);
    }

    public void insert(MenuEntity dish) {
        mRepository.insert(dish);
    }

}
