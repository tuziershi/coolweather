package com.example.tuziershi.coolweather.util;

/**
 * Created by 兔子二世 on 2016/1/7.
 */
public interface HttpCallbackListener {
     void onfinish(String response);
     void onerror(Exception e);
}
