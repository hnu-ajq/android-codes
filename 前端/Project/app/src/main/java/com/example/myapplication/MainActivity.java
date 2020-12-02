package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button loginbutton1;
    private Button registerbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbutton1=findViewById(R.id.login_button1);
        registerbutton=findViewById(R.id.register_button);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            //按下注册按钮跳转到注册界面
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "欢迎注册", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class) ;
                startActivity(intent);
            }
        });
        loginbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            //按下登录按钮跳转到检测界面
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,CheckActivity.class) ;
                startActivity(intent);
            }
        });

    }
}