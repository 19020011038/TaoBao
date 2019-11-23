package com.example.taobao;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity implements OnClickListener {

    private Button mBtn_login;
    private Button mBtn_register;

    private EditText mText_tel;
    private EditText mText_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.hide();
        }

        initUI();

        mBtn_login.setOnClickListener(this);//Alt+Enter
        mBtn_register.setOnClickListener(this);

    }

    private void initUI() {
        mBtn_login = findViewById(R.id.login);
        mBtn_register = findViewById(R.id.register);
        mText_tel = findViewById(R.id.tel);
        mText_password = findViewById(R.id.password);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                dataBaseQuery();
                break;
            case R.id.register:
                Intent intent = new Intent(login.this,register.class);
                startActivity(intent);
        }
    }


    private void dataBaseQuery() {
        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(login.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
        String tel = mText_tel.getText().toString();
        String pass = mText_password.getText().toString();
        Cursor cursor = database.query("user", new String[]{"number","id_user","password"}, "number=?", new String[]{tel}, null, null, null);
        if(cursor.moveToFirst()){
            String get_password = cursor.getString(cursor.getColumnIndex("password"));//Ctrl+P
            String id = cursor.getString(cursor.getColumnIndex("id_user"));//Ctrl+P
            if(get_password.equals(pass))
            {
                Intent intent = new Intent(login.this,main.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_user",id);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(login.this,"登录成功！ 欢迎您~  " + tel,Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(login.this,"您输入的密码有误！",Toast.LENGTH_SHORT).show();

        }else
            Toast.makeText(login.this,"请检查您输入的手机号是否正确！",Toast.LENGTH_SHORT).show();
        }
}