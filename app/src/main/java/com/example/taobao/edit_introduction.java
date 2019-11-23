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

public class edit_introduction extends AppCompatActivity implements View.OnClickListener{

    private Button mBtn_edit_nickname;
    private Button mBtn_edit_intro;
    private Button mBtn_edit_location;

    private EditText mText_nickname;
    private EditText mText_intro;
    private EditText mText_location;

    private Button mBtn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_introduction);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.hide();
        }
        initUI();
        mBtn_edit_nickname.setOnClickListener(this);
        mBtn_edit_intro.setOnClickListener(this);
        mBtn_edit_location.setOnClickListener(this);
        mBtn_back.setOnClickListener(this);

    }

    private void initUI() {
        mBtn_edit_nickname = findViewById(R.id.edit_nickname);
        mBtn_edit_intro = findViewById(R.id.edit_intro);
        mBtn_edit_location = findViewById(R.id.edit_location);
        mText_nickname = findViewById(R.id.get_edit_nickname);
        mText_intro = findViewById(R.id.get_edit_intro);
        mText_location = findViewById(R.id.get_edit_location);
        mBtn_back = findViewById(R.id.back_edit_introduction_to_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_nickname:
                dataBaseUpdate_nickname();
                break;
            case R.id.edit_intro:
                dataBaseUpdate_intro();
                break;
            case R.id.edit_location:
                dataBaseUpdate_location();
                break;
            case R.id.back_edit_introduction_to_main:
                Intent intent4 = getIntent();
                String id = intent4.getStringExtra("id3");
                Intent intent = new Intent(edit_introduction.this,main.class);
                intent.putExtra("id_user",id);
                startActivity(intent);
                finish();
                break;
    }
}

    private void dataBaseUpdate_location() {
        Intent intent3 = getIntent();
        String id3 = intent3.getStringExtra("id3");

        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(edit_introduction.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

        String location = mText_location.getText().toString();
        if("".equals(location))
            Toast.makeText(edit_introduction.this,"输入地址不能为空！",Toast.LENGTH_SHORT).show();
        else {
            ContentValues values = new ContentValues();
            values.put("location", location);//第一个"name" 是字段名字  第二个是对应字段的数据
            database.update("user", values, "id_user=?", new String[]{id3});
            Toast.makeText(edit_introduction.this,"修改地址成功",Toast.LENGTH_SHORT).show();
        }
        database.close();
    }

    private void dataBaseUpdate_intro() {
        Intent intent2 = getIntent();
        String id2 = intent2.getStringExtra("id3");

        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(edit_introduction.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

        String intro = mText_intro.getText().toString();
        if("".equals(intro))
            Toast.makeText(edit_introduction.this,"输入简介不能为空！",Toast.LENGTH_SHORT).show();
        else {
            ContentValues values = new ContentValues();
            values.put("introduction", intro);//第一个"name" 是字段名字  第二个是对应字段的数据
            database.update("user", values, "id_user=?", new String[]{id2});
            Toast.makeText(edit_introduction.this,"修改简介成功",Toast.LENGTH_SHORT).show();
        }
        database.close();
    }

    private void dataBaseUpdate_nickname() {
        Intent intent1 = getIntent();
        String id1 = intent1.getStringExtra("id3");

        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(edit_introduction.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

        String nickname = mText_nickname.getText().toString();
        if ("".equals(nickname))
            Toast.makeText(edit_introduction.this, "输入昵称不能为空！", Toast.LENGTH_SHORT).show();
        else {
            ContentValues values = new ContentValues();
            values.put("nickname", nickname);//第一个"name" 是字段名字  第二个是对应字段的数据
            database.update("user", values, "id_user=?", new String[]{id1});
            Toast.makeText(edit_introduction.this, "修改昵称成功", Toast.LENGTH_SHORT).show();
        }
        database.close();
    }
    }