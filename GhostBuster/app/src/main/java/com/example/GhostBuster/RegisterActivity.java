package com.example.GhostBuster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity
{

    private Button loginbutton;
    private Button backbutton;
    private EditText password_editText;
    private EditText telnum_editText;
    private EditText confpassword_editText;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginbutton=findViewById(R.id.register_button);
        backbutton=(Button) findViewById(R.id.register_backbutton);
        confpassword_editText=(EditText)findViewById(R.id.register_et3);
        password_editText=(EditText)findViewById(R.id.register_et2);
        telnum_editText=(EditText)findViewById(R.id.register_et1);
        backbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        loginbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            //按下登录按钮跳转到检测界面
            public void onClick(View v)
            {
                Intent intent=new Intent(RegisterActivity.this,check.class);
                String get_confpassword=confpassword_editText.getText().toString();
                String get_password=password_editText.getText().toString();
                String get_phonenum=telnum_editText.getText().toString();
                if(SQLService.SignUp(get_phonenum,get_password) && get_confpassword.equals(get_password))
                {
                    Toast.makeText(RegisterActivity.this, "注册成功,自动登录", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else if(get_phonenum.length()!=11)
                {
                    Toast.makeText(RegisterActivity.this, "手机号码必须为11位", Toast.LENGTH_SHORT).show();
                }
                else if(!get_confpassword.equals(get_password))
                {
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "注册不成功", Toast.LENGTH_SHORT).show();
                }
//                int hh=0;
//                AllMacAdress check_user=new AllMacAdress();
//                int user_num=check_user.user_num;
//                check_user.user_dbs[user_num].user_id=get_password;
//                check_user.user_dbs[user_num].user_password=get_phonenum;

            }
        });
    }
}