package com.example.taobao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    public MyDataBaseHelper(@Nullable Context context) {
        //下面这个"database"是数据库的名字,version是版本号
        super(context, "database", null, 17);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //下面这句是简单的SQL语句,创建数据库的表
        String TABLE_USER = "create table user(id_user INTEGER PRIMARY KEY autoincrement,number text,nickname text,introduction text,password text,location text);";
        sqLiteDatabase.execSQL(TABLE_USER);
        String TABLE_SHOP = "create table shop(id_shop INTEGER PRIMARY KEY autoincrement,id_user text,shop_name text);";
        sqLiteDatabase.execSQL(TABLE_SHOP);
        String TABLE_COMMODITY = "create table commodity(id_commodity INTEGER PRIMARY KEY autoincrement,id_user text,id_shop text,commodity_name text,price text,number text);";
        sqLiteDatabase.execSQL(TABLE_COMMODITY);
        String TABLE_LIST = "create table list(id_list INTEGER PRIMARY KEY autoincrement,id_user text,shop_name text,commodity_name text,price text,number text);";
        sqLiteDatabase.execSQL(TABLE_LIST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //更新数据库版本时才会执行onUpgrade
        sqLiteDatabase.execSQL("drop table if exists user");
        sqLiteDatabase.execSQL("drop table if exists shop");
        sqLiteDatabase.execSQL("drop table if exists commodity");
        sqLiteDatabase.execSQL("drop table if exists list");
        onCreate(sqLiteDatabase);

    }
}
