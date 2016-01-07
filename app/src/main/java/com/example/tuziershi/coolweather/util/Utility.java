package com.example.tuziershi.coolweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.tuziershi.coolweather.model.City;
import com.example.tuziershi.coolweather.model.CoolWeatherDB;
import com.example.tuziershi.coolweather.model.County;
import com.example.tuziershi.coolweather.model.Province;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 兔子二世 on 2016/1/7.
 */
public class Utility {
    public synchronized static boolean handleProvicesResponse(CoolWeatherDB coolWeatherDB,String response)
    {
        if(!TextUtils.isEmpty(response))
        {
            String[] allProvinces=response.split(",");  //这个只需要做一次就可以将各个逗号分隔的项放置到字符串数组中了；
            if(allProvinces!=null&&allProvinces.length>0)
            {
                for(String p:allProvinces)   //逗号分隔分割的是项。每一项还要通过中间的|分隔成标识号和名称；
                {
                    String[] array=p.split("\\|");
                    Province province=new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
    public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,String response,int provinceId)
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
                coolWeatherDB.saveCity(city);
            }
            return true;
        }
    }
    return false;
}

    public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,String response,int cityId)
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
                    coolWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
    public static void handleWeatherResponse(Context context,String response)
    {
        try{
            JSONObject jsonObject=new JSONObject(response);  //本身是一个JSONObject
            JSONObject weatherInfo=jsonObject.getJSONObject("weatherinfo"); //从JSONObject中得到另一个JSONObject；
            String cityName=weatherInfo.getString("city");
            String weatherCode=weatherInfo.getString("cityid");
            String temp1=weatherInfo.getString("temp1");
            String temp2=weatherInfo.getString("temp2");
            String weatherDesp=weatherInfo.getString("weather");
            String publishTime=weatherInfo.getString("ptime");
            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
        }catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
    public static void saveWeatherInfo(Context context,String cityName ,String weatherCode,String temp1,String temp2,String weatherDesp,String publishTime)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年M月d日",Locale.CHINA);
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date", sdf.format(new Date()));
        editor.commit();
    }
}

