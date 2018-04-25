package com.example.kzvda.menumanagementsystem.serverApi;

import com.example.kzvda.menumanagementsystem.serverApi.Interfaces.ServerApi;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit INSTANCE;

    private static ServerApi SERVER_API;

    public static ServerApi getApi() {
        if (SERVER_API == null) {
            synchronized (RetrofitInstance.class) {
                if (SERVER_API == null) {
                    INSTANCE = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                    SERVER_API = INSTANCE.create(ServerApi.class);

                }
            }
        }
        return SERVER_API;
    }
}
