package com.example.kzvda.menumanagementsystem.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.ServerApi.Constants;
import com.example.kzvda.menumanagementsystem.ServerApi.Interfaces.ServerApi;
import com.example.kzvda.menumanagementsystem.ServerApi.Models.FooRequest;
import com.example.kzvda.menumanagementsystem.ServerApi.Models.IsServerUpModel;
import com.example.kzvda.menumanagementsystem.ServerApi.Models.RestList;
import com.example.kzvda.menumanagementsystem.ServerApi.Models.RestServerModel;
import com.example.kzvda.menumanagementsystem.db.Entity.MenuEntity;
import com.example.kzvda.menumanagementsystem.db.Entity.RestaurantEntity;
import com.example.kzvda.menumanagementsystem.db.Repository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewModel extends AndroidViewModel {
    private Repository mRepository;
    private LiveData<List<RestaurantEntity>> mRestaurantsList;
    private static ServerApi serverApi;

    public ViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
        mRestaurantsList = mRepository.getRestaurantsList();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverApi = retrofit.create(ServerApi.class);
    }

    public LiveData<List<RestaurantEntity>> getRestaurantsList() {
        return mRestaurantsList;
    }

    public LiveData<RestaurantEntity> getRestaurant(int id) {
        return mRepository.getRestaurant(id);
    }

    public LiveData<List<MenuEntity>> getRestaurantMenu(int id) {
        return mRepository.getRestaurantMenu(id);
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
                Toast.makeText(getApplication().getBaseContext(), "No response from server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void downloadRestaurants() {
        serverApi.getRestaurants(new FooRequest("rest_list")).enqueue(new Callback<RestServerModel>() {
            @Override
            public void onResponse(@NonNull Call<RestServerModel> call, @NonNull Response<RestServerModel> response) {
                List<RestList> restList = response.body().getRestList();
                if (restList != null) {
                    mRepository.updateRestaurantList(foo(restList));
                    Toast.makeText(getApplication().getBaseContext(),"Yeah! " +response.message(),Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplication().getBaseContext(),"Rest List is null, message: " +response.message(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestServerModel> call, @NonNull Throwable t) {

            }
        });
    }

    public LiveData<List<MenuEntity>> getTemplates(int id) {
        return mRepository.getTemplates(id);
    }

    public void insertToMenu(MenuEntity dish) {
        mRepository.insertToMenu(dish);
    }

    private List<RestaurantEntity> foo(List<RestList> restLists) {
        ArrayList<RestaurantEntity> answer = new ArrayList<RestaurantEntity>(restLists.size());
        for (RestList restList : restLists) {
            RestaurantEntity restaurant = new RestaurantEntity(restList.getId(), restList.getRestName(), restList.getSubName(), restList.getDescription(), restList.getPhoneNum(), restList.getLocation(), restList.getWebSite(), R.drawable.ic_launcher_foreground);
            answer.add(restaurant);
        }
        return answer;
    }
}
