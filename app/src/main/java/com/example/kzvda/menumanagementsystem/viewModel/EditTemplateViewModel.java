package com.example.kzvda.menumanagementsystem.viewModel;

import android.app.Application;
import android.widget.Toast;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.serverApi.Interfaces.ServerApi;
import com.example.kzvda.menumanagementsystem.serverApi.Models.SimpleResponceModel;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.DishRequest;
import com.example.kzvda.menumanagementsystem.serverApi.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTemplateViewModel extends ViewModel {

    private static ServerApi serverApi;

    public EditTemplateViewModel(Application application) {
        super(application);
        serverApi = RetrofitInstance.getApi();
    }

    public Call<SimpleResponceModel> editTemplate(int restId, int dishId, String name, String description, int price, boolean isInMenu) {
        return serverApi.addDish(new DishRequest("update_dish", restId, dishId, name, description, price, isInMenu));
    }

    public void deleteTemplate(int dishId) {
        serverApi.addDish(new DishRequest("delete_dish", dishId)).enqueue(new Callback<SimpleResponceModel>() {
            @Override
            public void onResponse(Call<SimpleResponceModel> call, Response<SimpleResponceModel> response) {

            }

            @Override
            public void onFailure(Call<SimpleResponceModel> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), getApplication().getString(R.string.something_is_wrong) + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
