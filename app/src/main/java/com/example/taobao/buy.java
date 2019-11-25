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

public class buy extends AppCompatActivity {

    private EditText get_buy_commodity_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        get_buy_commodity_number = findViewById(R.id.get_buy_number);
        Button button = (Button)findViewById(R.id.back_buy_to_commodity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = getIntent().getExtras();
                String id_user = bundle1.getString("id_user");
                String id_shop = bundle1.getString("id_shop");
                String shop_name = bundle1.getString("shop_name");
                Intent intent = new Intent(buy.this,commodity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("id_user",id_user);
                bundle2.putString("id_shop",id_shop);
                bundle2.putString("shop_name",shop_name);
                intent.putExtras(bundle2);
                startActivity(intent);
                finish();
            }
        });
        Button button1 = (Button)findViewById(R.id.buy_buy_buy);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                String commodity_name = bundle.getString("commodity_name");
                String price = bundle.getString("price");
                String number = bundle.getString("number");
                String id_user = bundle.getString("id_user");
                String id_shop = bundle.getString("id_user");
                String shop_name = bundle.getString("shop_name");
                int number_origin = Integer.parseInt(number);
                if("".equals(get_buy_commodity_number.getText().toString()))
                    Toast.makeText(buy.this,"请您输入您要购买的商品的数量！",Toast.LENGTH_SHORT).show();
                else{
                    int result;
                    String buy_number1 = get_buy_commodity_number.getText().toString();
                    int buy_number = Integer.parseInt(buy_number1);
                    if(buy_number == 0)
                        Toast.makeText(buy.this,"您不能购买0个！",Toast.LENGTH_SHORT).show();
                    else
                    {
                        result = number_origin - buy_number;
                        if(result < 0)
                            Toast.makeText(buy.this,"库存不足！",Toast.LENGTH_SHORT).show();
                        else
                        {
                            //更新购买后的数据
                            String number_in_database = Integer.toString(result);
                            MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(buy.this);
                            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

                            ContentValues values = new ContentValues();
                            values.put("number", number_in_database);//第一个"name" 是字段名字  第二个是对应字段的数据
                            database.update("commodity", values, "commodity_name=?", new String[]{commodity_name});

                            //把数据填入list表
                            database.execSQL("insert into list(id_user,shop_name,commodity_name,price,number) values('" + id_user + "','" + shop_name + "','" + commodity_name + "','" + price + "','" + buy_number1 + "');");
                            database.close();

                            //提示和跳转
                            Toast.makeText(buy.this,"购买成功！",Toast.LENGTH_SHORT).show();
                            Bundle bundle1 = getIntent().getExtras();
                            Intent intent = new Intent(buy.this,commodity.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("id_user",id_user);
                            bundle2.putString("id_shop",id_shop);
                            bundle2.putString("shop_name",shop_name);
                            intent.putExtras(bundle1);
                            startActivity(intent);
                            finish();

                        }
                    }
                }
            }
        });
    }
}
