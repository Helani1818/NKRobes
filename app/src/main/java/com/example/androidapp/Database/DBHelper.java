package com.example.androidapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper
{

    public DBHelper(Context context) {
        super(context, "nkrobes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table customer(username TEXT primary key, name TEXT, email TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists customer");
    }

    public  Boolean insertUser(String username, String name, String email, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("password",password);
        long result = db.insert("customer", null,contentValues);
        if ((result==-1))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public  Boolean updateUser(String username, String name, String email, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("password",password);
        Cursor cursor = db.rawQuery("Select * from customer where username =  ?", new String[] {username});
        if(cursor.getCount()>0)
        {
            long result = db.update("customer",contentValues, "username=?", new String[] {username});
            if ((result==-1))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }
    public  Boolean deleteUser(String username, String name, String email, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from customer where username =  ?", new String[] {username});
        if(cursor.getCount()>0)
        {
            long result = db.delete("customer", "username=?", new String[] {username});
            if ((result==-1))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    public  Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from customer ", null);
       return cursor;
    }

    public  Boolean checkUsername(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from customer where username = ?", new String[] {username});
        if(cursor.getCount()>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public  Boolean checkUsernameAndPassword(String username, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from customer where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}