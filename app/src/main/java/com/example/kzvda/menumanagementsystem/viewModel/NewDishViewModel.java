package com.example.kzvda.menumanagementsystem.viewModel;

import android.app.Application;

import com.example.kzvda.menumanagementsystem.serverApi.Interfaces.ServerApi;
import com.example.kzvda.menumanagementsystem.serverApi.RetrofitInstance;
import com.example.kzvda.menumanagementsystem.db.Entity.MenuEntity;
import com.example.kzvda.menumanagementsystem.db.Repository;

public class NewDishViewModel extends ViewModel {
    private Repository mRepository;
    private static ServerApi serverApi;

    public NewDishViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
        serverApi = RetrofitInstance.getApi();
    }

    public void insertToMenu(MenuEntity dish) {
        mRepository.insertToMenu(dish);
    }
}
