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

    @FormUrlEncoded
    @POST("update_user.php")
    Call<User> updateUser(
            @Field("userId") int id,
            @Field("newName") String newName,
            @Field("newHeight") int newHeight,
            @Field("newWeight") int newWeight,
            @Field("newPhone") String newPhone,
            @Field("newEmail") String newEmail
    );

    @FormUrlEncoded
    @POST("save_exercise_data.php")
    Call<Void> saveExerciseData(
            @Field("user_id") int userId,
            @Field("plan_name") String planName,
            @Field("total_time") int totalTime,
            @Field("chest_time") int chestTime,
            @Field("shoulder_time") int shoulderTime,
            @Field("arm_time") int armTime,
            @Field("back_time") int backTime,
            @Field("leg_time") int legTime
    );

    @GET("get_plans.php")
    Call<List<ExercisePlanResponse>> getPlans();

    @GET("get_exercises.php")
    Call<List<ExerciseResponse>> getExercises(@Query("bodyPart") String bodyPart);

    @GET("search_exercises.php")
    Call<List<ExerciseResponse>> searchExercises(@Query("searchText") String searchText);
}
