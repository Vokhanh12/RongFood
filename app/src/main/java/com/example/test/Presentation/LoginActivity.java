package com.example.test.Presentation;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.test.MainActivity;
import com.example.test.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    //GUI BUTTON
    private Button btnLogin;
    private Button btnRegister;

    //GUI TEXTVIEW
    private TextView tvUsername;
    private TextView tvUserPassword;

    //GUI EDITTEXT
    private EditText etUsername;
    private EditText etUserPassword;

    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_login_gui);

        //Create Firebase
       mAuth = FirebaseAuth.getInstance();


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


                String username = etUsername.getText().toString();
                String password = etUserPassword.getText().toString();

                //Kiểm tra username và password có rổng hay không
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(LoginActivity.this,"Vui long nhap lai ten dang nhap!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"Vui nhap lai mat khau",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Dang nhap thanh cong",Toast.LENGTH_SHORT).show();
                           // Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                          //  startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Dang nhap that bai",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);


                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

    }
}