package com.example.test.GUI;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.test.R;

public class Login extends AppCompatActivity {

    //GUI BUTTON
    private Button btnLogin;
    private Button btnRegister;

    //GUI TEXTVIEW
    private TextView tvUsername;
    private TextView tvUserPassword;

    //GUI EDITTEXT
    private EditText etUsername;
    private EditText etUserPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_login_gui);

        // Khởi tạo các thành phần giao diện
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);

        tvUsername=findViewById(R.id.tvUsername);
        tvUserPassword=findViewById(R.id.tvPassword);

        etUsername=findViewById(R.id.etUsername);
        etUserPassword=findViewById(R.id.etPassword);


        // Xử lý sự kiện khi người dùng click vào nút "Submit" or "Button"

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                tvUsername.setText("");
                if(etUserPassword.getText().toString().equals("")||etUserPassword.getText().equals(" ")) {
                    tvUserPassword.setText("Mật khẩu");
                }

            }
        });

        etUserPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                tvUserPassword.setText("");
                if(etUsername.getText().toString().equals("")||etUsername.getText().equals(" ")) {
                    tvUsername.setText("Tài khoản");
                }


            }
        });

    }
}