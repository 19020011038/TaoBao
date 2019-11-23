package com.example.taobao;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class delete_commodity extends AppCompatActivity {
    private EditText get_delete_commodity_name;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_commodity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.hide();
        }
        get_delete_commodity_name = findViewById(R.id.get_delete_commodity);
        Button button = (Button)findViewById(R.id.back_delete_commodity_to_commodity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                String id_user = bundle.getString("id_user");
                String id_shop = bundle.getString("id_shop");
                String shop_name = bundle.getString("shop_name");
                Intent intent = new Intent(delete_commodity.this,commodity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("id_user",id_user);
                bundle1.putString("id_shop",id_shop);
                bundle1.putString("shop_name",shop_name);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        Button button1 = (Button)findViewById(R.id.delete_commodity);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(get_delete_commodity_name.getText().toString()))
                    Toast.makeText(delete_commodity.this,"请您输入要删除的商品名！",Toast.LENGTH_SHORT).show();
                else
                {
                    Bundle bundle = getIntent().getExtras();
                    String id_user = bundle.getString("id_user");
                    String id_shop = bundle.getString("id_shop");
                    String shop_name = bundle.getString("shop_name");
                    String delete_name = get_delete_commodity_name.getText().toString();
                    MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(delete_commodity.this);
                    SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

                    Cursor cursor = database.query("commodity", new String[]{"commodity_name"}, "commodity_name=?", new String[]{delete_name}, null, null, null);
                    if (cursor.moveToFirst()) {
                        database.delete("commodity", "commodity_name=?", new String[]{delete_name});
                        Toast.makeText(delete_commodity.this,"删除商品成功！",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(delete_commodity.this,commodity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("id_user",id_user);
                        bundle1.putString("id_shop",id_shop);
                        bundle1.putString("shop_name",shop_name);
                        intent.putExtras(bundle1);
                        startActivity(intent);

                    } else {
                        Toast.makeText(delete_commodity.this,"没有找到相关商品！",Toast.LENGTH_SHORT).show();
                    }

                    cursor.close();//游标关闭!!!!
                    database.close();
                }
            }
        });
    }
}
