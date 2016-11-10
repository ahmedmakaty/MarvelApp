package com.example.mindabloom.marvelapp;

import android.app.Application;
import android.content.Context;

import static okhttp3.internal.Internal.instance;

/**
 * Created by Ahmed Abdelaziz on 11/10/2016.
 * This is a singleton controller for the application
 */

public class MarvelApp extends Application {
    private static MarvelApp instance;
    private static Context mContext;

    public static MarvelApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        //  return instance.getApplicationContext();
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
    }
}