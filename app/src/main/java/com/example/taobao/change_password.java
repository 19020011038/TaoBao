package com.example.taobao;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class change_password extends AppCompatActivity {
    private EditText mText_change_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.hide();
        }
        mText_change_password = findViewById(R.id.get_change_password);
        Button button = (Button)findViewById(R.id.back_change_password_to_main);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String id = intent.getStringExtra("id5");
                Intent intent1 = new Intent(change_password.this,main.class);
                intent1.putExtra("id_user",id);
                startActivity(intent1);
                finish();

            }
        });
        Button button1 = (Button)findViewById(R.id.change);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String change_password = mText_change_password.getText().toString();

                Intent intent = getIntent();
                String id = intent.getStringExtra("id5");

                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(change_password.this);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

                if("".equals(change_password))
                    Toast.makeText(change_password.this,"输入密码不能为空！",Toast.LENGTH_SHORT).show();
                else {
                    ContentValues values = new ContentValues();
                    values.put("password", change_password);//第一个"name" 是字段名字  第二个是对应字段的数据
                    database.update("user", values, "id_user=?", new String[]{id});
                    Toast.makeText(change_password.this,"修改成功,请您重新登录！",Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(com.example.taobao.change_password.this,login.class);
                    startActivity(intent2);
                    finish();
                }
                database.close();
            }
        });
    }
}
