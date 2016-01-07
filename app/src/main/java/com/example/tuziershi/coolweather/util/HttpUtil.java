package com.example.tuziershi.coolweather.util;

import android.renderscript.ScriptGroup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

/**
 * Created by 兔子二世 on 2016/1/7.
 */
public class HttpUtil{
    public static void sendHttpRequest(final String address,final HttpCallbackListener listener)
    {
       new Thread(new Runnable() {
           @Override
           public void run() {
               HttpURLConnection connection=null;
               try{
                   URL url=new URL(address);
                   connection=(HttpURLConnection)url.openConnection();
                   connection.setRequestMethod("GET");
                   connection.setConnectTimeout(8000);
                   connection.setReadTimeout(8000);
                   InputStream in=connection.getInputStream();
                   InputStreamReader inputStreamReader=new InputStreamReader(in);
                   BufferedReader reader=new BufferedReader(inputStreamReader);
                   StringBuilder response=new StringBuilder();
                   String line="";
                   while((line=reader.readLine())!=null)
                   {
                       response.append(line);
                   }
                   if(listener!=null)
                   {
                       listener.onfinish(response.toString());
                   }
               }catch (Exception e)
               {
                   if(listener!=null)
                   {
                       listener.onerror(e);
                   }
               }finally{
                   if(connection!=null)
                   {
                       connection.disconnect();
                   }
               }
           }
       });
    }
}
