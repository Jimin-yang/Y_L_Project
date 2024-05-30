package com.example.planvoice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.planvoice.network.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class InformationActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private TextView nameTextView;
    private TextView heightTextView;
    private TextView weightTextView;
    private TextView phoneTextView;
    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_information);

        tabLayout = findViewById(R.id.tabs);
        nameTextView = findViewById(R.id.nameText);
        heightTextView = findViewById(R.id.heightText);
        weightTextView = findViewById(R.id.weightText);
        phoneTextView = findViewById(R.id.phoneText);
        emailTextView = findViewById(R.id.emailText);

        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setSelectedItemId(R.id.navigation_notifications);

        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(InformationActivity.this, MainActivity.class));
                return true;
            } else if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(InformationActivity.this, StatisticsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_notifications) {
                startActivity(new Intent(InformationActivity.this, InformationActivity.class));
                return true;
            }
            return false;
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                changeView(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // TODO : tab의 상태가 선택되지 않음으로 변경.
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // TODO : 이미 선택된 tab이 다시 선택됨.
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 사용자 정보 설정
        User user = (User) getIntent().getSerializableExtra("user");

        if (user != null) {
            nameTextView.setText(user.getName());
            heightTextView.setText(String.format("%d cm", user.getHeight()));
            weightTextView.setText(String.format("%d kg", user.getWeight()));
            phoneTextView.setText(user.getPhone());
            emailTextView.setText(user.getEmail());
        }
    }

    private void changeView(int index) {
        LinearLayout l1 = findViewById(R.id.layout1);
        LinearLayout l2 = findViewById(R.id.layout2);

        switch (index) {
            case 0:
                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.INVISIBLE);
                break;
            case 1:
                l2.setVisibility(View.VISIBLE);
                l1.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
