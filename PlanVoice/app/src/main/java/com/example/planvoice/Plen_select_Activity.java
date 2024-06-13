package com.example.planvoice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.planvoice.network.ApiService;
import com.example.planvoice.network.ExercisePlanResponse;
import com.example.planvoice.network.ExerciseResponse;
import com.example.planvoice.network.RetrofitClient;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Plen_select_Activity extends AppCompatActivity {

    private static final String TAG = "Plen_select_Activity";
    private Button selectedPlanButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plen_select);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setTitle("플랜 선택");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("PlanVoicePrefs", MODE_PRIVATE);
        selectedPlanButton = findViewById(R.id.selected_plan_button);

        fetchExercisePlans();
    }

    private void fetchExercisePlans() {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2/planvoice/");
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<ExercisePlanResponse>> call = apiService.getExercisePlans();
        call.enqueue(new Callback<List<ExercisePlanResponse>>() {
            @Override
            public void onResponse(Call<List<ExercisePlanResponse>> call, Response<List<ExercisePlanResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ExercisePlanResponse> plans = response.body();
                    Log.d(TAG, "Received plans: " + new Gson().toJson(plans));
                    for (ExercisePlanResponse plan : plans) {
                        setupButtonForPlan(plan);
                    }
                } else {
                    Log.e(TAG, "Failed to fetch plans: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ExercisePlanResponse>> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }

    private void setupButtonForPlan(ExercisePlanResponse plan) {
        String planName = plan.getPlanName();
        String buttonIdName = "plan_" + planName.toLowerCase().replace(" ", "_").replace("(", "").replace(")", "");
        int buttonId = getResources().getIdentifier(buttonIdName, "id", getPackageName());
        Button planButton = findViewById(buttonId);
        if (planButton != null) {
            planButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPlanButton.setText(planName);
                    List<ExerciseResponse> exercises = plan.getExercises();
                    Log.d(TAG, "Selected Plan: " + planName);
                    if (exercises != null) {
                        int exerciseCount = exercises.size();
                        int exerciseTime = exerciseCount * 15; // 임의로 각 운동에 15분 할당
                        int caloriesBurned = exerciseCount * 100; // 임의로 각 운동에 100kcal 할당

                        String exerciseCategories = getExerciseCategories(exercises);

                        // SharedPreferences에 선택한 플랜 정보 저장
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("selectedPlan", planName);
                        editor.putInt("exerciseCount", exerciseCount);
                        editor.putInt("exerciseTime", exerciseTime);
                        editor.putInt("caloriesBurned", caloriesBurned);
                        editor.putString("exerciseCategories", exerciseCategories);
                        editor.apply();

                        // 로그 추가
                        Log.d(TAG, "Plan saved: " + planName);
                        Log.d(TAG, "Exercise count: " + exerciseCount);
                        Log.d(TAG, "Exercise time: " + exerciseTime);
                        Log.d(TAG, "Calories burned: " + caloriesBurned);
                        Log.d(TAG, "Exercise categories: " + exerciseCategories);
                    }
                }
            });
        } else {
            Log.e(TAG, "Button not found for plan: " + planName);
        }
    }

    private String getExerciseCategories(List<ExerciseResponse> exercises) {
        // 운동 부위 추출 로직 구현
        Set<String> categories = new HashSet<>();
        for (ExerciseResponse exercise : exercises) {
            categories.add(exercise.getBodyPart());
        }
        return String.join(", ", categories);
    }
}
