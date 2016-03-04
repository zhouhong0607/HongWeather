package com.example.administrator.kossweather.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.kossweather.datamodel.City;
import com.example.administrator.kossweather.datamodel.County;
import com.example.administrator.kossweather.datamodel.Province;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/3/4.
 */
public class DatabaseOperator {
    public static final String DB_NAME="HongWeather";

    public static  final int VERSION=1;
    private static DatabaseOperator databaseOperator;
    private static SQLiteDatabase   db;//静态类型
    /**
     * 构造方法
     */
    private DatabaseOperator(Context context)//构造函数里面打开数据库
    {
        KOSSWeatherOpenHelper hongWeatherOpenHelper=new KOSSWeatherOpenHelper(context,DB_NAME,null,VERSION);
        db=hongWeatherOpenHelper.getWritableDatabase();
    }
    /**
     * 获取唯一实例
     */
    public synchronized static DatabaseOperator getInstance(Context context) //将访问共享资源的方法设置成 同步方法
    {
        if(databaseOperator==null)
        {
            databaseOperator=new DatabaseOperator(context);
        }
        return  databaseOperator;
    }

    /**
     * 存储Province
     */
    public void saveProvinceToDB(Province province)
    {
        if (province!=null)
        {
            ContentValues contentValues=new ContentValues();
            contentValues.put("province_name",province.getProvinceName());
            contentValues.put("province_code",province.getProvinceCode());
            db.insert("Province",null,contentValues);
        }
    }
    /**
     * 查询Province
     */
    public List<Province> queryFromProvince()
    {
        List<Province> queryList=new ArrayList<>();
        Cursor cursor=db.query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            do {
                Province province=new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                queryList.add(province);
            }while (cursor.moveToNext());
        }

        if(cursor!=null)
            cursor.close();

        return queryList;
    }

    /**
     * 存储City
     */
    public void saveCityToDB(City city)
    {
        if (city!=null)
        {
            ContentValues contentValues=new ContentValues();
            contentValues.put("city_name",city.getCityName());
            contentValues.put("city_code", city.getCityCode());
            contentValues.put("province_id",city.getProvinceId());
            db.insert("City",null,contentValues);
        }
    }
    /**
     * 查询City
     */
    public List<City> queryFromCity(String provinceId)
    {
        List<City> queryList=new ArrayList<>();
        Cursor cursor=db.query("City",null,"province_id=?",new String[]{provinceId} ,null,null,null);
        if(cursor.moveToFirst())
        {
            do {
                City city=new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(cursor.getString(cursor.getColumnIndex("province_id")));
                queryList.add(city);
            }while (cursor.moveToNext());
        }

        if(cursor!=null)
            cursor.close();

        return queryList;
    }

    /**
     * 存储County
     */
    public void saveCountyToDB(County county)
    {
        if (county!=null)
        {
            ContentValues contentValues=new ContentValues();
            contentValues.put("county_name",county.getCountyName());
            contentValues.put("county_code", county.getCountyCode());
            contentValues.put("city_id",county.getCityId());
            db.insert("County",null,contentValues);
        }
    }
    /**
     * 查询County
     */
    public List<County> queryFromCounty(String cityId)
    {
        List<County> queryList=new ArrayList<>();
        Cursor cursor=db.query("County",null," city_id=?",new String[]{cityId},null,null,null);
        if(cursor.moveToFirst())
        {
            do {
                County county=new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cursor.getString(cursor.getColumnIndex("city_id")));
                queryList.add(county);
            }while (cursor.moveToNext());
        }

        if(cursor!=null)
            cursor.close();

        return queryList;
    }

}

