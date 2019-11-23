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

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.ViewHolder> {

    private List<Map<String, Object>> list;
    private List<Map<String, Object>> list2;
    private List<Map<String, Object>> list3;
    private Context context;
    private String address;



    public MyAdapter3(Context context, List<Map<String, Object>> list, List<Map<String,Object>>list2 ,List<Map<String, Object>> list3,String address) {
        this.context = context;
        this.list = list;
        this.list2 = list2;
        this.list3 = list3;
        this.address = address;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter3.ViewHolder holder, final int position) {
        holder.name.setText(list.get(position).get("name").toString());
        holder.price.setText(list2.get(position).get("price").toString()+"元");
        holder.number.setText(list3.get(position).get("number").toString()+"个");
        holder.address.setText("收货地址："+address);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView price;
        private TextView number;
        private  TextView address;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.show_commodity_name);
            price = itemView.findViewById(R.id.show_price);
            number = itemView.findViewById(R.id.show_number);
            address = itemView.findViewById(R.id.show_address);

        }
    }
}
