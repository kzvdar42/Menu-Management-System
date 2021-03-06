package com.example.kzvda.menumanagementsystem.viewModel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.db.Entity.RestaurantEntity;
import com.example.kzvda.menumanagementsystem.db.Repository;
import com.example.kzvda.menumanagementsystem.serverApi.Interfaces.ServerApi;
import com.example.kzvda.menumanagementsystem.serverApi.Models.RestList;
import com.example.kzvda.menumanagementsystem.serverApi.Models.RestServerModel;
import com.example.kzvda.menumanagementsystem.serverApi.Models.SimpleResponceModel;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.RestRequest;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.SimpleRequest;
import com.example.kzvda.menumanagementsystem.serverApi.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private Repository mRepository;
    private LiveData<List<RestaurantEntity>> mRestaurantsList;
    private static ServerApi serverApi;

    public MainViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
        mRestaurantsList = mRepository.getRestaurantsList();
        serverApi = RetrofitInstance.getApi();
    }

    public LiveData<List<RestaurantEntity>> getRestaurantsList() {
        return mRestaurantsList;
    }

    public void deleteAccount(String username) {
        serverApi.removeAccount(new SimpleRequest("delete_user", username)).enqueue(new Callback<SimpleResponceModel>() {
            @Override
            public void onResponse(Call<SimpleResponceModel> call, Response<SimpleResponceModel> response) {

            }

            @Override
            public void onFailure(Call<SimpleResponceModel> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), getApplication().getString(R.string.something_is_wrong) + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void downloadRestaurants() {
        serverApi.getRestaurants(new RestRequest("rest_list")).enqueue(new Callback<RestServerModel>() {
            @Override
            public void onResponse(@NonNull Call<RestServerModel> call, @NonNull Response<RestServerModel> response) {
                List<RestList> restList = response.body().getRestList();
                if (restList != null) {
                    mRepository.updateRestaurantList(servRestToLocal(restList));
                } else {
                    Toast.makeText(getApplication().getBaseContext(), "Rest List is null, message: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestServerModel> call, @NonNull Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), getApplication().getString(R.string.something_is_wrong) + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<RestaurantEntity> servRestToLocal(List<RestList> restLists) {
        ArrayList<RestaurantEntity> answer = new ArrayList<RestaurantEntity>(restLists.size());
        for (RestList restList : restLists) {
            RestaurantEntity restaurant = new RestaurantEntity(restList.getId(), restList.getRestName(), restList.getSubName(), restList.getDescription(), restList.getPhoneNum(), restList.getLocation(), restList.getWebSite(), restList.getMainPhotoSrc());
            answer.add(restaurant);
        }
        return answer;
    }
}
