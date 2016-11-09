package com.example.mindabloom.marvelapp.Injectors;

import android.content.Context;

import com.example.mindabloom.marvelapp.Helpers.PropertiesReader;

/**
 * Created by Ahmed Abdelaziz on 11/9/2016.
 */

public class PropertiesReaderInjector {

    public static PropertiesReader propertiesReader(Context context){
        return new PropertiesReader(context);
    }
}
