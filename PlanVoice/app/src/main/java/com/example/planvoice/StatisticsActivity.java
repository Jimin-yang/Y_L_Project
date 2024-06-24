package com.example.planvoice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.planvoice.network.ApiService;
import com.example.planvoice.network.DataResponse;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatisticsActivity extends AppCompatActivity {

    private BarChart barChart1;
    private BarChart barChart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistics);

        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setSelectedItemId(R.id.navigation_dashboard);

        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(StatisticsActivity.this, MainActivity.class));
                return true;
            } else if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(StatisticsActivity.this, StatisticsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_notifications) {
                startActivity(new Intent(StatisticsActivity.this, InformationActivity.class));
                return true;
            }
            return false;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button BTN1 = findViewById(R.id.bu1);
        Button BTN2 = findViewById(R.id.bu2);
        LinearLayout l1 = findViewById(R.id.chatLL1);
        LinearLayout l2 = findViewById(R.id.chatLL2);
        BTN1.setOnClickListener(v -> {
            BTN1.setBackgroundResource(R.drawable.button_text_rounded_background);
            BTN2.setBackgroundColor(Color.parseColor("#00000000"));
            l2.setVisibility(View.GONE);
            l1.setVisibility(View.VISIBLE);
        });
        BTN2.setOnClickListener(v -> {
            BTN2.setBackgroundResource(R.drawable.button_text_rounded_background);
            BTN1.setBackgroundColor(Color.parseColor("#00000000"));
            l1.setVisibility(View.GONE);
            l2.setVisibility(View.VISIBLE);
        });

        barChart1 = findViewById(R.id.chart1);
        barChart2 = findViewById(R.id.chart2);

        // Retrofit 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/planvoice/") // PHP 파일이 위치한 서버 URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // ApiService 인터페이스와 연결
        ApiService apiService = retrofit.create(ApiService.class);

        // 서버에서 데이터 요청
        Call<DataResponse> call = apiService.getExerciseData();
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DataResponse dataResponse = response.body();

                    // 부위별 운동 시간 데이터 처리
                    Map<String, Integer> bodyPartData = dataResponse.getExerciseByBodyPart();
                    if (bodyPartData != null && !bodyPartData.isEmpty()) {
                        setupChart1(bodyPartData);
                    }

                    // 날짜별 운동 총 시간 데이터 처리
                    Map<String, Integer> dateData = dataResponse.getExerciseByDate();
                    if (dateData != null && !dateData.isEmpty()) {
                        setupChart2(dateData);
                    }
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                // 네트워크 오류 처리
                t.printStackTrace();
                Toast.makeText(StatisticsActivity.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupChart1(Map<String, Integer> bodyPartData) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, bodyPartData.get("chest")));
        entries.add(new BarEntry(1, bodyPartData.get("shoulder")));
        entries.add(new BarEntry(2, bodyPartData.get("arm")));
        entries.add(new BarEntry(3, bodyPartData.get("back")));
        entries.add(new BarEntry(4, bodyPartData.get("leg")));

        BarDataSet barDataSet = new BarDataSet(entries, "부위별 운동 시간");
        barDataSet.setColor(Color.WHITE);
        barDataSet.setValueTextColor(Color.WHITE);

        BarData barData = new BarData(barDataSet);
        barChart1.setData(barData);

        // X축 설정
        String[] bodyParts = {"가슴", "어깨", "팔", "등", "다리"}; // X축 레이블 텍스트
        XAxis xAxis = barChart1.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(bodyParts));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // 간격
        xAxis.setLabelCount(bodyParts.length); // 레이블 개수
        xAxis.setTextColor(Color.WHITE);
        barChart1.invalidate();
        barChart1.setTouchEnabled(false);
    }

    private void setupChart2(Map<String, Integer> dateData) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, Integer> entry : dateData.entrySet()) {
            entries.add(new BarEntry(index++, entry.getValue()));
        }

        BarDataSet barDataSet = new BarDataSet(entries, "날짜별 운동 총 시간");
        barDataSet.setColor(Color.WHITE);
        barDataSet.setValueTextColor(Color.WHITE);

        BarData barData = new BarData(barDataSet);
        barChart2.setData(barData);

        // X축 설정
        String[] dates = formatDateKeys(dateData); // 날짜 포맷 변경
        XAxis xAxis = barChart2.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // 간격
        xAxis.setLabelCount(dates.length); // 레이블 개수
        xAxis.setTextColor(Color.WHITE);
        String startDate = dates[0]; // 첫 번째 날짜
        String endDate = dates[dates.length - 1]; // 마지막 날짜
        TextView dateRangeTextView = findViewById(R.id.date_date);
        dateRangeTextView.setText(startDate + " ~ " + endDate);
        barDataSet.setValueTextColor(Color.WHITE);
        barChart2.invalidate();
        barChart2.setTouchEnabled(false);
    }

    private String[] formatDateKeys(Map<String, Integer> dateData) {
        String[] formattedDates = new String[dateData.size()];
        int i = 0;
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM-dd");

        for (String key : dateData.keySet()) {
            LocalDate date = LocalDate.parse(key, inputFormatter);
            String formattedDate = date.format(outputFormatter);
            formattedDates[i++] = formattedDate;
        }

        return formattedDates;
    }
}
