package com.example.administrator.kossweather.util;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2016/3/4.
 */
public class HttpUtil {
    public static  void sendHttpRequest(final String address,final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {



                HttpURLConnection connection=null;
                try
                {
                    URL url=new URL(address);
                    Log.i("AAA",address);

                    connection=(HttpURLConnection)url.openConnection();

//                    if (Build.VERSION.SDK != null
//                            && Build.VERSION.SDK_INT > 13) {
//                      connection.setRequestProperty("Connection", "close");
//                    }

                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in=connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null)
                    {
                        response.append(line);
                    }
                    if(listener!=null)
                    {
                        listener.onFinish(response.toString());
                    }

                }catch (Exception e)
                {
                    if(listener!=null)
                    {
                        listener.onError(e);
                    }
                }finally {
                    if(connection!=null)
                    {
                        connection.disconnect();

                    }
                }
            }
        }).start();
    }

}
