package com.example.planvoice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planvoice.network.User;

public class MainActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Intent로 전달된 사용자 정보 가져오기
        user = (User) getIntent().getSerializableExtra("user");

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
        startBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RoutineActivity.class);
                intent.putExtra("user", user);  // 사용자 정보 전달
                startActivity(intent);
            }
        });

        ImageButton menuBTN  = findViewById(R.id.toolbar).findViewById(R.id.menu_icon);
        menuBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Exercise_list_Activity.class);
                intent.putExtra("user", user);  // 사용자 정보 전달
                startActivity(intent);
            }
        });

        ImageButton settingBTN  = findViewById(R.id.toolbar).findViewById(R.id.settings_icon);
        settingBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Plen_select_Activity.class);
                intent.putExtra("user", user);  // 사용자 정보 전달
                startActivity(intent);
            }
        });
    }
}
