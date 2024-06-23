package com.example.planvoice;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExerciseCompletionActivity extends AppCompatActivity {

    private TextView tvPlanName, tvTotalTime;
    private Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_completion);

        tvPlanName = findViewById(R.id.tv_plan_name);
        tvTotalTime = findViewById(R.id.tv_total_time);
        btnFinish = findViewById(R.id.btn_finish);

        // Intent로부터 데이터 가져오기
        String planName = getIntent().getStringExtra("planName");
        long totalTimeMillis = getIntent().getLongExtra("totalTime", 0); // 밀리초 단위로 받아오기

        // 시간 형식 변환
        long totalTimeSeconds = totalTimeMillis / 1000;
        String formattedTime = String.format("%02d:%02d", totalTimeSeconds / 60, totalTimeSeconds % 60);

        // 데이터 설정
        tvPlanName.setText(planName);
        tvTotalTime.setText(formattedTime);

        // 종료 버튼 클릭 이벤트
        btnFinish.setOnClickListener(v -> finish());
    }
}
