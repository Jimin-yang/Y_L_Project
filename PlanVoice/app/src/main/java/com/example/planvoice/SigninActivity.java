package com.example.planvoice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.planvoice.network.ApiService;
import com.example.planvoice.network.RegisterResponse;
import com.example.planvoice.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

public class SigninActivity extends AppCompatActivity {

    private EditText idEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText nameEditText;
    private EditText ageEditText;
    private EditText heightEditText;
    private EditText weightEditText;
    private RadioGroup genderRadioGroup;
    private EditText emailEditText;
    private EditText phoneEditText;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        idEditText = findViewById(R.id.sign_idText);
        passwordEditText = findViewById(R.id.sign_passwdText);
        confirmPasswordEditText = findViewById(R.id.sign_check_passwdText);
        nameEditText = findViewById(R.id.nameText);
        ageEditText = findViewById(R.id.ageText);
        heightEditText = findViewById(R.id.heightText);
        weightEditText = findViewById(R.id.weightText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        emailEditText = findViewById(R.id.emailText);
        phoneEditText = findViewById(R.id.phoneText);
        Button registerButton = findViewById(R.id.registerButton);

        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8080/planvoice/");
        apiService = retrofit.create(ApiService.class);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String id = idEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String height = heightEditText.getText().toString();
        String weight = weightEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String gender = ((RadioButton) findViewById(genderRadioGroup.getCheckedRadioButtonId())).getText().toString();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(SigninActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<RegisterResponse> call = apiService.register(id, password, name, age, height, weight, gender, email, phone);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("SigninActivity", "Response: " + response.body().toString());
                    Toast.makeText(SigninActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("SigninActivity", "Response error: " + errorBody);
                        Toast.makeText(SigninActivity.this, "Failed to register: " + errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("SigninActivity", "Failure: " + t.getMessage());
                Toast.makeText(SigninActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
