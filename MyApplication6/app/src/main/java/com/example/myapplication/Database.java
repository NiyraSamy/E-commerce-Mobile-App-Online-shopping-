package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    final static String DB_Name = "Mydatabase";
    SQLiteDatabase database;

    public Database(@Nullable Context context) {
        super(context, DB_Name, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  customer (id integer primary key  autoincrement , name text not null, email text not null," +
                "password text not null, gender text not null, birthdate text , jop text )");

        db.execSQL("create table category (id integer primary key  autoincrement , name text not null )");

        db.execSQL("create table product (id integer primary key autoincrement, name text not null ,image blob ," +
                "price real not null , quantity integer not null , cate_id integer not null ," +
                "foreign key (cate_id)references category (id))");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists customer");
        db.execSQL("drop table if exists category");
        db.execSQL("drop table if exists product");
        onCreate(db);
    }
    public void AddProduct(Product_Class p){
        database=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name", p.getProName());
        values.put("image", p.getProImage());
        values.put("price", p.getPrice());
        values.put("quantity", p.getPro_quantity());
        values.put("cate_id", p.getCatId());

        database.insert("product",null,values);
        database.close();
    }
    public Cursor FetchtProducts(){
        database=getReadableDatabase();
        String[]columns={"id","name","image","price","quantity","cate_id"};
        Cursor cursor= database.query("product",columns,null,null,null,null,null);

        if (cursor!=null)
            cursor.moveToFirst();

        database.close();
        return cursor;
    }

    public Cursor get(String name ){
        database=getReadableDatabase();
        String[]args={name};
        Cursor cursor=database.rawQuery("select * from product where name =?",args);
        cursor.moveToFirst();
        database.close();
        return cursor;
    }

    public Cursor getid(int id ){
        database=getReadableDatabase();
        String[]args={String.valueOf(id)};
        Cursor cursor=database.rawQuery("select * from product where id =?",args);
        cursor.moveToFirst();
        database.close();
        return cursor;
    }
    public void AddCustomer(Customer_Class c)
    {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", c.getCustName());
        values.put("email", c.getMail());
        values.put("password", c.getPassword());
        values.put("birthdate", c.getCustBirthDate());
        values.put("gender", c.getGender());
        values.put("jop", c.getCustJop());

        database.insert("customer", null, values);
        database.close();
    }
    public Cursor Login(String email, String password) {
        database = getReadableDatabase();
        String[] args = {email, password};
        Cursor cursor = database.rawQuery("select id from customer where email =? and  password =? ", args);
        if (cursor != null)
            cursor.moveToFirst();

        database.close();
        return cursor;
    }

}
