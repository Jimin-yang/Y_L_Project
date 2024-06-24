package com.example.planvoice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planvoice.network.ApiService;
import com.example.planvoice.network.ExercisePlan;
import com.example.planvoice.network.ExercisePlanResponse;
import com.example.planvoice.network.ExerciseResponse;
import com.example.planvoice.network.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Plen_select_Activity extends AppCompatActivity {

    private static final String TAG = "Plen_select_Activity";
    private Button selectedPlanButton;
    private Map<String, ExercisePlan> exercisePlansMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plen_select);

        getSupportActionBar().setTitle("플랜 선택");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedPlanButton = findViewById(R.id.selected_plan_button);

        exercisePlansMap = new HashMap<>();

        // API 호출하여 모든 플랜을 가져옴
        loadExercisePlans();
    }


    private void loadExercisePlans() {
        Retrofit retrofit = RetrofitClient.getClient("http://192.168.210.51:8080/planvoice/");
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<ExercisePlanResponse>> call = apiService.getPlans();
        call.enqueue(new Callback<List<ExercisePlanResponse>>() {
            @Override
            public void onResponse(Call<List<ExercisePlanResponse>> call, Response<List<ExercisePlanResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ExercisePlanResponse> planResponses = response.body();
                    for (ExercisePlanResponse planResponse : planResponses) {
                        ExercisePlan plan = planResponse.toExercisePlan();
                        Log.d(TAG, "Server response for plan: " + plan.getPlanName() + ", Exercises: " + new Gson().toJson(plan.getExercises()));
                        exercisePlansMap.put(plan.getPlanName(), plan);
                        Log.d(TAG, "Loaded plan: " + plan.getPlanName() + " with exercises: " + plan.getExercises().size());
                    }
                    setupPlanButtons();
                } else {
                    Log.e(TAG, "Failed to load plans: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ExercisePlanResponse>> call, Throwable t) {
                Log.e(TAG, "Failed to load plans", t);
            }
        });
    }


    private void setupPlanButtons() {
        for (Map.Entry<String, ExercisePlan> entry : exercisePlansMap.entrySet()) {
            String planName = entry.getKey();
            int buttonId = getButtonIdForPlanName(planName);
            if (buttonId != 0) {
                setupButton(buttonId, planName);
            } else {
                Log.e(TAG, "Button not found for plan: " + planName);
            }
        }
    }

    private int getButtonIdForPlanName(String planName) {
        switch (planName) {
            case "근육량 증가 추천 플랜 (초급)": return R.id.plan_muscle_gain_beginner;
            case "근육량 증가 추천 플랜 (입문)": return R.id.plan_muscle_gain_intro;
            case "근육량 증가 추천 플랜 (중급)": return R.id.plan_muscle_gain_intermediate;
            case "근육량 증가 추천 플랜 (고급)": return R.id.plan_muscle_gain_advanced;
            case "체지방 감소 추천 플랜 (입문)": return R.id.plan_fat_loss_intro;
            case "체지방 감소 추천 플랜 (중급)": return R.id.plan_fat_loss_intermediate;
            case "체지방 감소 추천 플랜 (고급)": return R.id.plan_fat_loss_advanced;
            case "현재 상태 유지 플랜 (입문)": return R.id.plan_maintain_intro;
            case "현재 상태 유지 플랜 (중급)": return R.id.plan_maintain_intermediate;
            case "현재 상태 유지 플랜 (고급)": return R.id.plan_maintain_advanced;
            default: return 0;
        }
    }

    private void setupButton(int buttonId, String planName) {
        Button planButton = findViewById(buttonId);
        if (planButton != null) {
            planButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPlanButton.setText(planName);
                    ExercisePlan selectedPlan = exercisePlansMap.get(planName);
                    if (selectedPlan != null) {
                        // PlanConfig를 통해 운동 개수와 예상 소모 시간을 가져옴
                        PlanConfig.PlanDetails planDetails = PlanConfig.getPlanDetails(planName);

                        // 전체 운동 리스트 가져오기
                        List<ExerciseResponse> allExercises = selectedPlan.getExercises();
                        List<ExerciseResponse> limitedExercises = new ArrayList<>();

                        Log.d(TAG, "Plan: " + planName + " has total exercises: " + allExercises.size());

                        // PlanConfig의 개수에 맞게 운동을 추가
                        int exerciseCount = planDetails.exerciseCount; // 올바른 운동 개수 설정
                        for (int i = 0; i < exerciseCount && i < allExercises.size(); i++) {
                            limitedExercises.add(allExercises.get(i));
                        }

                        Log.d(TAG, "Limited exercises count for plan " + planName + ": " + limitedExercises.size());

                        SharedPreferences preferences = getSharedPreferences("PlanPreferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("selectedPlan", planName);
                        editor.putInt("exerciseCount", limitedExercises.size());
                        editor.putInt("exerciseTime", planDetails.estimatedTime);
                        editor.putString("exerciseCategories", getExerciseCategories(limitedExercises));
                        editor.putString("exercisesJson", new Gson().toJson(limitedExercises));
                        editor.apply();

                        Log.d(TAG, "Plan saved: " + planName);
                        Log.d(TAG, "Exercise count: " + limitedExercises.size());
                        Log.d(TAG, "Exercise time: " + planDetails.estimatedTime);

                        // 선택된 플랜의 운동 목록을 로그로 출력
                        for (ExerciseResponse exercise : limitedExercises) {
                            Log.d(TAG, "Exercise: " + exercise.getExerciseName() + ", BodyPart: " + exercise.getBodyPart());
                        }

                        // 여기에서 Intent에 선택한 플랜의 이름을 추가하여 MainActivity로 전달
                        Intent resultIntent = new Intent(Plen_select_Activity.this, MainActivity.class);
                        resultIntent.putExtra("selectedPlan", planName);
                        resultIntent.putExtra("exerciseCount", limitedExercises.size());
                        resultIntent.putExtra("exerciseTime", planDetails.estimatedTime);
                        startActivity(resultIntent);
                    } else {
                        Log.e(TAG, "Selected plan not found: " + planName);
                    }
                }
            });
        }
    }






    private String getExerciseCategories(List<ExerciseResponse> exercises) {
        StringBuilder categories = new StringBuilder();
        for (ExerciseResponse exercise : exercises) {
            if (categories.length() > 0) {
                categories.append(", ");
            }
            categories.append(exercise.getBodyPart());
        }
        return categories.toString();
    }
}
