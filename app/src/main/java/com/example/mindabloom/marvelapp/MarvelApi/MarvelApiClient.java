package com.example.mindabloom.marvelapp.MarvelApi;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ahmed Abdelaziz on 11/9/2016.
 */

public class MarvelApiClient {

    private static final String TAG = "MarvelApiClient";

    private static MarvelApiInterface apiInterface;
    // this is the base url of the used api
    private static String baseURL = "http://gateway.marvel.com:80";

    public static MarvelApiInterface getClient(Context context) {
        if (apiInterface == null) {

            /*
            * setting the http configurations for the client
            */
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor(context))
                    .addInterceptor(interceptor)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiInterface = retrofit.create(MarvelApiInterface.class);
        }

        return apiInterface;
    }

    public static void clear() {
        apiInterface = null;
    }

    private static class LoggingInterceptor implements Interceptor {

        public LoggingInterceptor(Context context) {

        }

        /*
        * intercept is used for adding headers to the request
        */
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            request = request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = chain.proceed(request);

            String bodyString = response.body().string();

            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();
        }
    }
}