package com.example.androidapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.androidapp.ViewHolder.DesignerOrderModelRecord;
import com.example.androidapp.ViewHolder.ProductModelRecord;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper
{
    public static final String USER_COLUMN_NAME = "name";
    public static final String USER_COLUMN_EMAIL = "email";

    public static final String TABLE_2 = "productLine";
    public static final String PRODUCT_COL1 = "productLineId";
    public static final String PRODUCT_COL2 = "productLineImage";
    public static final String PRODUCT_COL3 = "productLineName";

    public static final String TABLE_3 = "Profile";
    public static final String PROFILE_COL1 = "profileId";
    public static final String PROFILE_COL2 = "profileName";
    public static final String PROFILE_COL3 = "profileEmail";
    public static final String PROFILE_COL4 = "profileAddress";
    public static final String PROFILE_COL5 = "profileTelephone";

    public static final String TABLE_4 = "DesignerOrders";
    public static final String D_ORDERS_COL1 = "dOrderId";
    public static final String D_ORDERS_COL2 = "dOrderName";
    public static final String D_ORDERS_COL3 = "dOrderAddress";
    public static final String D_ORDERS_COL4 = "dOrderTelNumber";
    public static final String D_ORDERS_COL5 = "dOrderHeight";
    public static final String D_ORDERS_COL6 = "dOrderWaist";
    public static final String D_ORDERS_COL7 = "dOrderBust";
    public static final String D_ORDERS_COL8 = "dOrderNeck";
    public static final String D_ORDERS_COL9 = "dOrderHip";
    public static final String D_ORDERS_COL10 = "dOrderThigh";
    public static final String D_ORDERS_COL11 = "dOrderNeckToHip";
    public static final String D_ORDERS_COL12 = "dOrderProductDescription";
    public static final String D_ORDERS_COL13 = "dOrderProductImage";
    public static final String D_ORDERS_COL14 = "dOrderUserName";
    public static final String D_ORDERS_COL15 = "dOrderProductName";


    public DBHelper(Context context) {
        super(context, "NKROBES.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table customer(username TEXT primary key, name TEXT, email TEXT, password TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_2 + "("
                + PRODUCT_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PRODUCT_COL2 + " TEXT,"
                + PRODUCT_COL3 + " TEXT" + ")");

        db.execSQL("CREATE TABLE " + TABLE_3 + "("
                + PROFILE_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PROFILE_COL2 + " TEXT,"
                + PROFILE_COL3 + " TEXT,"
                + PROFILE_COL4 + " TEXT,"
                + PROFILE_COL5 + " TEXT" + ")");

        db.execSQL("CREATE TABLE " + TABLE_4 + "("
                + D_ORDERS_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + D_ORDERS_COL2 + " TEXT,"
                + D_ORDERS_COL3 + " TEXT,"
                + D_ORDERS_COL4 + " TEXT,"
                + D_ORDERS_COL5 + " TEXT,"
                + D_ORDERS_COL6 + " TEXT,"
                + D_ORDERS_COL7 + " TEXT,"
                + D_ORDERS_COL8 + " TEXT,"
                + D_ORDERS_COL9 + " TEXT,"
                + D_ORDERS_COL10 + " TEXT,"
                + D_ORDERS_COL11 + " TEXT,"
                + D_ORDERS_COL12 + " TEXT,"
                + D_ORDERS_COL13 + " TEXT,"
                + D_ORDERS_COL14 + " TEXT,"
                + D_ORDERS_COL15 + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists customer");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_2);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_3);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_4);
        onCreate(db);
    }

    public  Boolean insertUser(String username, String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("password",password);
        long result = db.insert("customer", null,contentValues);
        if ((result==-1)) {
            return false;
        } else {
            return true; }
    }

    public  Boolean updatePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",password);
        long result = db.update("customer",contentValues, "username = ?", new String[] {username});
        if ((result==-1)) {
            return false;
        } else {
            return true;
        }
    }

    public  Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from customer order by username", null);
        return cursor;
    }

    public  Boolean checkUsername(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from customer where username = ?", new String[] {username});
        if(cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }

    public  Boolean checkUsernameAndPassword(String username, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from customer where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor getUserPWD(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users where username='"+username+"'", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "customer");
        return numRows;
    }

    public long insertProduct (String image, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_COL2, image);
        values.put(PRODUCT_COL3, name);
        long id = db.insert(TABLE_2, null, values);
        return id;
    }

    public ArrayList<ProductModelRecord> viewProducts (String orderBy)
    {
        ArrayList<ProductModelRecord> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_2 + " ORDER BY " + orderBy;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ProductModelRecord productModelRecord = new ProductModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(PRODUCT_COL1)),
                        ""+cursor.getString(cursor.getColumnIndex(PRODUCT_COL2)),
                        ""+cursor.getString(cursor.getColumnIndex(PRODUCT_COL3)));
                productList.add(productModelRecord);
            }while (cursor.moveToNext()); }
        return productList;
    }

    public ArrayList<ProductModelRecord> searchProducts (String query)
    {
        ArrayList<ProductModelRecord> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_2 + " WHERE " + PRODUCT_COL3 + " LIKE '% " + query +"%' ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ProductModelRecord productModelRecord = new ProductModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(PRODUCT_COL1)),
                        ""+cursor.getString(cursor.getColumnIndex(PRODUCT_COL2)),
                        ""+cursor.getString(cursor.getColumnIndex(PRODUCT_COL3)));
                productList.add(productModelRecord);
            }while (cursor.moveToNext());
        }return productList;
    }

    public boolean insertProfile (String name, String email, String address, String telephone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROFILE_COL2, name);
        values.put(PROFILE_COL3, email);
        values.put(PROFILE_COL4, address);
        values.put(PROFILE_COL5, telephone);
        long result = db.insert(TABLE_3, null, values);
        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public long insertDesignerOrders (String doName, String doAddress, String doTelNumber, String doHeight, String doWaist,
                                      String doBust,String doNeck, String doHip, String doThigh, String doNeckToHip,
                                      String doProductDescription, String doProductImage,String userName,String pName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(D_ORDERS_COL2, doName); values.put(D_ORDERS_COL3, doAddress);
        values.put(D_ORDERS_COL4, doTelNumber); values.put(D_ORDERS_COL5, doHeight);
        values.put(D_ORDERS_COL6, doWaist); values.put(D_ORDERS_COL7, doBust);
        values.put(D_ORDERS_COL8, doNeck); values.put(D_ORDERS_COL9, doHip);
        values.put(D_ORDERS_COL10, doThigh); values.put(D_ORDERS_COL11, doNeckToHip);
        values.put(D_ORDERS_COL12, doProductDescription);
        values.put (D_ORDERS_COL13, doProductImage);
        values.put (D_ORDERS_COL14, userName);
        values.put (D_ORDERS_COL15, pName);
        long doId = db.insert(TABLE_4, null, values);
        return doId; }


    public ArrayList<DesignerOrderModelRecord> viewDesignerOrders (String orderBy)
    {
        ArrayList<DesignerOrderModelRecord> designerOrderList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_4 + " ORDER BY " + orderBy;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do { DesignerOrderModelRecord designerOrderModelRecord = new DesignerOrderModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(D_ORDERS_COL1)),
                        ""+cursor.getString(cursor.getColumnIndex(D_ORDERS_COL2)),
                        ""+cursor.getString(cursor.getColumnIndex(D_ORDERS_COL3)),
                        ""+cursor.getString(cursor.getColumnIndex(D_ORDERS_COL4)),
                        ""+cursor.getString(cursor.getColumnIndex(D_ORDERS_COL5)),
                        ""+cursor.getString(cursor.getColumnIndex(D_ORDERS_COL6)),
                        ""+cursor.getString(cursor.getColumnIndex(D_ORDERS_COL7)),
                        ""+cursor.getString(cursor.getColumnIndex(D_ORDERS_COL8)),
                        ""+cursor.getString(cursor.getColumnIndex(D_ORDERS_COL9)),
                        ""+cursor.getString(cursor.getColumnIndex(D_ORDERS_COL10)),
                        ""+cursor.getString(cursor.getColumnIndex(D_ORDERS_COL11)),
                        ""+cursor.getString(cursor.getColumnIndex(D_ORDERS_COL12)),
                    ""+cursor.getString(cursor.getColumnIndex(D_ORDERS_COL13)));
                designerOrderList.add(designerOrderModelRecord);
            }while (cursor.moveToNext());
        }return designerOrderList;
    }

    public void deleteDesignerOrder(String doId)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_4, D_ORDERS_COL1 + " =?", new String[]{doId});
    }
}