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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_routine_list);

        ListView exercisesListView = findViewById(R.id.exercises_list_view);

        SharedPreferences preferences = getSharedPreferences("PlanPreferences", MODE_PRIVATE);
        String exercisesJson = preferences.getString("exercisesJson", null);

        if (exercisesJson != null) {
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

        Button startRoutineButton = findViewById(R.id.start_routine);
        startRoutineButton.setOnClickListener(v -> {
            // 시작하기 버튼 클릭 시의 로직 추가
        });
    }
}
