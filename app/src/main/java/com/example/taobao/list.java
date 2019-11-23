package com.example.taobao;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class list extends AppCompatActivity {
    private RecyclerView recyclerView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.hide();
        }
        Bundle bundle = getIntent().getExtras();
        String id_user = bundle.getString("id2");
        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(list.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

        Cursor cursor4 = database.query("user", new String[]{"location", "id_user"}, "id_user=?", new String[]{id_user}, null, null, null);

        if (cursor4.moveToFirst()) {
            String address = cursor4.getString(cursor4.getColumnIndex("location"));//Ctrl+P
            recyclerView3 = findViewById(R.id.recyclerView3);

            Cursor cursor = database.query("list", new String[]{"commodity_name", "id_user"}, "id_user=?", new String[]{id_user}, null, null, null);
            List<Map<String, Object>> list = new ArrayList<>();
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("commodity_name"));
                Map<String, Object> map = new HashMap<>();
                map.put("name",name);
                list.add(map);
            }
            cursor.close();

            Cursor cursor2 = database.query("list", new String[]{"price", "id_user"}, "id_user=?", new String[]{id_user}, null, null, null);
            List<Map<String, Object>> list2 = new ArrayList<>();
            while (cursor2.moveToNext()){
                String price = cursor2.getString(cursor2.getColumnIndex("price"));
                Map<String, Object> map2 = new HashMap<>();
                map2.put("price",price);
                list2.add(map2);
            }
            cursor2.close();


            Cursor cursor3 = database.query("list", new String[]{"number", "id_user"}, "id_user=?", new String[]{id_user}, null, null, null);
            List<Map<String, Object>> list3 = new ArrayList<>();
            while (cursor3.moveToNext()){
                String number = cursor3.getString(cursor3.getColumnIndex("number"));
                Map<String, Object> map3 = new HashMap<>();
                map3.put("number",number);
                list3.add(map3);
            }
            cursor3.close();

            database.close();
            recyclerView3.setLayoutManager(new LinearLayoutManager(list.this));//垂直排列 , Ctrl+P
            recyclerView3.setAdapter(new MyAdapter3(list.this, list,list2,list3,address));//绑定适配器


        }
        cursor4.close();//游标关闭!!!!

        Button button = (Button)findViewById(R.id.back_list_to_main);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                String id_user = bundle.getString("id2");
                Intent intent = new Intent(list.this,main.class);
                intent.putExtra("id_user",id_user);
                startActivity(intent);
                finish();
            }
        });


    }
}