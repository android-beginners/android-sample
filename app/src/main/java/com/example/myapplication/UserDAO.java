package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UserDAO {
    public static ArrayList<User> getALL(Context context){
        ArrayList<User> ds= new ArrayList<>();
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cs = db.rawQuery("Select * from User",null);
//        cs.moveToFirst();
        while (cs.moveToNext()){
            String id = cs.getString(0);
            String name = cs.getString(1);
            String phone = cs.getString(2);
            User u = new User(id,name,phone);
            ds.add(u);
        }
        cs.close();
        db.close();
        return ds;
    }

    public static boolean insert(Context context,String id,String name,String phone){
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Id", id);
        values.put("Username", name);
        values.put("Phone", phone);;
        long row = db.insert("User",null,values);
        return (row>0);
    }
    public static boolean upadte(Context context,User u){
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Id", u.getId());
        values.put("Username", u.getName());
        values.put("Phone", u.getPhone());;
        int row = db.update("User",values,"Id=?",new String[]{u.getId()});
        return (row>0);
    }

    public static boolean delete(Context context,String id){
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        int row = db.delete("User","Id=?",new String[]{id});
        return (row>0);
    }
}
