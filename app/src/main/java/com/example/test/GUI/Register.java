package com.example.test.GUI;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.test.R;

public class Register extends AppCompatActivity {

    private RadioGroup rgNamNu;
    private RadioButton rbNam1;
    private RadioButton rbNu1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        rgNamNu=findViewById(R.id.rgNamNu);

        rbNam1=findViewById(R.id.rbNam);
        rbNu1=findViewById(R.id.rbNu);




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



    }
}