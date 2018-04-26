package com.example.kzvda.menumanagementsystem.viewModel;

import android.app.Application;
import android.widget.Toast;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.db.Entity.MenuEntity;
import com.example.kzvda.menumanagementsystem.db.Repository;
import com.example.kzvda.menumanagementsystem.serverApi.Interfaces.ServerApi;
import com.example.kzvda.menumanagementsystem.serverApi.Models.SimpleResponceModel;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.DishRequest;
import com.example.kzvda.menumanagementsystem.serverApi.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void uploadToMenu(DishRequest dish) {
        serverApi.addDish(dish).enqueue(new Callback<SimpleResponceModel>() {
            @Override
            public void onResponse(Call<SimpleResponceModel> call, Response<SimpleResponceModel> response) {
                if (response.body().getResult().equals("OK")) {

                } else {
                    Toast.makeText(getApplication().getBaseContext(), getApplication().getString(R.string.something_is_wrong) + response.body().getResult() + response.body().getInfo(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SimpleResponceModel> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), getApplication().getString(R.string.something_is_wrong) + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
