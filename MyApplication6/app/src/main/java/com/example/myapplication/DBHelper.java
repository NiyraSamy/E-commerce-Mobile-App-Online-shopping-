package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper( Context context) {
        super(context, "Cartdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table cart( id integer primary key  autoincrement , name text not null )");
        db.execSQL("create Table QR( id integer primary key  autoincrement , name text not null , qr text not null )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists cart");
        db.execSQL("drop table if exists QR");
        onCreate(db);
    }

    public boolean insert (String name ) {
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues contentValues = new ContentValues() ;
        contentValues.put("name" , name );
        long result = db.insert("cart" , null , contentValues ) ;
        if( result==-1 ){ return false ; }
        else { return true ; }
    }
    public boolean insert_qr ( String name , String qr ) {
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues contentValues = new ContentValues() ;
        contentValues.put("name" , name );
        contentValues.put("qr" , qr );
        long result = db.insert("QR" , null , contentValues ) ;
        if( result==-1 ){ return false ; }
        else { return true ; }
    }
    public Cursor getdata () {
        SQLiteDatabase db = this.getReadableDatabase() ;
        Cursor cursor = db.rawQuery("select * from cart" , null ) ;
        if ( cursor != null ) { cursor.moveToFirst() ;}
        db.close();
        return cursor ;
    }
    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase() ;
        db.execSQL("drop table if exists cart");
        onCreate(db);
    }
    public Cursor fetch(int id ){
        SQLiteDatabase db = this.getReadableDatabase() ;
        String[]args={String.valueOf(id)};
        Cursor cursor = db.rawQuery("select * from cart where id =?",args);
        cursor.moveToFirst();
        db.close();
        return cursor;
    }
    public Cursor get_qr(String qr ) {
        SQLiteDatabase db = this.getWritableDatabase() ;
        String[]args={qr};
        Cursor cursor = db.rawQuery("select * from QR WHERE qr =?",args);
        cursor.moveToFirst();
        db.close();
        return cursor;
    }


}
