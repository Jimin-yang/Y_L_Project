package com.example.planvoice.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @GET("get_plans.php")
    Call<List<ExercisePlanResponse>> getPlans();

    @GET("get_exercises.php")
    Call<List<ExerciseResponse>> getExercises(@Query("bodyPart") String bodyPart);

    @GET("search_exercises.php")
    Call<List<ExerciseResponse>> searchExercises(@Query("searchText") String searchText);

}
