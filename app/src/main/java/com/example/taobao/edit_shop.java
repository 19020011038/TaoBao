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

public class edit_shop extends AppCompatActivity {
    private EditText mText_delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_shop);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.hide();
        }
        mText_delete = findViewById(R.id.get_delete_shop_name);

        Button button2 = (Button)findViewById(R.id.delete_shop);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String delete_shop_name = mText_delete.getText().toString();

                Intent intent = getIntent();
                String id = intent.getStringExtra("id_user");
                Log.d("qqqqqqqqqq",id);
                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(edit_shop.this);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

                Cursor cursor = database.query("shop", new String[]{"shop_name", "id_user"}, "shop_name=?", new String[]{delete_shop_name}, null, null, null);
                if (cursor.moveToFirst()) {
                    String idx = cursor.getString(cursor.getColumnIndex("id_user"));//Ctrl+P
                    Log.d("zzzzzzzzzzz",idx);
                    if(id.equals(idx)){
                        database.delete("shop", "shop_name=?", new String[]{delete_shop_name});
                        Toast.makeText(edit_shop.this,"成功删除店铺！",Toast.LENGTH_SHORT).show();
                        Intent intent6 = new Intent(edit_shop.this,shop.class);
                        intent6.putExtra("id1",id);
                        startActivity(intent6);
                        finish();
                    }
                    else
                        Toast.makeText(edit_shop.this,"您只能删除自己的店铺！",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(edit_shop.this,"没有找到相关店铺！",Toast.LENGTH_SHORT).show();
                }

                cursor.close();//游标关闭!!!!
                database.close();
            }
        });
        Button button = (Button)findViewById(R.id.back_edit_shop_to_shop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String id = intent.getStringExtra("id_user");
                Intent intent1 = new Intent(edit_shop.this,shop.class);
                intent1.putExtra("id1",id);
                startActivity(intent1);
                finish();
            }
        });
        Button button1 = (Button)findViewById(R.id.jump_edit_shop_to_open_shop);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String id = intent.getStringExtra("id_user");
                Intent intent1 = new Intent(edit_shop.this,open_shop.class);
                intent1.putExtra("id_user",id);
                startActivity(intent1);
                finish();
            }
        });
    }
}