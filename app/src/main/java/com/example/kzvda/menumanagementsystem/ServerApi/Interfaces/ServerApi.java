package com.example.kzvda.menumanagementsystem.ServerApi.Interfaces;

import com.example.kzvda.menumanagementsystem.ServerApi.Models.FooRequest;
import com.example.kzvda.menumanagementsystem.ServerApi.Models.IsServerUpModel;
import com.example.kzvda.menumanagementsystem.ServerApi.Models.RestServerModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ServerApi {
    @GET("api/is_up")
    Call<IsServerUpModel> getStatus();

    @POST("api")
    Call<RestServerModel> getRestaurants(@Body FooRequest fooRequest);


}
