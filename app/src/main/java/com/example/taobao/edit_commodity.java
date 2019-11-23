package com.example.taobao;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class edit_commodity extends AppCompatActivity {
    private EditText get_edit_commodity_name;
    private EditText get_edit_price;
    private EditText get_edit_number;
    private EditText get_name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_commodity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.hide();
        }
        get_edit_commodity_name = findViewById(R.id.get_edit_commodity_name);
        get_edit_price = findViewById(R.id.get_edit_price);
        get_edit_number = findViewById(R.id.get_edit_number);
        get_name = findViewById(R.id.get_name);
        Button button = (Button)findViewById(R.id.back_edit_commodity_to_commodity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = getIntent().getExtras();
                String id_user = bundle1.getString("id_user");
                String id_shop = bundle1.getString("id_shop");
                String shop_name = bundle1.getString("shop_name");
                Intent intent = new Intent(edit_commodity.this,commodity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("id_user",id_user);
                bundle2.putString("id_shop",id_shop);
                bundle2.putString("shop_name",shop_name);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });


        //修改商品名
        Button edit_commodity_name = (Button)findViewById(R.id.edit_commodity_name);
        edit_commodity_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(get_name.getText().toString()))
                    Toast.makeText(edit_commodity.this,"请您输入您所选择要修改的商品名称！",Toast.LENGTH_SHORT).show();
                else{
                    MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(edit_commodity.this);
                    SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                    String name = get_name.getText().toString();
                    Cursor cursor = database.query("commodity", new String[]{"commodity_name"}, "commodity_name=?", new String[]{name}, null, null, null);
                    if (cursor.moveToFirst()) {
                        if("".equals(get_edit_commodity_name.getText().toString()))
                        Toast.makeText(edit_commodity.this,"请您输入您要修改的商品名称！",Toast.LENGTH_SHORT).show();
                    else
                    {
                        Bundle bundle1 = getIntent().getExtras();
                        String id_user = bundle1.getString("id_user");
                        String id_shop = bundle1.getString("id_shop");
                        String shop_name = bundle1.getString("shop_name");
                        String edit_name = get_edit_commodity_name.getText().toString();
                        ContentValues values = new ContentValues();
                        values.put("commodity_name", edit_name);//第一个"name" 是字段名字  第二个是对应字段的数据
                        database.update("commodity", values, "commodity_name=?", new String[]{name});
                        Toast.makeText(edit_commodity.this,"修改商品名成功！",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(edit_commodity.this,commodity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("id_user",id_user);
                        bundle2.putString("id_shop",id_shop);
                        bundle2.putString("shop_name",shop_name);
                        intent.putExtras(bundle2);
                        startActivity(intent);
                    }
                    } else {
                        Toast.makeText(edit_commodity.this,"该商品不存在！",Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();//游标关闭!!!!
                    database.close();
                }
            }
        });


        //修改商品单价
        Button button1 = (Button)findViewById(R.id.edit_price);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(get_name.getText().toString()))
                    Toast.makeText(edit_commodity.this,"请您输入您所选择要修改的商品名称！",Toast.LENGTH_SHORT).show();
                else{
                    MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(edit_commodity.this);
                    SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                    String name = get_name.getText().toString();
                    Cursor cursor = database.query("commodity", new String[]{"commodity_name"}, "commodity_name=?", new String[]{name}, null, null, null);
                    if (cursor.moveToFirst()) {
                        if("".equals(get_edit_price.getText().toString()))
                            Toast.makeText(edit_commodity.this,"请您输入您要修改的商品单价！",Toast.LENGTH_SHORT).show();
                        else
                        {
                            Bundle bundle1 = getIntent().getExtras();
                            String id_user = bundle1.getString("id_user");
                            String id_shop = bundle1.getString("id_shop");
                            String shop_name = bundle1.getString("shop_name");
                            String price = get_edit_price.getText().toString();
                            ContentValues values = new ContentValues();
                            values.put("price", price);//第一个"name" 是字段名字  第二个是对应字段的数据
                            database.update("commodity", values, "commodity_name=?", new String[]{name});
                            Toast.makeText(edit_commodity.this,"修改商品单价成功！",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(edit_commodity.this,commodity.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("id_user",id_user);
                            bundle2.putString("id_shop",id_shop);
                            bundle2.putString("shop_name",shop_name);
                            intent.putExtras(bundle2);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(edit_commodity.this,"该商品不存在！",Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();//游标关闭!!!!
                    database.close();
                }
            }
        });

        //修改商品数量
        Button button2 = (Button)findViewById(R.id.edit_number);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(get_name.getText().toString()))
                    Toast.makeText(edit_commodity.this,"请您输入您所选择要修改的商品名称！",Toast.LENGTH_SHORT).show();
                else{
                    MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(edit_commodity.this);
                    SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                    String name = get_name.getText().toString();
                    Cursor cursor = database.query("commodity", new String[]{"commodity_name"}, "commodity_name=?", new String[]{name}, null, null, null);
                    if (cursor.moveToFirst()) {
                        if("".equals(get_edit_number.getText().toString()))
                            Toast.makeText(edit_commodity.this,"请您输入您要修改的商品数量！",Toast.LENGTH_SHORT).show();
                        else
                            if("0".equals(get_edit_number.getText().toString()))
                                Toast.makeText(edit_commodity.this,"输入商品数量不能为0！",Toast.LENGTH_SHORT).show();
                            else
                        {
                            Bundle bundle1 = getIntent().getExtras();
                            String id_user = bundle1.getString("id_user");
                            String id_shop = bundle1.getString("id_shop");
                            String shop_name = bundle1.getString("shop_name");
                            String number = get_edit_number.getText().toString();
                            ContentValues values = new ContentValues();
                            values.put("number", number);//第一个"name" 是字段名字  第二个是对应字段的数据
                            database.update("commodity", values, "commodity_name=?", new String[]{name});
                            Toast.makeText(edit_commodity.this,"修改商品数量成功！",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(edit_commodity.this,commodity.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("id_user",id_user);
                            bundle2.putString("id_shop",id_shop);
                            bundle2.putString("shop_name",shop_name);
                            intent.putExtras(bundle2);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(edit_commodity.this,"该商品不存在！",Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();//游标关闭!!!!
                    database.close();
                }
            }
        });
    }
}
