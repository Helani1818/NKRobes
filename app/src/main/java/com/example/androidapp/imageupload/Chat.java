package com.example.androidapp.imageupload;

import com.example.androidapp.ViewHolder.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Chat {


    @POST("api-v1/api/chat")
    Call<Boolean> message(@Body Message message);

    @GET("api-v1/api/chat/{user}/{touser}")
    Call<List<Message>> getAll(@Path("user") String user, @Path("touser") String touser);

}
