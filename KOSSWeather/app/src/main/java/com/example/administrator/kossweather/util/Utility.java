package com.example.administrator.kossweather.util;

import android.text.TextUtils;
import android.widget.TextView;

import com.example.administrator.kossweather.database.DatabaseOperator;
import com.example.administrator.kossweather.datamodel.City;
import com.example.administrator.kossweather.datamodel.County;
import com.example.administrator.kossweather.datamodel.Province;

/**
 * Created by Administrator on 2016/3/4.
 */
public class Utility {
    public synchronized static boolean handleProvincesResponse(DatabaseOperator databaseOperator,String response)
    {
        if(!TextUtils.isEmpty(response))
        {
             String[] allProvinces=response.split(",");
            if(allProvinces!=null&&allProvinces.length>0)
            {
                for(String p:allProvinces)
                {
                    String[] array=p.split("\\|");
                    Province province=new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    databaseOperator.saveProvinceToDB(province);
                }
                return  true;
            }


        }

        return  false;
    }

    public static boolean handleCitiesResponse(DatabaseOperator databaseOperator,String response,String provinceId)
    {
        if(!TextUtils.isEmpty(response))
        {
            String[] allCities=response.split(",");
            if(allCities!=null&&allCities.length>0)
            {
                for(String c:allCities)
                {
                    String[] array=c.split("\\|");
                    City city=new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    databaseOperator.saveCityToDB(city);
                }
                return  true;

            }
        }
        return  false;
    }

    public static boolean handleCountiesResponse(DatabaseOperator databaseOperator,String response,String cityId)
    {
        if(!TextUtils.isEmpty(response))
        {
            String[] allCounties=response.split(",");
            if(allCounties!=null&&allCounties.length>0)
            {
                for(String c:allCounties)
                {
                    String[] array=c.split("\\|");
                    County county=new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    databaseOperator.saveCountyToDB(county);
                }
                return  true;
            }

        }
        return false;
    }


//    private void addData()
//    {
//        Province province1=new Province();
//        province1.setId(1);
//        province1.setProvinceName("辽宁");
//        province1.setProvinceCode("01");
//
//        Province province2=new Province();
//        province2.setId(2);
//        province2.setProvinceName("北京");
//        province2.setProvinceCode("02");
//
//        databaseOperator.saveProvinceToDB(province1);
//        databaseOperator.saveProvinceToDB(province2);
//
//        City city1=new City();
//        city1.setId(1);
//        city1.setCityName("营口");
//        city1.setCityCode("0101");
//        city1.setProvinceId("01");
//
//        City city2=new City();
//        city2.setId(2);
//        city2.setCityName("锦州");
//        city2.setCityCode("0102");
//        city2.setProvinceId("01");
//
//        databaseOperator.saveCityToDB(city1);
//        databaseOperator.saveCityToDB(city2);
//
//        County county1=new County();
//        county1.setId(1);
//        county1.setCountyName("大石桥");
//        county1.setCountyCode("010101");
//        county1.setCityId("0101");
//
//        County county2=new County();
//        county2.setId(2);
//        county2.setCountyName("鲅鱼圈");
//        county2.setCountyCode("010102");
//        county2.setCityId("0101");
//
//        databaseOperator.saveCountyToDB(county1);
//        databaseOperator.saveCountyToDB(county2);
//    }

}
