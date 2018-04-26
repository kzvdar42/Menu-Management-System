package com.example.kzvda.menumanagementsystem.viewModel;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.kzvda.menumanagementsystem.db.Entity.RestaurantEntity;
import com.example.kzvda.menumanagementsystem.db.Repository;

public class RestaurantInfoViewModel extends ViewModel {
    private Repository mRepository;

    public RestaurantInfoViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
    }

    public LiveData<RestaurantEntity> getRestaurant(int id) {
        return mRepository.getRestaurant(id);
    }
}
