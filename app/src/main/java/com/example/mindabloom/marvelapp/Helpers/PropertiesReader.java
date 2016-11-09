package com.example.mindabloom.marvelapp.Helpers;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Ahmed Abdelaziz on 11/9/2016.
 */

public class PropertiesReader {
    private Context context;
    private Properties properties;

    public PropertiesReader(Context context) {
        this.context = context;
        /**
         * Constructs a new Properties object.
         */
        properties = new Properties();
    }

    public Properties getProperties(String FileName) {

        try {
            /**
             * getAssets() Return an AssetManager instance for your
             * application's package. AssetManager Provides access to an
             * application's raw asset files;
             */
            AssetManager assetManager = context.getAssets();
            /**
             * Open an asset using ACCESS_STREAMING mode. This
             */
            InputStream inputStream = assetManager.open(FileName);
            /**
             * Loads properties from the specified InputStream,
             */
            properties.load(inputStream);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("AssetsPropertyReader",e.toString());
        }
        return properties;
    }
}

