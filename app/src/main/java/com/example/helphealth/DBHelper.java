package com.example.helphealth;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// polymorphism check 8)
public class DBHelper extends SQLiteOpenHelper{

    public static final String DBNAME = "Login.db";

    public DBHelper(@Nullable Context context) {
        super(context, "Login.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT, fullname TEXT, gender TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        onCreate(MyDB);
    }
    public boolean insertData(String username, String password, String fullname, String gender){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("fullname", fullname);
        contentValues.put("gender", gender);
        long result = MyDB.insert("users", null, contentValues);
        if(result == -1) return false;
        else
            return true;
    }
    public boolean checkUsername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username = ?", new String[] {username});
        return cursor.getCount() > 0;
    }

    public boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[] {username, password});
        return cursor.getCount() > 0;
    }

    @SuppressLint("Range")
    public String getFullname(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String fullname = "";
        Cursor cursor = MyDB.rawQuery("SELECT fullname FROM users WHERE username=?", new String[]{username});

        if (cursor.moveToFirst()) {
            fullname = cursor.getString(cursor.getColumnIndex("fullname"));
        }

        cursor.close();
        return fullname;
    }

    @SuppressLint("Range")
    public String getGender(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String gender = "";
        Cursor cursor = MyDB.rawQuery("SELECT gender FROM users WHERE username=?", new String[]{username});

        if (cursor.moveToFirst()) {
            gender = cursor.getString(cursor.getColumnIndex("gender"));
        }

        cursor.close();
        return gender;
    }

    @SuppressLint("Range")
    public String getEmail(String username){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String usern = "";
        Cursor cursor = MyDB.rawQuery("SELECT username FROM users WHERE username=?", new String[]{username});

        if (cursor.moveToFirst()){
            usern = cursor.getString(cursor.getColumnIndex("username"));
        }
        cursor.close();
        return usern;
    }
    @SuppressLint("Range")
    public UserDetails getUserDetails(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        UserDetails userDetails = new UserDetails();
        Cursor cursor = MyDB.rawQuery("SELECT fullname, username, gender FROM users WHERE username=?", new String[]{username});

        if (cursor.moveToFirst()) {
            userDetails.setFullname(cursor.getString(cursor.getColumnIndex("fullname")));
            userDetails.setEmail(cursor.getString(cursor.getColumnIndex("username")));
            userDetails.setGender(cursor.getString(cursor.getColumnIndex("gender")));
        }

        cursor.close();
        return userDetails;
    }
}