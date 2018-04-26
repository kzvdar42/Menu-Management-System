package com.example.kzvda.menumanagementsystem.serverApi.Interfaces;

import com.example.kzvda.menumanagementsystem.serverApi.Models.LoginModel;
import com.example.kzvda.menumanagementsystem.serverApi.Models.MenuServerModel;
import com.example.kzvda.menumanagementsystem.serverApi.Models.RegistrationModel;
import com.example.kzvda.menumanagementsystem.serverApi.Models.RestServerModel;
import com.example.kzvda.menumanagementsystem.serverApi.Models.SimpleResponceModel;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.ChangePersonalInfoRequest;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.DishRequest;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.RegRequest;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.RestRequest;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.SimpleRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerApi {
    @GET("api/is_up")
    Call<SimpleResponceModel> getStatus();

    @POST("api")
    Call<RestServerModel> getRestaurants(@Body RestRequest restRequest);

    @POST("api")
    Call<MenuServerModel> getMenu(@Body RestRequest restRequest);

    @POST("api")
    Call<RegistrationModel> register(@Body RegRequest regRequest);

    @POST("api")
    Call<LoginModel> login(@Body RegRequest regRequest);

    @POST("api")
    Call<SimpleResponceModel> addDish(@Body DishRequest DishRequest);

    @POST("api")
    Call<SimpleResponceModel> removeAccount(@Body SimpleRequest simpleRequest);

    @POST("api")
    Call<SimpleResponceModel> changePersonalInfo(@Body ChangePersonalInfoRequest changePersonalInfoRequest);

}
