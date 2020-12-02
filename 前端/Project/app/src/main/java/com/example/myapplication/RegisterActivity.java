package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button loginbutton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginbutton2=findViewById(R.id.login_button2);
        loginbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            //按下登录按钮跳转到检测界面
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "注册成功,自动登录", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(RegisterActivity.this,CheckActivity.class) ;
                startActivity(intent);
            }
        });
    }
}