package com.example.kzvda.menumanagementsystem.viewModel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.kzvda.menumanagementsystem.db.Entity.MenuEntity;
import com.example.kzvda.menumanagementsystem.db.Repository;
import com.example.kzvda.menumanagementsystem.serverApi.Interfaces.ServerApi;
import com.example.kzvda.menumanagementsystem.serverApi.Models.MenuServerModel;
import com.example.kzvda.menumanagementsystem.serverApi.Models.RestMenu;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.RestRequest;
import com.example.kzvda.menumanagementsystem.serverApi.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageTemplatesViewModel extends ViewModel {
    private Repository mRepository;
    private static ServerApi serverApi;
    private LiveData<List<MenuEntity>> templates;

    public ManageTemplatesViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
        serverApi = RetrofitInstance.getApi();
    }

    public LiveData<List<MenuEntity>> getTemplates(int id) {
        if (templates == null) {
            templates = mRepository.getTemplates(id);
        }
        return templates;
    }

    public void downloadTemplates(int id) {
        serverApi.getMenu(new RestRequest("rest_templates", id)).enqueue(new Callback<MenuServerModel>() {
            @Override
            public void onResponse(@NonNull Call<MenuServerModel> call, @NonNull Response<MenuServerModel> response) {
                List<RestMenu> restMenu = response.body().getRestMenu();
                if (restMenu != null) {
                    mRepository.updateRestaurantMenu(restMenu);
                    Toast.makeText(getApplication().getBaseContext(), "Yeah! " + response.message(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplication().getBaseContext(), "Menu List is null, message: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MenuServerModel> call, @NonNull Throwable t) {

            }
        });
    }
}
