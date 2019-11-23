package com.example.taobao;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class register extends AppCompatActivity {
    private EditText mText_number;
    private EditText mText_nickname;
    private EditText mText_introduction;
    private EditText mText_password;
    private EditText mText_location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mText_number = findViewById(R.id.get_tel);
        mText_nickname = findViewById(R.id.get_nickname);
        mText_introduction = findViewById(R.id.get_introduction);
        mText_password = findViewById(R.id.get_password);
        mText_location = findViewById(R.id.get_location);

        Button button1 = (Button)findViewById(R.id.back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(register.this,login.class);
                startActivity(intent1);
            }
        });
        Button button2 = (Button)findViewById(R.id.register_sure);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(mText_number.getText().toString()))
                    Toast.makeText(register.this,"输入手机号不能为空！",Toast.LENGTH_SHORT).show();
                else
                    if("".equals(mText_nickname.getText().toString()))
                        Toast.makeText(register.this,"输入昵称不能为空！",Toast.LENGTH_SHORT).show();
                    else
                        if("".equals(mText_introduction.getText().toString()))
                            Toast.makeText(register.this,"输入简介不能为空！",Toast.LENGTH_SHORT).show();
                        else
                            if("".equals(mText_password.getText().toString()))
                                Toast.makeText(register.this,"输入密码不能为空！",Toast.LENGTH_SHORT).show();
                            else
                                if("".equals(mText_location.getText().toString()))
                                    Toast.makeText(register.this,"输入地址不能为空！",Toast.LENGTH_SHORT).show();
                                else{
                dataBaseInsert();}
            }
        });
    }
    public void dataBaseInsert (){
        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(register.this);//实例化一个对象

        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();//打开数据库

        String number = mText_number.getText().toString();

        String nickname = mText_nickname.getText().toString();

        String introduction = mText_introduction.getText().toString();

        String password = mText_password.getText().toString();

        String location = mText_location.getText().toString();

        database.execSQL("insert into user(number,nickname,introduction,password,location) values('" +  number + "','" + nickname + "','" + introduction + "','" + password + "','" + location + "');");

        database.close();

        Toast.makeText(register.this, "注册成功" , Toast.LENGTH_LONG).show();
        Intent intent2 = new Intent(register.this,login.class);
        startActivity(intent2);
    }
}
