package com.example.kzvda.menumanagementsystem.viewModel;

import android.app.Application;

import com.example.kzvda.menumanagementsystem.serverApi.Interfaces.ServerApi;
import com.example.kzvda.menumanagementsystem.serverApi.Models.SimpleResponceModel;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.ChangePersonalInfoRequest;
import com.example.kzvda.menumanagementsystem.serverApi.RetrofitInstance;

import retrofit2.Call;

public class ChangePersonalInfoViewModel extends ViewModel {

    private ServerApi serverApi;

    public ChangePersonalInfoViewModel(Application application) {
        super(application);
        serverApi = RetrofitInstance.getApi();
    }

    public Call<SimpleResponceModel> changePersonalInfo(String oldLogin, String newLogin, String phoneNumber) {
        return serverApi.changePersonalInfo(new ChangePersonalInfoRequest("change_personal_info", oldLogin, newLogin, phoneNumber));
    }

    public Call<SimpleResponceModel> changePersonalInfo(String oldLogin, String newLogin, String password, String phoneNumber) {
        return serverApi.changePersonalInfo(new ChangePersonalInfoRequest("change_personal_info", oldLogin, newLogin, password, phoneNumber));
    }
}
