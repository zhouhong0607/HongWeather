package com.example.administrator.kossweather.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/3/4.
 */
public class KOSSWeatherOpenHelper extends SQLiteOpenHelper {

    /**
     * 建表Province
     */
    public static final String CREATE_PROVINCE = "create table Province (" + " id integer primary key autoincrement , " + " province_name text," + " province_code text )";
    /**
     * 建表City
     */
    public static final String CREATE_CITY = "create table City (" + " id integer primary key autoincrement , " + " city_name text," + " city_code text ," + "province_id integer)";
    /**
     * 建表City
     */
    public static final String CREATE_COUNTY = "create table County (" + " id integer primary key autoincrement , " + " county_name text," + " county_code text, " + "city_id integer )";

    public KOSSWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}