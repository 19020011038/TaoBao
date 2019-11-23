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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class commodity extends AppCompatActivity {
    private TextView show_shop_name_in_commodity;
    private RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commodity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.hide();
        }
        show_shop_name_in_commodity = findViewById(R.id.text_show_shop_name);
        Bundle bundle = getIntent().getExtras();
        String shop_name = bundle.getString("shop_name");
        show_shop_name_in_commodity.setText(shop_name);
        Button button = (Button)findViewById(R.id.back_commodity_to_shop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = getIntent().getExtras();
                String id = bundle1.getString("id_user");
                Intent intent = new Intent(commodity.this,shop.class);
                intent.putExtra("id1",id);
                startActivity(intent);
            }
        });
        Button jump_to_add = (Button)findViewById(R.id.jump_commodity_to_add);
        jump_to_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = getIntent().getExtras();
                String id_user = bundle1.getString("id_user");
                Log.d("aaaaaa",id_user);
                String id_shop = bundle1.getString("id_shop");
                String shop_name = bundle1.getString("shop_name");

                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(commodity.this);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                Cursor cursor = database.query("shop", new String[]{"shop_name", "id_user"}, "shop_name=?", new String[]{shop_name}, null, null, null);
                if (cursor.moveToFirst()) {
                    String idx = cursor.getString(cursor.getColumnIndex("id_user"));//Ctrl+P
                    Log.d("bbbbbbb",idx);
                    if(idx.equals(id_user))
                    {
                        Intent intent = new Intent(commodity.this,add_commodity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("id_user",id_user);
                        bundle2.putString("id_shop",id_shop);
                        bundle2.putString("shop_name",shop_name);
                        intent.putExtras(bundle2);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(commodity.this,"您不可以在别人的店铺内添加商品！",Toast.LENGTH_SHORT).show();
                }
                cursor.close();//游标关闭!!!!
                database.close();
            }
        });

        //...................................................................
        Button jump_to_delete = (Button)findViewById(R.id.jump_commodity_to_delete);
        jump_to_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = getIntent().getExtras();
                String id_user = bundle1.getString("id_user");
                Log.d("aaaaaa",id_user);
                String id_shop = bundle1.getString("id_shop");
                String shop_name = bundle1.getString("shop_name");

                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(commodity.this);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                Cursor cursor = database.query("shop", new String[]{"shop_name", "id_user"}, "shop_name=?", new String[]{shop_name}, null, null, null);
                if (cursor.moveToFirst()) {
                    String idx = cursor.getString(cursor.getColumnIndex("id_user"));//Ctrl+P
                    Log.d("bbbbbbb",idx);
                    if(idx.equals(id_user))
                    {
                        Intent intent = new Intent(commodity.this,delete_commodity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("id_user",id_user);
                        bundle2.putString("id_shop",id_shop);
                        bundle2.putString("shop_name",shop_name);
                        intent.putExtras(bundle2);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(commodity.this,"您不可以在别人的店铺内删除商品！",Toast.LENGTH_SHORT).show();
                }
                cursor.close();//游标关闭!!!!
                database.close();
            }
        });
        //.................................................................................................................................
        Button button1 = (Button)findViewById(R.id.jump_commodity_to_edit);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = getIntent().getExtras();
                String id_user = bundle1.getString("id_user");
                Log.d("aaaaaa",id_user);
                String id_shop = bundle1.getString("id_shop");
                String shop_name = bundle1.getString("shop_name");

                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(commodity.this);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                Cursor cursor = database.query("shop", new String[]{"shop_name", "id_user"}, "shop_name=?", new String[]{shop_name}, null, null, null);
                if (cursor.moveToFirst()) {
                    String idx = cursor.getString(cursor.getColumnIndex("id_user"));//Ctrl+P
                    Log.d("bbbbbbb",idx);
                    if(idx.equals(id_user))
                    {
                        Intent intent = new Intent(commodity.this,edit_commodity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("id_user",id_user);
                        bundle2.putString("id_shop",id_shop);
                        bundle2.putString("shop_name",shop_name);
                        intent.putExtras(bundle2);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(commodity.this,"您不可以在别人的店铺内修改商品！",Toast.LENGTH_SHORT).show();
                }
                cursor.close();//游标关闭!!!!
                database.close();
            }
        });
//.......................................................................................................................................................
        Bundle bundle1 = getIntent().getExtras();
        String id_user = bundle1.getString("id_user");
        Log.d("aaaaaa",id_user);
        String id_shop = bundle1.getString("id_shop");


        recyclerView = findViewById(R.id.recyclerView2);


        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(commodity.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

        Cursor cursor = database.query("commodity", new String[]{"commodity_name", "id_shop"}, "id_shop=?", new String[]{id_shop}, null, null, null);
        List<Map<String, Object>> list = new ArrayList<>();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("commodity_name"));
            Map<String, Object> map = new HashMap<>();
            map.put("name",name);
            list.add(map);
        }
        cursor.close();

        Cursor cursor2 = database.query("commodity", new String[]{"price", "id_shop"}, "id_shop=?", new String[]{id_shop}, null, null, null);
        List<Map<String, Object>> list2 = new ArrayList<>();
        while (cursor2.moveToNext()){
            String price = cursor2.getString(cursor2.getColumnIndex("price"));
            Map<String, Object> map2 = new HashMap<>();
            map2.put("price",price);
            list2.add(map2);
        }
        cursor2.close();


        Cursor cursor3 = database.query("commodity", new String[]{"number", "id_shop"}, "id_shop=?", new String[]{id_shop}, null, null, null);
        List<Map<String, Object>> list3 = new ArrayList<>();
        while (cursor3.moveToNext()){
            String number = cursor3.getString(cursor3.getColumnIndex("number"));
            Map<String, Object> map3 = new HashMap<>();
            map3.put("number",number);
            list3.add(map3);
        }
        cursor3.close();

        database.close();
        recyclerView.setLayoutManager(new LinearLayoutManager(commodity.this));//垂直排列 , Ctrl+P
        recyclerView.setAdapter(new MyAdapter2(commodity.this, list,list2,list3,id_user,id_shop,shop_name));//绑定适配器

    }
}
