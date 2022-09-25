package com.example.myapplication;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper  extends SQLiteOpenHelper {
    public DbHelper(Context context){
        super(context,"User",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="Create table User(Id text primary key,Username text,Phone text)";
        db.execSQL(sql);
        sql ="Insert Into User Values('001','hai','0123123123')";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    db.execSQL("Drop Table if exists User");
    onCreate(db);
    }
}
