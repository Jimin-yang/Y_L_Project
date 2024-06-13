package com.example.planvoice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planvoice.network.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private User user;
    private SharedPreferences sharedPreferences;
    private TextView tvWorkoutPlan;
    private TextView tvCategories;
    private TextView tvExerciseCount;
    private TextView tvExerciseTime;
    private TextView tvCaloriesBurned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Intent로 전달된 사용자 정보 가져오기
        user = (User) getIntent().getSerializableExtra("user");

        sharedPreferences = getSharedPreferences("PlanVoicePreferences", MODE_PRIVATE);

        // UI 요소 초기화
        tvWorkoutPlan = findViewById(R.id.tv_workout_plan);
        tvCategories = findViewById(R.id.tv_categories);
        tvExerciseCount = findViewById(R.id.tv_exercise_count);
        tvExerciseTime = findViewById(R.id.time_exercise_count);
        tvCaloriesBurned = findViewById(R.id.kcal_exercise_count);

        // SharedPreferences에서 선택된 플랜 정보 가져오기
        updatePlanInfo();

        BottomNavigationView navView = findViewById(R.id.navigation);
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

        Button startBTN = findViewById(R.id.btn_start_workout);
        startBTN.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RoutineActivity.class);
            intent.putExtra("user", user);  // 사용자 정보 전달
            startActivity(intent);
        });

        ImageButton menuBTN  = findViewById(R.id.toolbar).findViewById(R.id.menu_icon);
        menuBTN.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Exercise_list_Activity.class);
            //intent.putExtra("user", user);  // 사용자 정보 전달
            startActivity(intent);
        });

        ImageButton settingBTN  = findViewById(R.id.toolbar).findViewById(R.id.settings_icon);
        settingBTN.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Plen_select_Activity.class);
            intent.putExtra("user", user);  // 사용자 정보 전달
            startActivity(intent);
        });
    }

    private void updatePlanInfo() {
        String planName = sharedPreferences.getString("selectedPlan", "근육량 증가 추천 플랜 (초급)");
        int exerciseCount = sharedPreferences.getInt("exerciseCount", 6);
        int exerciseTime = sharedPreferences.getInt("exerciseTime", 90);
        int caloriesBurned = sharedPreferences.getInt("caloriesBurned", 709);
        String exerciseCategories = sharedPreferences.getString("exerciseCategories", "가슴, 팔, 다리");

        // 로그 추가
        Log.d("MainActivity", "Plan loaded: " + planName);
        Log.d("MainActivity", "Exercise count: " + exerciseCount);
        Log.d("MainActivity", "Exercise time: " + exerciseTime);
        Log.d("MainActivity", "Calories burned: " + caloriesBurned);
        Log.d("MainActivity", "Exercise categories: " + exerciseCategories);

        tvWorkoutPlan.setText(planName);
        tvCategories.setText("운동부위: " + exerciseCategories);
        tvExerciseCount.setText(exerciseCount + "개의 운동");
        tvExerciseTime.setText(exerciseTime + "분");
        tvCaloriesBurned.setText(caloriesBurned + "kcal");
    }


}
