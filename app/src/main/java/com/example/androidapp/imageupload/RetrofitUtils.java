package com.example.androidapp.imageupload;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if (retrofit != null){
            return retrofit;
        }
        retrofit = new Retrofit.Builder()
                .baseUrl("http://188.166.240.231:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;

    }
}