package com.example.GhostBuster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private Button login_button;
    private Button register_button;
    private EditText password_editText;
    private EditText telnum_editText;
    private String m_password;
    private String m_telnum;
    //private SQL sql;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_button=(Button) findViewById(R.id.main_button1);
        register_button=(Button) findViewById(R.id.main_button2);
        password_editText=(EditText)findViewById(R.id.main_et2);
        telnum_editText=(EditText)findViewById(R.id.main_et1);
//        sendcode_button.setOnClickListener
//        (
//            new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String ss1=telnum_editText.getText().toString();
//                        Checking m_s=new Checking();//创建发送验证码对象
//                        m_s.Sendcode(ss1);//发送验证码
//                        m_yzcode = m_s.m_Fcode;//储存发送的验证码
//                    }
//                }).start();
//           }
//           }
//        );
        login_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,check.class);
                String get_password=password_editText.getText().toString();
                String get_phonenum=telnum_editText.getText().toString();
                //m_password = "1111";
                //m_telnum="18653262976";
                m_password = SQLService.SignIn(get_phonenum);
                //sql=new SQL();
                //m_password=sql.testSQL("18653262976");
                //if(hh==1) startActivity(intent);
                if(get_password.equals(m_password))
                {
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "用户不存在或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        register_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}