package com.example.planvoice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    private BarChart barChart1;
    private BarChart barChart2;
    private BarChart barChart3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistics);

        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setSelectedItemId(R.id.navigation_dashboard);

        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                // HomeActivity로 이동
                startActivity(new Intent(StatisticsActivity.this, MainActivity.class));
                return true;
            } else if (itemId == R.id.navigation_dashboard) {
                // DashboardActivity로 이동
                startActivity(new Intent(StatisticsActivity.this, StatisticsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_notifications) {
                // NotificationsActivity로 이동
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
        LinearLayout l1 =findViewById(R.id.chatLL1);
        LinearLayout l2 =findViewById(R.id.chatLL2);
        BTN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BTN1.setBackgroundResource(R.drawable.button_text_rounded_background);
                BTN2.setBackgroundColor(Color.parseColor("#00000000"));
                l2.setVisibility(View.GONE);
                l1.setVisibility(View.VISIBLE);
            }
        });
        BTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BTN2.setBackgroundResource(R.drawable.button_text_rounded_background);
                BTN1.setBackgroundColor(Color.parseColor("#00000000"));
                l1.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);
            }
        });
        // 차트1  //
        ArrayList<BarEntry> entry_chart1 = new ArrayList<>(); // 데이터를 담을 Arraylist
        barChart1 = (BarChart) findViewById(R.id.chart1);
        BarData barData1 = new BarData(); // 차트에 담길 데이터

        entry_chart1.add(new BarEntry(1, 1)); //entry_chart1에 좌표 데이터를 담는다.
        entry_chart1.add(new BarEntry(2, 2));
        entry_chart1.add(new BarEntry(3, 3));
        entry_chart1.add(new BarEntry(4, 4));
        entry_chart1.add(new BarEntry(5, 2));
        entry_chart1.add(new BarEntry(6, 8));

        BarDataSet barDataSet1 = new BarDataSet(entry_chart1,"ww"); // 데이터가 담긴 Arraylist 를 BarDataSet 으로 변환한다.
        barDataSet1.setValueTextColor(Color.WHITE);
        barDataSet1.setColor(Color.WHITE); // 해당 BarDataSet 색 설정 :: 각 막대 과 관련된 세팅은 여기서 설정한다.
        barData1.addDataSet(barDataSet1); // 해당 BarDataSet 을 적용될 차트에 들어갈 DataSet 에 넣는다.
        barChart1.setData(barData1); // 차트에 위의 DataSet 을 넣는다.
        barChart1.invalidate(); // 차트 업데이트
        barChart1.setTouchEnabled(false); // 차트 터치 불가능하게
        // 차트1  //

        // 차트2  //
        ArrayList<BarEntry> entry_chart2 = new ArrayList<>(); // 데이터를 담을 Arraylist
        barChart2 = (BarChart) findViewById(R.id.chart2);
        BarData barData2 = new BarData(); // 차트에 담길 데이터

        entry_chart2.add(new BarEntry(1, 4)); //entry_chart1에 좌표 데이터를 담는다.
        entry_chart2.add(new BarEntry(2, 7));
        entry_chart2.add(new BarEntry(3, 2));
        entry_chart2.add(new BarEntry(4, 4));
        entry_chart2.add(new BarEntry(5, 5));
        entry_chart2.add(new BarEntry(6, 2));

        BarDataSet barDataSet2 = new BarDataSet(entry_chart2, "yy"); // 데이터가 담긴 Arraylist 를 BarDataSet 으로 변환한다.
        barDataSet2.setValueTextColor(Color.WHITE);
        barDataSet2.setColor(Color.WHITE); // 해당 BarDataSet 색 설정 :: 각 막대 과 관련된 세팅은 여기서 설정한다.
        barData2.addDataSet(barDataSet2); // 해당 BarDataSet 을 적용될 차트에 들어갈 DataSet 에 넣는다.
        barChart2.setData(barData2); // 차트에 위의 DataSet 을 넣는다.
        barChart2.invalidate(); // 차트 업데이트
        barChart2.setTouchEnabled(false); // 차트 터치 불가능하게
        // 차트2  //

        // 차트3  //
        ArrayList<BarEntry> entry_chart3 = new ArrayList<>(); // 데이터를 담을 Arraylist
        barChart3 = (BarChart) findViewById(R.id.chart3);
        BarData barData3 = new BarData(); // 차트에 담길 데이터

        entry_chart3.add(new BarEntry(1, 5)); //entry_chart1에 좌표 데이터를 담는다.
        entry_chart3.add(new BarEntry(2, 4));
        entry_chart3.add(new BarEntry(3, 3));
        entry_chart3.add(new BarEntry(4, 4));
        entry_chart3.add(new BarEntry(5, 2));
        entry_chart3.add(new BarEntry(6, 4));
        entry_chart3.add(new BarEntry(7, 2));
        entry_chart3.add(new BarEntry(8, 6));

        BarDataSet barDataSet3 = new BarDataSet(entry_chart3,"ww"); // 데이터가 담긴 Arraylist 를 BarDataSet 으로 변환한다.
        barDataSet3.setValueTextColor(Color.WHITE);
        barDataSet3.setColor(Color.WHITE); // 해당 BarDataSet 색 설정 :: 각 막대 과 관련된 세팅은 여기서 설정한다.
        barData3.addDataSet(barDataSet3); // 해당 BarDataSet 을 적용될 차트에 들어갈 DataSet 에 넣는다.
        barChart3.setData(barData3); // 차트에 위의 DataSet 을 넣는다.
        barChart3.invalidate(); // 차트 업데이트
        barChart3.setTouchEnabled(false); // 차트 터치 불가능하게
        // 차트3  //
    }
}