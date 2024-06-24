package com.example.planvoice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planvoice.network.ApiService;
import com.example.planvoice.network.ExerciseResponse;
import com.example.planvoice.network.User;
import com.example.planvoice.network.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ExerciseCompletionActivity extends AppCompatActivity {

    private static final String TAG = "ExerciseCompletionActivity";
    private TextView tvPlanName, tvTotalTime, tvCompletionMessage;
    private Button btnFinish;

    private String planName;
    private long totalTimeMillis;
    private List<ExerciseResponse> exerciseList;
    private User user;
    private ApiService apiService;

    private int chestTime = 0;
    private int shoulderTime = 0;
    private int armTime = 0;
    private int backTime = 0;
    private int legTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_completion);

        // View 초기화
        tvPlanName = findViewById(R.id.tv_plan_name);
        tvTotalTime = findViewById(R.id.tv_total_time);
        tvCompletionMessage = findViewById(R.id.tv_completion_message);
        btnFinish = findViewById(R.id.btn_finish);

        // Intent로부터 데이터 가져오기
        planName = getIntent().getStringExtra("planName");
        totalTimeMillis = getIntent().getLongExtra("totalTime", 0);
        String exercisesJson = getIntent().getStringExtra("exercisesJson");
        int[] exerciseDurations = getIntent().getIntArrayExtra("exerciseDurations");

        Log.d(TAG, "Received planName: " + planName);
        Log.d(TAG, "Received totalTimeMillis: " + totalTimeMillis);
        Log.d(TAG, "Received exercisesJson: " + exercisesJson);
        Log.d(TAG, "Received exerciseDurations: " + exerciseDurations);

        // Exercise 리스트 파싱
        Type exerciseListType = new TypeToken<List<ExerciseResponse>>() {}.getType();
        exerciseList = new Gson().fromJson(exercisesJson, exerciseListType);

        if (exerciseList != null) {
            Log.d(TAG, "exerciseList size: " + exerciseList.size());
        }

        // 사용자 정보 가져오기
        MyApplication app = (MyApplication) getApplication();
        user = app.getUser();

        if (user != null) {
            Log.d(TAG, "User ID: " + user.getId());
        } else {
            Log.e(TAG, "User is null");
        }

        // 각 부위별 운동 시간 계산
        if (exerciseList != null && exerciseDurations != null && exerciseList.size() == exerciseDurations.length) {
            for (int i = 0; i < exerciseList.size(); i++) {
                ExerciseResponse exercise = exerciseList.get(i);
                switch (exercise.getBodyPart()) {
                    case "가슴":
                        chestTime += exerciseDurations[i];
                        break;
                    case "어깨":
                        shoulderTime += exerciseDurations[i];
                        break;
                    case "팔":
                        armTime += exerciseDurations[i];
                        break;
                    case "등":
                        backTime += exerciseDurations[i];
                        break;
                    case "하체":
                        legTime += exerciseDurations[i];
                        break;
                }
            }
        }

        // API 서비스 초기화
        Retrofit retrofit = RetrofitClient.getClient("http://192.168.210.51:8080/planvoice/");
        apiService = retrofit.create(ApiService.class);

        // 운동 정보 표시
        tvPlanName.setText(planName);
        long totalMinutes = (totalTimeMillis / 1000) / 60;
        long totalSeconds = (totalTimeMillis / 1000) % 60;
        tvTotalTime.setText(String.format("%02d:%02d", totalMinutes, totalSeconds));

        // 종료 버튼 클릭 이벤트
        btnFinish.setOnClickListener(v -> {
            saveExerciseDataToServer();
            // 메인 화면으로 이동
            Intent intent = new Intent(ExerciseCompletionActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void saveExerciseDataToServer() {
        if (user != null && exerciseList != null) {
            // 서버로 데이터 전송
            Call<Void> call = apiService.saveExerciseData(user.getId(), planName, (int) (totalTimeMillis / 1000),
                    chestTime, shoulderTime, armTime, backTime, legTime);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "Data saved successfully");
                    } else {
                        Log.e(TAG, "Failed to save data: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, "Error saving data: " + t.getMessage());
                }
            });
        } else {
            if (user == null) {
                Log.e(TAG, "User is null, cannot save data to server");
            }
            if (exerciseList == null) {
                Log.e(TAG, "exerciseList is null, cannot save data to server");
            }
        }
    }
}
