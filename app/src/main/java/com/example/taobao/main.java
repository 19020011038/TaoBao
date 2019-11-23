package com.example.taobao;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class main extends AppCompatActivity implements View.OnClickListener {
    private TextView mText_show_nickname;
    private TextView mText_show_location;
    private TextView mText_show_introduction;
    private Button mBtn_jump_shop;
    private Button mBtn_jump_list;
    private Button mBtn_jump_edit_introduction;
    private Button mBtn_jump_login;
    private Button mBtn_change_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();
        mBtn_jump_shop.setOnClickListener(this);
        mBtn_jump_list.setOnClickListener(this);
        mBtn_jump_edit_introduction.setOnClickListener(this);
        mBtn_jump_login.setOnClickListener(this);
        mBtn_change_password.setOnClickListener(this);
        show();
    }


    private void initUI() {
        mText_show_nickname = findViewById(R.id.show_nickname);
        mText_show_location = findViewById(R.id.show_location);
        mText_show_introduction = findViewById(R.id.show_introduction);
        mBtn_jump_shop = findViewById(R.id.jump_shop);
        mBtn_jump_list = findViewById(R.id.jump_list);
        mBtn_jump_edit_introduction = findViewById(R.id.jump_edit_introduction);
        mBtn_jump_login = findViewById(R.id.jump_login);
        mBtn_change_password = findViewById(R.id.change_password);
    }

    private void show() {
        Intent intent = getIntent();
        String id_user = intent.getStringExtra("id_user");
        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(main.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

        Cursor cursor = database.query("user", new String[]{"nickname", "location","introduction","id_user"}, "id_user=?",new String[]{id_user}, null, null, null);

        if (cursor.moveToFirst()) {
            String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            String introduction = cursor.getString(cursor.getColumnIndex("introduction"));//Ctrl+P

            mText_show_nickname.setText(nickname);
            mText_show_location.setText(location);
            mText_show_introduction.setText(introduction);
        }
        else{}

        cursor.close();//游标关闭!!!!
        database.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.jump_shop:
                Intent jump1 = new Intent(main.this,shop.class);
                Intent intent1 = getIntent();
                String id1 = intent1.getStringExtra("id_user");
                Bundle bundle1 = new Bundle();
                bundle1.putString("id1",id1);
                jump1.putExtras(bundle1);
                startActivity(jump1);
                finish();
                break;

            case R.id.jump_list:
                Intent jump2 = new Intent(main.this,list.class);
                Intent intent2 = getIntent();
                String id2 = intent2.getStringExtra("id_user");
                Bundle bundle2 = new Bundle();
                bundle2.putString("id2",id2);
                jump2.putExtras(bundle2);
                startActivity(jump2);
                finish();
                break;
            case R.id.jump_edit_introduction:
                Intent jump3 = new Intent(main.this,edit_introduction.class);
                Intent intent3 = getIntent();
                String id3 = intent3.getStringExtra("id_user");

                Bundle bundle3 = new Bundle();
                bundle3.putString("id3",id3);
                jump3.putExtras(bundle3);
                startActivity(jump3);
                finish();
                break;
            case R.id.jump_login:
                Intent jump4 = new Intent(main.this,login.class);
                startActivity(jump4);
                finish();
                break;
            case R.id.change_password:
                Intent intent5 = getIntent();
                String id5 = intent5.getStringExtra("id_user");
                Intent jump5 = new Intent(main.this,change_password.class);
                jump5.putExtra("id5",id5);
                startActivity(jump5);
                finish();
                break;


        }
    }
}
