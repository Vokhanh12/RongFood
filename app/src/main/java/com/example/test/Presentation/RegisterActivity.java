package com.example.test.Presentation;

import android.text.Editable;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.test.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;

    private Button btnRegister;
    private RadioGroup rgNamNu;
    private RadioButton rbNam1;
    private RadioButton rbNu1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        rgNamNu=findViewById(R.id.rgNamNu);

        rbNam1=findViewById(R.id.rbNam);
        rbNu1=findViewById(R.id.rbNu);

        btnRegister = findViewById(R.id.btnRegister);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://rongfood-c701f-default-rtdb.firebaseio.com/").getReference().child("Users");



       rgNamNu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               if (checkedId == R.id.rbNam) {
                   //do something
                   rbNu1.setChecked(false);
               } else if (checkedId == R.id.rbNu) {
                   //do something
                   rbNam1.setChecked(false);
               }
           }
       });

       btnRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String username = etUsername.getText().toString();
               String password = etPassword.getText().toString();
               User user = new User(username, password);
               try {
                   mDatabase.push().setValue(user);
               } catch (Exception ex) {
                   ex.printStackTrace();
               }

               Toast.makeText(RegisterActivity.this,"Insert thanh cong",Toast.LENGTH_SHORT).show();

           }
       });


    }
}