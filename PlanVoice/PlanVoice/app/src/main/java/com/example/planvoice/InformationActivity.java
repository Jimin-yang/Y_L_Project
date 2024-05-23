package com.example.planvoice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class InformationActivity extends AppCompatActivity {
    private  TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_information);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs) ;


        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setSelectedItemId(R.id.navigation_notifications);

        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                // HomeActivity로 이동
                startActivity(new Intent(InformationActivity.this, MainActivity.class));
                return true;
            } else if (itemId == R.id.navigation_dashboard) {
                // DashboardActivity로 이동
                startActivity(new Intent(InformationActivity.this, StatisticsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_notifications) {
                // NotificationsActivity로 이동
                startActivity(new Intent(InformationActivity.this, InformationActivity.class));
                return true;
            }
            return false;
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition() ;
                changeView(pos) ;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // TODO : tab의 상태가 선택되지 않음으로 변경.
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // TODO : 이미 선택된 tab이 다시
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });


    }


    private void changeView(int index) {
        LinearLayout l1 = (LinearLayout) findViewById(R.id.layout1) ;
        LinearLayout l2 = (LinearLayout) findViewById(R.id.layout2) ;


        switch (index) {
            case 0 :
                l1.setVisibility(View.VISIBLE) ;
                l2.setVisibility(View.INVISIBLE) ;
                break ;
            case 1 :
                l2.setVisibility(View.VISIBLE) ;
                l1.setVisibility(View.INVISIBLE) ;
                break ;


        }
    }
}