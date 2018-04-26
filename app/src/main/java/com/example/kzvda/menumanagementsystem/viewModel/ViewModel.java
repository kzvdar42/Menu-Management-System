package com.example.kzvda.menumanagementsystem.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.kzvda.menumanagementsystem.db.Repository;
import com.example.kzvda.menumanagementsystem.serverApi.Interfaces.ServerApi;
import com.example.kzvda.menumanagementsystem.serverApi.Models.SimpleResponceModel;
import com.example.kzvda.menumanagementsystem.serverApi.RetrofitInstance;

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

    public void isServerUp() {
        serverApi.getStatus().enqueue(new Callback<SimpleResponceModel>() {
            @Override
            public void onResponse(@NonNull Call<SimpleResponceModel> call, @NonNull Response<SimpleResponceModel> response) {
                try {
                    if (response.body().getResult().equals("OK")) {
                        Toast.makeText(getApplication().getBaseContext(), "Server is up, connection established", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplication().getBaseContext(), "Result if false", Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException e) {
                    Toast.makeText(getApplication().getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SimpleResponceModel> call, @NonNull Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "Блэт. Something is wrong..." + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
