package com.example.administrator.kossweather.util;

/**
 * Created by Administrator on 2016/3/4.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
