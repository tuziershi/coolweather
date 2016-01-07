package com.example.tuziershi.coolweather.util;

import android.text.TextUtils;

import com.example.tuziershi.coolweather.model.City;
import com.example.tuziershi.coolweather.model.CoolWeatherDB;
import com.example.tuziershi.coolweather.model.County;
import com.example.tuziershi.coolweather.model.Province;

import org.w3c.dom.Text;

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
}

