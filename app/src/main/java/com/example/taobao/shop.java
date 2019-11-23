package com.example.taobao;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class shop extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.hide();
        }
        Intent intent3 = getIntent();
        String id1 = intent3.getStringExtra("id1");



        Button button = (Button)findViewById(R.id.back_shop_to_main);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = getIntent();
                String id1 = intent3.getStringExtra("id1");
                Intent intent = new Intent(shop.this,main.class);
                intent.putExtra("id_user",id1);
                startActivity(intent);
                finish();
            }
        });

        Button button1 = (Button)findViewById(R.id.jump_shop_to_edit_shop);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = getIntent();
                String id1 = intent3.getStringExtra("id1");
                Intent intent1 = new Intent(shop.this,edit_shop.class);
                intent1.putExtra("id_user",id1);
                startActivity(intent1);
                finish();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> list2 = new ArrayList<>();

        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(shop.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

        Cursor cursor = database.query("shop", new String[]{"shop_name"}, null, null, null, null, null);

        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("shop_name"));
            Cursor cursor2 = database.query("shop", new String[]{"shop_name", "id_user"}, "shop_name=?", new String[]{name}, null, null, null);
            if (cursor2.moveToFirst()) {
                String id_user = cursor2.getString(cursor2.getColumnIndex("id_user"));//Ctrl+P
                Cursor cursor3 = database.query("user", new String[]{"id_user", "nickname"}, "id_user=?", new String[]{id_user}, null, null, null);
                if (cursor3.moveToFirst()) {
                    String owner_name = cursor3.getString(cursor3.getColumnIndex("nickname"));//Ctrl+P
                    Map<String, Object> map3 = new HashMap<>();
                    map3.put("nickname",owner_name);
                    list2.add(map3);
                }
                cursor3.close();//游标关闭!!!!
            }
            cursor2.close();//游标关闭!!!!
            Map<String, Object> map = new HashMap<>();
            map.put("name",name);
            list.add(map);
        }
        cursor.close();
        database.close();
        recyclerView.setLayoutManager(new LinearLayoutManager(shop.this));//垂直排列 , Ctrl+P
        recyclerView.setAdapter(new MyAdapter(shop.this, list,list2,id1));//绑定适配器

    }
}
