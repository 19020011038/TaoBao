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

public class open_shop extends AppCompatActivity {
    private EditText mText_get_open_shop_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_shop);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.hide();
        }
        mText_get_open_shop_name = findViewById(R.id.get_open_shop_name);
        Button button = (Button)findViewById(R.id.back_open_shop_to_edit_shop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String id = intent.getStringExtra("id_user");
                Intent intent1 = new Intent(open_shop.this,edit_shop.class);
                intent1.putExtra("id_user",id);
                startActivity(intent1);
                finish();
            }
        });
        Button button1 = (Button)findViewById(R.id.open_shop);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(mText_get_open_shop_name.getText().toString()))
                    Toast.makeText(open_shop.this,"店铺名不能为空！",Toast.LENGTH_SHORT).show();
                else {
                    databaseInsert();
                }
            }
        });
    }
    private void databaseInsert() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id_user");

        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(open_shop.this);//实例化一个对象

        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();//打开数据库

        String shop_name = mText_get_open_shop_name.getText().toString();

        database.execSQL("insert into shop(id_user,shop_name) values('" +  id + "','" + shop_name + "');");
        database.close();

        Toast.makeText(open_shop.this, "开店铺成功！" , Toast.LENGTH_SHORT).show();
        Intent intent2 = new Intent(open_shop.this,edit_shop.class);
        intent2.putExtra("id_user",id);
        startActivity(intent2);
        finish();
    }
}
