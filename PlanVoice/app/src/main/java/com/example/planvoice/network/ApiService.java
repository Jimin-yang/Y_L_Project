package com.example.planvoice.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
            @Field("id") String id,
            @Field("password") String password,
            @Field("name") String name,
            @Field("age") String age,
            @Field("height") String height,
            @Field("weight") String weight,
            @Field("gender") String gender,
            @Field("email") String email,
            @Field("phone") String phone
    );
}
