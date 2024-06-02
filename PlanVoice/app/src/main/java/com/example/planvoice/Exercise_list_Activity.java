package com.example.planvoice;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.planvoice.network.ApiService;
import com.example.planvoice.network.ExerciseAdapter;
import com.example.planvoice.network.ExerciseResponse;
import com.example.planvoice.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Exercise_list_Activity extends AppCompatActivity {

    private ListView exerciseListView;
    private EditText searchEditText;
    private ApiService apiService;
    private Retrofit retrofit;
    private ExerciseAdapter adapter;
    private ImageButton searchButton;
    private List<ExerciseResponse> allExercises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        searchEditText = findViewById(R.id.searchEditText);
        exerciseListView = findViewById(R.id.exercise_view);
        ImageButton searchButton = findViewById(R.id.exercise_searchButton);

        retrofit = RetrofitClient.getClient("http://10.0.2.2:8080/planvoice/");
        apiService = retrofit.create(ApiService.class);

        // 초기 운동 데이터 로드 (예: 전체 운동 목록)
        fetchExercises("등");
        getSupportActionBar().setTitle("운동 목록");
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEditText.getText().toString().trim();
                if (!searchText.isEmpty()) {
                    searchExercises(searchText);
                } else {
                    Toast.makeText(Exercise_list_Activity.this, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        adapter = new ExerciseAdapter(this, exercises);
        exerciseListView.setAdapter(adapter);
    }

    private void searchExercises(String searchText) {
        apiService.searchExercises(searchText).enqueue(new Callback<List<ExerciseResponse>>() {
            @Override
            public void onResponse(Call<List<ExerciseResponse>> call, Response<List<ExerciseResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayExercises(response.body());
                } else {
                    Toast.makeText(Exercise_list_Activity.this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ExerciseResponse>> call, Throwable t) {
                Toast.makeText(Exercise_list_Activity.this, "검색 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}