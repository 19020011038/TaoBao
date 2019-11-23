package com.example.taobao;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<Map<String, Object>> list;
    private List<Map<String, Object>> list2;
    private Context context;
    private String id1;

    public MyAdapter(Context context, List<Map<String, Object>> list,List<Map<String, Object>> list2,String id1) {
        this.context = context;
        this.list = list;
        this.list2 = list2;
        this.id1 = id1;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(list.get(position).get("name").toString());
        holder.textView2.setText(list2.get(position).get("nickname").toString());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String shop_name = list.get(position).get("name").toString();

                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(MyAdapter.this.context);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

                Cursor cursor = database.query("shop", new String[]{"id_shop","shop_name"}, "shop_name=?", new String[]{shop_name}, null, null, null);

                if (cursor.moveToFirst()) {
                    String id_shop = cursor.getString(cursor.getColumnIndex("id_shop"));//Ctrl+P

                    Intent intent = new Intent(MyAdapter.this.context,commodity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id_user",id1);
                    bundle.putString("id_shop",id_shop);
                    bundle.putString("shop_name",shop_name);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
                cursor.close();//游标关闭!!!!
                database.close();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private Button button;
        private TextView textView2;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text);
            textView2 = itemView.findViewById(R.id.text2);
            button = itemView.findViewById(R.id.btn);
        }
    }
}
