package com.example.taobao;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class add_commodity extends AppCompatActivity {
    private TextView show;
    private EditText name;
    private EditText price;
    private EditText number;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_commodity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.hide();
        }
        name = findViewById(R.id.get_commodity_name);
        price = findViewById(R.id.get_commodity_price);
        number = findViewById(R.id.get_commodity_number);
        show = findViewById(R.id.text_show_shop_name_in_add);
        Bundle bundle = getIntent().getExtras();
        String shop_name = bundle.getString("shop_name");
        show.setText(shop_name);

        Button button = (Button)findViewById(R.id.back_add_commodity_to_commodity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = getIntent().getExtras();
                String id_user = bundle1.getString("id_user");
                String id_shop = bundle1.getString("id_shop");
                String shop_name = bundle1.getString("shop_name");
                Intent intent = new Intent(add_commodity.this,commodity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("id_user",id_user);
                bundle2.putString("id_shop",id_shop);
                bundle2.putString("shop_name",shop_name);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });


        Button button1 = (Button)findViewById(R.id.add_commodity);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = getIntent().getExtras();
                String id_user = bundle1.getString("id_user");
                String id_shop = bundle1.getString("id_shop");

                if("".equals(name.getText().toString()))
                    Toast.makeText(add_commodity.this,"商品名不能为空！",Toast.LENGTH_SHORT).show();
                else
                    if("".equals(price.getText().toString()))
                        Toast.makeText(add_commodity.this,"商品单价不能为空！",Toast.LENGTH_SHORT).show();
                    else
                        if("".equals(number.getText().toString()))
                            Toast.makeText(add_commodity.this,"商品数量不能为空！",Toast.LENGTH_SHORT).show();
                        else
                            if("0".equals(number.getText().toString()))
                                Toast.makeText(add_commodity.this,"商品数量不能为0！",Toast.LENGTH_SHORT).show();
                        else
                            databaseInsert();
            }
        });

    }
    private void databaseInsert(){
        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(add_commodity.this);//实例化一个对象
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();//打开数据库

        Bundle bundle1 = getIntent().getExtras();
        String id_user = bundle1.getString("id_user");
        String id_shop = bundle1.getString("id_shop");
        String shop_name = bundle1.getString("shop_name");
        String commodity_name = name.getText().toString();
        String commodity_price = price.getText().toString();
        String commodity_number = number.getText().toString();

        database.execSQL("insert into commodity(id_user,id_shop,commodity_name,price,number) values('" + id_user + "','" + id_shop + "','" + commodity_name + "','" + commodity_price + "','" + commodity_number + "');");

        database.close();

        Toast.makeText(add_commodity.this, "添加商品成功！" , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(add_commodity.this,commodity.class);
        Bundle bundle2 = new Bundle();
        bundle2.putString("id_user",id_user);
        bundle2.putString("id_shop",id_shop);
        bundle2.putString("shop_name",shop_name);
        intent.putExtras(bundle2);
        startActivity(intent);

    }
}
