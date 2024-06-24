package com.example.planvoice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.planvoice.network.ApiService;
import com.example.planvoice.network.RetrofitClient;
import com.example.planvoice.network.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Retrofit;

import android.widget.Toast;

import retrofit2.Callback;
import retrofit2.Response;

public class InformationActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private TextView nameTextView;
    private TextView heightTextView;
    private TextView weightTextView;
    private TextView phoneTextView;
    private TextView emailTextView;
    private Button logoutButton;

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
        logoutButton = findViewById(R.id.btn_logout);

        logoutButton.setOnClickListener(v -> {
            MyApplication app = (MyApplication) getApplication();
            app.setUser(null);  // 사용자 정보 초기화
            Intent intent = new Intent(InformationActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // 이전 액티비티 스택을 모두 제거
            startActivity(intent);
            finish();
        });

        ImageButton renameButton = findViewById(R.id.user_rename);
        renameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRenameDialog();
            }
        });

        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setSelectedItemId(R.id.navigation_notifications);

        MyApplication app = (MyApplication) getApplication();
        User user = app.getUser();

        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Intent intent;
            if (itemId == R.id.navigation_home) {
                intent = new Intent(InformationActivity.this, MainActivity.class);
            } else if (itemId == R.id.navigation_dashboard) {
                intent = new Intent(InformationActivity.this, StatisticsActivity.class);
            } else if (itemId == R.id.navigation_notifications) {
                intent = new Intent(InformationActivity.this, InformationActivity.class);
            } else {
                return false;
            }

            startActivity(intent);
            return true;
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
        if (user != null) {
            setUserInformation(user);
        }
    }

    private void setUserInformation(User user) {
        nameTextView.setText(user.getName());
        heightTextView.setText(String.format("%d cm", user.getHeight()));
        weightTextView.setText(String.format("%d kg", user.getWeight()));
        phoneTextView.setText(user.getPhone());
        emailTextView.setText(user.getEmail());
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

    private void showRenameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("사용자 정보 변경");
        View viewInflated = getLayoutInflater().inflate(R.layout.dialog_update_user, null);
        builder.setView(viewInflated);
        EditText editName = viewInflated.findViewById(R.id.edit_name);
        EditText editHeight = viewInflated.findViewById(R.id.edit_height);
        EditText editWeight = viewInflated.findViewById(R.id.edit_weight);
        EditText editPhone = viewInflated.findViewById(R.id.edit_phone);
        EditText editEmail = viewInflated.findViewById(R.id.edit_email);

        MyApplication app = (MyApplication) getApplication();
        User user = app.getUser();
        if (user != null) {
            editName.setText(user.getName());
            editHeight.setText(String.valueOf(user.getHeight()));
            editWeight.setText(String.valueOf(user.getWeight()));
            editPhone.setText(user.getPhone());
            editEmail.setText(user.getEmail());
        }

        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = editName.getText().toString().trim();
                int newHeight = Integer.parseInt(editHeight.getText().toString().trim());
                int newWeight = Integer.parseInt(editWeight.getText().toString().trim());
                String newPhone = editPhone.getText().toString().trim();
                String newEmail = editEmail.getText().toString().trim();
                int userId = user.getId();

                // Update user information
                updateUser(userId, newName, newHeight, newWeight, newPhone, newEmail);
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void updateUser(int id, String newName, int newHeight, int newWeight, String newPhone, String newEmail) {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8080/planvoice/"); // 10.0.2.2
        ApiService apiService = retrofit.create(ApiService.class);

        Call<User> call = apiService.updateUser(id, newName, newHeight, newWeight, newPhone, newEmail);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User updatedUser = response.body();

                    // 서버에서 받은 업데이트된 사용자 정보를 UI에 반영
                    setUserInformation(updatedUser);

                    // 필요에 따라 MyApplication 같은 곳에 사용자 정보를 저장할 수도 있습니다.
                    // MyApplication app = (MyApplication) getApplication();
                    // app.setUser(updatedUser);

                    Toast.makeText(InformationActivity.this, "사용자 정보가 업데이트되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    // 서버로부터 유효하지 않은 응답이 왔을 경우 처리
                    Log.e("UpdateUser", "Failed to get user data. Response code: " + response.code());
                    Toast.makeText(InformationActivity.this, "사용자 정보 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // 네트워크 오류 처리
                Log.e("UpdateUser", "Network error: " + t.getMessage());
                Toast.makeText(InformationActivity.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
