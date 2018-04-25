package com.example.kzvda.menumanagementsystem.viewModel;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.kzvda.menumanagementsystem.db.Entity.RestaurantEntity;
import com.example.kzvda.menumanagementsystem.db.Repository;
import com.example.kzvda.menumanagementsystem.serverApi.Interfaces.ServerApi;
import com.example.kzvda.menumanagementsystem.serverApi.RetrofitInstance;

import java.util.List;

public class ManageUsersAccountsViewModel extends ViewModel {
    private static ServerApi serverApi;

    public ManageUsersAccountsViewModel(Application application) {
        super(application);
        serverApi = RetrofitInstance.getApi();
    }

}
