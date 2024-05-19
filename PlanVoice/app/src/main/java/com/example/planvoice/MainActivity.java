package com.example.planvoice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.navigation);


        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                // HomeActivity로 이동
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                return true;
            } else if (itemId == R.id.navigation_dashboard) {
                // DashboardActivity로 이동
                startActivity(new Intent(MainActivity.this, StatisticsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_notifications) {
                // NotificationsActivity로 이동
                startActivity(new Intent(MainActivity.this, InformationActivity.class));
                return true;
            }
            return false;
        });

    }
}
