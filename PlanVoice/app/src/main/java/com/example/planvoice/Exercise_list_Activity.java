package com.example.planvoice;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planvoice.network.ApiService;
import com.example.planvoice.network.ExerciseResponse;
import com.example.planvoice.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Exercise_list_Activity extends AppCompatActivity {

    private LinearLayout exerciseContainer;
    private EditText searchEditText;
    private ApiService apiService;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exerciseContainer = findViewById(R.id.exerciseContainer);
        searchEditText = findViewById(R.id.searchEditText);

        retrofit = RetrofitClient.getClient("http://10.0.2.2:8080/planvoice/");
        apiService = retrofit.create(ApiService.class);

        // 초기 운동 데이터 로드 (예: 전체 운동 목록)
        fetchExercises("등");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 액티비티가 파괴될 때 Retrofit 클라이언트 해제
        if (retrofit != null) {
            retrofit = null;
        }
    }

    public void filterExercises(View view) {
        String bodyPart = (String) view.getTag();
        fetchExercises(bodyPart);
    }

    private void fetchExercises(String bodyPart) {
        apiService.getExercises(bodyPart).enqueue(new Callback<List<ExerciseResponse>>() {
            @Override
            public void onResponse(Call<List<ExerciseResponse>> call, Response<List<ExerciseResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayExercises(response.body());
                } else {
                    Toast.makeText(Exercise_list_Activity.this, "Failed to load exercises", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ExerciseResponse>> call, Throwable t) {
                Toast.makeText(Exercise_list_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayExercises(List<ExerciseResponse> exercises) {
        if (exerciseContainer != null) {
            exerciseContainer.removeAllViews();
            for (ExerciseResponse exercise : exercises) {
                View exerciseView = getLayoutInflater().inflate(R.layout.exercise_item, null);

                ImageView exerciseImage = exerciseView.findViewById(R.id.exerciseImage);
                TextView exerciseName = exerciseView.findViewById(R.id.exerciseName);
                TextView exerciseDescription = exerciseView.findViewById(R.id.exerciseDescription);

                // 이미지 리소스 설정 (drawable 리소스는 이미지 URL 대신 리소스 이름으로 설정)
                int resId = getResources().getIdentifier(exercise.getImageURL().substring(10), "drawable", getPackageName());
                exerciseImage.setImageResource(resId);

                exerciseName.setText(exercise.getExerciseName());
                exerciseDescription.setText(exercise.getExerciseDescription());

                exerciseContainer.addView(exerciseView);
            }
        }
    }
}
