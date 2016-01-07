package com.example.tuziershi.coolweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.tuziershi.coolweather.service.AutoUpdateService;

import java.util.ResourceBundle;

/**
 * Created by 兔子二世 on 2016/1/7.
 */
public class AutoUpdateReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i=new Intent(context, AutoUpdateService.class);
        context.startService(i);
    }
}
