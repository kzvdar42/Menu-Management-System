package com.example.kzvda.menumanagementsystem.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.kzvda.menumanagementsystem.serverApi.Interfaces.ServerApi;
import com.example.kzvda.menumanagementsystem.serverApi.Models.LoginModel;
import com.example.kzvda.menumanagementsystem.serverApi.Models.RegistrationModel;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.RegRequest;
import com.example.kzvda.menumanagementsystem.serverApi.RetrofitInstance;

import retrofit2.Call;

public class RegistrationViewModel<View> extends AndroidViewModel {

    private ServerApi serverApi;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        serverApi = RetrofitInstance.getApi();
    }

    public Call<RegistrationModel> register(RegRequest regRequest) {
        return serverApi.register(regRequest);
    }

    public Call<LoginModel> login(RegRequest regRequest) {
        return serverApi.login(regRequest);
    }
}
