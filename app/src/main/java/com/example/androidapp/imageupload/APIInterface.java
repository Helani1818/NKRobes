package com.example.androidapp.imageupload;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface
{
    @Multipart
    @POST("/predict")
    Call<Img_Pojo> uploadImage(
            @Part MultipartBody.Part image
    );

//    @Multipart
//    @POST("/lost")
//    Call<LostResponse> uploadLostImage(
//            @Part MultipartBody.Part image
//    );
}
