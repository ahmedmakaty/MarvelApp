package com.example.mindabloom.marvelapp.MarvelApi;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Ahmed Abdelaziz on 11/9/2016.
 */

public interface MarvelApiInterface {

    @GET("/v1/public/characters")
    Call<ResponseBody> searchCharacterByName(@QueryMap HashMap<String, Object> args);
}
