package com.example.planvoice;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planvoice.network.ExerciseResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ExercisePlayActivity extends AppCompatActivity {

    private static final String TAG = "ExercisePlayActivity";

    private TextView tvTimer, tvExerciseTitle, tvExerciseSubtitle, tvStartTime, tvEndTime;
    private ImageView imgExercise;
    private ImageButton btnPlay, btnBack, btnVoice;
    private Button btnNext, btnPrevious, btnSkip;
    private SeekBar seekBarProgress;
    private List<ExerciseResponse> exerciseList;
    private int currentExerciseIndex = 0;
    private boolean isPaused = false;
    private Handler handler = new Handler();
    private long totalElapsedTime = 0, exerciseElapsedTime = 0;
    private long totalStartTime, exerciseStartTime, pausedTime;
    private Runnable updateTimer;

    private int totalExerciseTime;
    private long[] exerciseTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_play);

        // View 초기화
        tvTimer = findViewById(R.id.tv_timer);
        tvExerciseTitle = findViewById(R.id.tv_exercise_title);
        tvExerciseSubtitle = findViewById(R.id.tv_exercise_subtitle);
        tvStartTime = findViewById(R.id.tv_start_time);
        tvEndTime = findViewById(R.id.tv_end_time);
        imgExercise = findViewById(R.id.img_exercise);
        btnPlay = findViewById(R.id.btn_play);
        btnBack = findViewById(R.id.btn_back);
        btnVoice = findViewById(R.id.btn_voice);
        btnNext = findViewById(R.id.btn_next);
        btnPrevious = findViewById(R.id.btn_previous);
        btnSkip = findViewById(R.id.btn_skip);
        seekBarProgress = findViewById(R.id.seekbar_progress);

        // 뒤로가기 버튼 클릭 이벤트
        btnBack.setOnClickListener(v -> onBackPressed());

        // 운동 리스트 로드
        SharedPreferences preferences = getSharedPreferences("PlanPreferences", MODE_PRIVATE);
        String exercisesJson = preferences.getString("exercisesJson", "");
        Type exerciseListType = new TypeToken<List<ExerciseResponse>>() {}.getType();
        exerciseList = new Gson().fromJson(exercisesJson, exerciseListType);
        totalExerciseTime = preferences.getInt("exerciseTime", 30) * 60; // 총 운동 시간을 초 단위로 변환
        exerciseTimes = new long[exerciseList.size()];

        // 각 운동 시간 분배
        long individualExerciseTime = totalExerciseTime / exerciseList.size();
        for (int i = 0; i < exerciseList.size(); i++) {
            exerciseTimes[i] = individualExerciseTime;
        }

        // 첫 번째 운동 표시
        if (exerciseList != null && !exerciseList.isEmpty()) {
            displayExercise(currentExerciseIndex);
        }

        // 시작/일시정지 버튼 클릭 이벤트
        btnPlay.setOnClickListener(v -> {
            if (isPaused) {
                resumeExercise();
            } else {
                pauseExercise();
            }
        });

        // 다음 운동 버튼 클릭 이벤트
        btnNext.setOnClickListener(v -> {
            if (currentExerciseIndex < exerciseList.size() - 1) {
                currentExerciseIndex++;
                displayExercise(currentExerciseIndex);
                resetExerciseTimer();
            }
        });

        // 이전 운동 버튼 클릭 이벤트
        btnPrevious.setOnClickListener(v -> {
            if (currentExerciseIndex > 0) {
                currentExerciseIndex--;
                displayExercise(currentExerciseIndex);
                resetExerciseTimer();
            }
        });

        // 생략 버튼 클릭 이벤트
        btnSkip.setOnClickListener(v -> {
            if (currentExerciseIndex < exerciseList.size() - 1) {
                currentExerciseIndex++;
                displayExercise(currentExerciseIndex);
                resetExerciseTimer();
            } else {
                // 마지막 운동일 경우, 운동 완료 처리
                finish();
            }
        });

        // 타이머 업데이트
        updateTimer = new Runnable() {
            @Override
            public void run() {
                if (!isPaused) {
                    long currentTime = System.currentTimeMillis();

                    // 총 운동 시간 계산
                    totalElapsedTime += currentTime - totalStartTime;
                    totalStartTime = currentTime;

                    // 현재 운동 시간 계산
                    exerciseElapsedTime += currentTime - exerciseStartTime;
                    exerciseStartTime = currentTime;

                    // 총 운동 시간 표시
                    long totalMinutes = (totalElapsedTime / 1000) / 60;
                    long totalSeconds = (totalElapsedTime / 1000) % 60;
                    tvTimer.setText(String.format("%02d:%02d", totalMinutes, totalSeconds));

                    // 현재 운동 시간 표시
                    long exerciseMinutes = (exerciseElapsedTime / 1000) / 60;
                    long exerciseSeconds = (exerciseElapsedTime / 1000) % 60;
                    tvStartTime.setText(String.format("%02d:%02d", exerciseMinutes, exerciseSeconds));
                    seekBarProgress.setProgress((int) (exerciseElapsedTime / 1000));

                    handler.postDelayed(this, 1000);
                }
            }
        };
    }

    private void displayExercise(int index) {
        ExerciseResponse exercise = exerciseList.get(index);
        tvExerciseTitle.setText(exercise.getExerciseName());
        tvExerciseSubtitle.setText(exercise.getBodyPart());

        // 이미지 로드
        String imageName = exercise.getImageURL();
        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        if (imageResId != 0) {
            imgExercise.setImageResource(imageResId);
        } else {
            Log.e(TAG, "Image not found for: " + imageName);
        }

        // 운동 시간 초기화
        tvStartTime.setText("00:00");
        tvEndTime.setText(String.format("%02d:%02d", exerciseTimes[index] / 60, exerciseTimes[index] % 60));
        seekBarProgress.setMax((int) exerciseTimes[index]);

        // 타이머 초기화
        resetExerciseTimer();
    }

    private void startExercise() {
        totalStartTime = System.currentTimeMillis();
        exerciseStartTime = totalStartTime;
        handler.post(updateTimer);
        btnPlay.setImageResource(R.drawable.pause); // pause 이미지로 교체
        isPaused = false;
    }

    private void pauseExercise() {
        isPaused = true;
        pausedTime = System.currentTimeMillis();
        handler.removeCallbacks(updateTimer);
        btnPlay.setImageResource(R.drawable.play_arrow_40px); // play 이미지로 교체
    }

    private void resumeExercise() {
        isPaused = false;
        totalStartTime = System.currentTimeMillis();
        exerciseStartTime = totalStartTime;
        handler.post(updateTimer);
        btnPlay.setImageResource(R.drawable.pause); // pause 이미지로 교체
    }

    private void resetExerciseTimer() {
        exerciseElapsedTime = 0;
        exerciseStartTime = System.currentTimeMillis();
        tvStartTime.setText("00:00");
        seekBarProgress.setProgress(0);
    }
}
