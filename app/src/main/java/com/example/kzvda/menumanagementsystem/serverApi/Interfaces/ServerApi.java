package com.example.kzvda.menumanagementsystem.serverApi.Interfaces;

import com.example.kzvda.menumanagementsystem.serverApi.Models.RegistrationModel;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.RegRequest;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.RestRequest;
import com.example.kzvda.menumanagementsystem.serverApi.Models.IsServerUpModel;
import com.example.kzvda.menumanagementsystem.serverApi.Models.MenuServerModel;
import com.example.kzvda.menumanagementsystem.serverApi.Models.RestServerModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerApi {
    @GET("api/is_up")
    Call<IsServerUpModel> getStatus();

    @POST("api")
    Call<RestServerModel> getRestaurants(@Body RestRequest restRequest);

    @POST("api")
    Call<MenuServerModel> getMenu(@Body RestRequest restRequest);

    @POST("api")
    Call<RegistrationModel> register(@Body RegRequest regRequest);


}
