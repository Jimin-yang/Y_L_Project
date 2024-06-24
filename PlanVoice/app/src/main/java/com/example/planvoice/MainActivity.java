package com.example.planvoice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.planvoice.network.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private User user;
    private TextView tvWorkoutPlan, tvExerciseCount, tvExerciseTime, tvCategories;
    private Button startBTN;
    private ImageButton menuBTN, settingBTN;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Intent로 전달된 사용자 정보 가져오기
        user = (User) getIntent().getSerializableExtra("user");

        // View 초기화
        tvWorkoutPlan = findViewById(R.id.tv_workout_plan);
        tvExerciseCount = findViewById(R.id.tv_exercise_count);
        tvExerciseTime = findViewById(R.id.tv_exercise_time);
        // tvCaloriesBurned = findViewById(R.id.tv_calories_burned); // 주석 처리
        tvCategories = findViewById(R.id.tv_categories);

        navView = findViewById(R.id.navigation);
        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                return true;
            } else if (itemId == R.id.navigation_dashboard) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                intent.putExtra("user", user);  // 사용자 정보 전달
                startActivity(intent);
                return true;
            } else if (itemId == R.id.navigation_notifications) {
                Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                intent.putExtra("user", user);  // 사용자 정보 전달
                startActivity(intent);
                return true;
            }
            return false;
        });

        startBTN = findViewById(R.id.btn_start_workout);
        startBTN.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RoutineActivity.class);
            intent.putExtra("user", user);  // 사용자 정보 전달
            startActivity(intent);
        });

        menuBTN = findViewById(R.id.toolbar).findViewById(R.id.menu_icon);
        menuBTN.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Exercise_list_Activity.class);
            startActivity(intent);
        });

        settingBTN = findViewById(R.id.toolbar).findViewById(R.id.settings_icon);
        settingBTN.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Plen_select_Activity.class);
            intent.putExtra("user", user);  // 사용자 정보 전달
            startActivity(intent);
        });

        loadSelectedPlan();
    }

    private void loadSelectedPlan() {
        SharedPreferences preferences = getSharedPreferences("PlanPreferences", MODE_PRIVATE);
        String selectedPlan = preferences.getString("selectedPlan", "근육량 증가 추천 플랜 (초급)");
        int exerciseCount = preferences.getInt("exerciseCount", 0);
        int exerciseTime = preferences.getInt("exerciseTime", 0);
        // int caloriesBurned = preferences.getInt("caloriesBurned", 0); // 주석 처리
        String exerciseCategories = preferences.getString("exerciseCategories", "");

        tvWorkoutPlan.setText(selectedPlan);
        tvExerciseCount.setText(exerciseCount + "개의 운동");
        tvExerciseTime.setText(exerciseTime + "분");
        // tvCaloriesBurned.setText(caloriesBurned + "kcal"); // 주석 처리
        tvCategories.setText("운동부위: " + exerciseCategories);

        Log.d(TAG, "Loaded plan: " + selectedPlan);
        Log.d(TAG, "Exercise count: " + exerciseCount);
        Log.d(TAG, "Exercise time: " + exerciseTime);
        // Log.d(TAG, "Calories burned: " + caloriesBurned); // 주석 처리
        Log.d(TAG, "Exercise categories: " + exerciseCategories);
    }
}
