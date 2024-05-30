package com.example.planvoice;

import android.content.Intent;
import android.os.Bundle;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SigninActivity extends AppCompatActivity {
    private EditText signIdText, signPasswdText, signCheckPasswdText, nameText, ageText, heightText, weightText, emailText, phoneText;
    private RadioGroup genderGroup;
    private Button signinButton;

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

        signIdText = findViewById(R.id.sign_idText);
        signPasswdText = findViewById(R.id.sign_passwdText);
        signCheckPasswdText = findViewById(R.id.sign_check_passwdText);
        nameText = findViewById(R.id.nameText);
        ageText = findViewById(R.id.ageText);
        heightText = findViewById(R.id.heightText);
        weightText = findViewById(R.id.widthText);
        emailText = findViewById(R.id.emailText);
        phoneText = findViewById(R.id.phoneText);
        genderGroup = findViewById(R.id.genderGroup);
        signinButton = findViewById(R.id.signinButton);

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidInput()) {
                    registerUser();
                }
            }
        });
    }

    private boolean isValidInput() {
        String userId = signIdText.getText().toString().trim();
        String password = signPasswdText.getText().toString().trim();
        String confirmPassword = signCheckPasswdText.getText().toString().trim();
        String name = nameText.getText().toString().trim();
        String age = ageText.getText().toString().trim();
        String height = heightText.getText().toString().trim();
        String weight = weightText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String phone = phoneText.getText().toString().trim();
        int selectedGenderId = genderGroup.getCheckedRadioButtonId();

        if (userId.isEmpty()) {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "비밀번호와 비밀번호 확인을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (name.isEmpty()) {
            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (age.isEmpty()) {
            Toast.makeText(this, "나이를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (height.isEmpty()) {
            Toast.makeText(this, "키를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (weight.isEmpty()) {
            Toast.makeText(this, "몸무게를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email.isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.isEmpty()) {
            Toast.makeText(this, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.isEmpty() || !isValidEmail(email)) {
            Toast.makeText(this, "유효한 이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.isEmpty() || !isValidPhone(phone)) {
            Toast.makeText(this, "유효한 전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (selectedGenderId == -1) {
            Toast.makeText(this, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPhone(String phone) {
        String phonePattern = "^[0-9]{10,13}$";
        Pattern pattern = Pattern.compile(phonePattern);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    private void registerUser() {
        final String userId = signIdText.getText().toString().trim();
        final String password = signPasswdText.getText().toString().trim();
        final String name = nameText.getText().toString().trim();
        final String age = ageText.getText().toString().trim();
        final String height = heightText.getText().toString().trim();
        final String weight = weightText.getText().toString().trim();
        final String email = emailText.getText().toString().trim();
        final String phone = phoneText.getText().toString().trim();
        int selectedGenderId = genderGroup.getCheckedRadioButtonId();
        RadioButton selectedGenderButton = findViewById(selectedGenderId);
        final String gender = selectedGenderButton.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2/register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(SigninActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish(); // 현재 액티비티 종료
                            } else {
                                Toast.makeText(SigninActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(SigninActivity.this, "에러: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId);
                params.put("password", password);
                params.put("name", name);
                params.put("age", age);
                params.put("height", height);
                params.put("weight", weight);
                params.put("gender", gender);
                params.put("email", email);
                params.put("phone", phone);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
