package com.example.kzvda.menumanagementsystem.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.serverApi.Interfaces.ServerApi;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.RestRequest;
import com.example.kzvda.menumanagementsystem.serverApi.Models.IsServerUpModel;
import com.example.kzvda.menumanagementsystem.serverApi.Models.MenuServerModel;
import com.example.kzvda.menumanagementsystem.serverApi.Models.RestMenu;
import com.example.kzvda.menumanagementsystem.serverApi.RetrofitInstance;
import com.example.kzvda.menumanagementsystem.db.Entity.MenuEntity;
import com.example.kzvda.menumanagementsystem.db.Entity.RestaurantEntity;
import com.example.kzvda.menumanagementsystem.db.Repository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModel extends AndroidViewModel {
    private Repository mRepository;
    private static ServerApi serverApi;

    public ViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
        serverApi = RetrofitInstance.getApi();
    }

    public LiveData<RestaurantEntity> getRestaurant(int id) {
        return mRepository.getRestaurant(id);
    }

    public LiveData<List<MenuEntity>> getTemplates(int id) {
        return mRepository.getTemplates(id);
    }

    public void isServerUp() {
        serverApi.getStatus().enqueue(new Callback<IsServerUpModel>() {
            @Override
            public void onResponse(@NonNull Call<IsServerUpModel> call, @NonNull Response<IsServerUpModel> response) {
                try {
                    if (response.body().getResult()) {
                        Toast.makeText(getApplication().getBaseContext(), "Server is up, connection established", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplication().getBaseContext(), "Result if false", Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException e) {
                    Toast.makeText(getApplication().getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<IsServerUpModel> call, @NonNull Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "Блэт. Something is wrong..." + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void downloadTemplates(int id) {
        serverApi.getMenu(new RestRequest("rest_templates", id)).enqueue(new Callback<MenuServerModel>() {
            @Override
            public void onResponse(@NonNull Call<MenuServerModel> call, @NonNull Response<MenuServerModel> response) {
                List<RestMenu> restMenu = response.body().getRestMenu();
                if (restMenu != null) {
                    mRepository.updateRestaurantMenu(servMenuToLocal(restMenu));
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

    private List<MenuEntity> servMenuToLocal(List<RestMenu> menuList) {
        ArrayList<MenuEntity> answer = new ArrayList<MenuEntity>(menuList.size());
        for (RestMenu restMenu : menuList) {
            MenuEntity restaurant = new MenuEntity(restMenu.getId(), restMenu.getRestId(), restMenu.getDishName(), restMenu.getDescription(), R.drawable.food_example, restMenu.getPrice(), restMenu.getOnoff());
            answer.add(restaurant);
        }
        return answer;
    }
}
