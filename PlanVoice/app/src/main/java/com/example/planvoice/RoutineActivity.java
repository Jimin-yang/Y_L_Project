package com.example.planvoice;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planvoice.network.ExerciseAdapter;
import com.example.planvoice.network.ExerciseResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class RoutineActivity extends AppCompatActivity {

    private static final String TAG = "RoutineActivity";

    private ExerciseAdapter exerciseAdapter;
    private ListView exercisesListView;
    private Button startRoutineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_routine_list);

        exercisesListView = findViewById(R.id.exercises_list_view);
        startRoutineButton = findViewById(R.id.start_routine);

        loadSelectedPlan();

        startRoutineButton.setOnClickListener(v -> {
            // 시작하기 버튼 클릭 시의 로직 추가
        });
    }

    private void loadSelectedPlan() {
        SharedPreferences preferences = getSharedPreferences("PlanPreferences", MODE_PRIVATE);
        String selectedPlan = preferences.getString("selectedPlan", "근육량 증가 추천 플랜 (초급)");
        int exerciseCount = preferences.getInt("exerciseCount", 0);
        int exerciseTime = preferences.getInt("exerciseTime", 0);
        int caloriesBurned = preferences.getInt("caloriesBurned", 0);
        String exerciseCategories = preferences.getString("exerciseCategories", "");

        // Load the exercise list JSON
        String exercisesJson = preferences.getString("exercisesJson", null);

        if (exercisesJson != null) {
            Log.d(TAG, "Loaded exercises JSON: " + exercisesJson); // Log the loaded JSON

            Gson gson = new Gson();
            Type type = new TypeToken<List<ExerciseResponse>>() {}.getType();
            List<ExerciseResponse> exercises = gson.fromJson(exercisesJson, type);

            if (exercises != null && !exercises.isEmpty()) {
                Log.d(TAG, "Loaded exercises: " + exercises.size());
                for (ExerciseResponse exercise : exercises) {
                    Log.d(TAG, "Exercise: " + exercise.getExerciseName());
                }
                exerciseAdapter = new ExerciseAdapter(this, exercises);
                exercisesListView.setAdapter(exerciseAdapter);
            } else {
                Log.d(TAG, "No exercises found for the selected plan.");
            }
        } else {
            Log.d(TAG, "No exercises JSON found in SharedPreferences.");
        }

        Log.d(TAG, "Loaded plan: " + selectedPlan);
        Log.d(TAG, "Exercise count: " + exerciseCount);
        Log.d(TAG, "Exercise time: " + exerciseTime);
        Log.d(TAG, "Calories burned: " + caloriesBurned);
        Log.d(TAG, "Exercise categories: " + exerciseCategories);
    }
}
