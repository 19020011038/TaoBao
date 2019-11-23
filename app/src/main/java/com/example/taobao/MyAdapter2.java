package com.example.taobao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {

    private List<Map<String, Object>> list;
    private List<Map<String, Object>> list2;
    private List<Map<String, Object>> list3;
    private Context context;
    private String id_user;
    private String id_shop;
    private String shop_name;

    public MyAdapter2(Context context, List<Map<String, Object>> list, List<Map<String,Object>>list2 ,List<Map<String, Object>> list3,String id_user,String id_shop,String shop_name) {
        this.context = context;
        this.list = list;
        this.list2 = list2;
        this.list3 = list3;
        this.id_user = id_user;
        this.id_shop = id_shop;
        this.shop_name = shop_name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_commodity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.ViewHolder holder, final int position) {
        holder.name.setText(list.get(position).get("name").toString());
        holder.price.setText(list2.get(position).get("price").toString()+"元");
        holder.number.setText(list3.get(position).get("number").toString()+"个");

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commodity_name = list.get(position).get("name").toString();
                String commodity_price = list2.get(position).get("price").toString();
                String commodity_number = list3.get(position).get("number").toString();
                Intent intent = new Intent(MyAdapter2.this.context,buy.class);
                Bundle bundle = new Bundle();
                bundle.putString("commodity_name",commodity_name);
                bundle.putString("price",commodity_price);
                bundle.putString("number",commodity_number);
                bundle.putString("id_user",id_user);
                bundle.putString("id_shop",id_shop);
                bundle.putString("shop_name",shop_name);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView price;
        private TextView number;
        private Button button;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.commodity_name);
            price = itemView.findViewById(R.id.commodity_price);
            number = itemView.findViewById(R.id.commodity_number);

            button = itemView.findViewById(R.id.buy);

        }
    }
}
