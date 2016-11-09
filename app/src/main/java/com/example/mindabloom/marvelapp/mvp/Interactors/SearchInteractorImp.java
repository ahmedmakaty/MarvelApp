package com.example.mindabloom.marvelapp.mvp.Interactors;

import android.content.Context;
import android.util.Log;

import com.example.mindabloom.marvelapp.Database.MarvelDatabaseHelper;
import com.example.mindabloom.marvelapp.MarvelApi.MarvelApiClient;
import com.example.mindabloom.marvelapp.MarvelApi.MarvelApiInterface;
import com.example.mindabloom.marvelapp.mvp.Presenters.OnSearchResultFinishedListener;
import com.example.mindabloom.marvelapp.mvp.Presenters.SearchPresenter;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ahmed Abdelaziz on 11/9/2016.
 */

public class SearchInteractorImp implements SearchInteractor {

    private Context context;
    private MarvelApiInterface marvelApiInterface;
    private MarvelDatabaseHelper marvelDatabaseHelper;

    public SearchInteractorImp(Context context) {
        this.context = context;
        marvelDatabaseHelper = new MarvelDatabaseHelper(context);
    }

    @Override
    public void searchByName(String name, String apiKey, String hash, long timestamp, final OnSearchResultFinishedListener listener) {

        HashMap<String, Object> getArgs = new HashMap<>();
        getArgs.put("name", name);
        getArgs.put("apikey", apiKey);
        getArgs.put("hash", hash);
        getArgs.put("ts", timestamp);

        marvelApiInterface = MarvelApiClient.getClient(context);

        Call<ResponseBody> call = marvelApiInterface.searchCharacterByName(getArgs);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("Call", "successful");
                    try {
                        JSONObject responseBody = new JSONObject(response.body().string());
                        JSONObject data = responseBody.getJSONObject("data");
                        JSONArray results = data.getJSONArray("results");
                        if (results.length() > 0) {
                            JSONObject character = results.getJSONObject(0);
                            JSONObject thumbnail = character.getJSONObject("thumbnail");
                            String name = character.getString("name");
                            String description = character.getString("description");
                            String imagePath = thumbnail.getString("path");
                            String imageExtension = thumbnail.getString("extension");

                            listener.onSuccess(name, description, imagePath, imageExtension);
                        } else {
                            listener.onWrongNameError();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("JsonException", e.getMessage());
                    }
                } else {
                    try {
                        JSONObject error = new JSONObject(response.errorBody().string());
                        String errorMessage = error.getString("status");
                        Log.e("Api Error", errorMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listener.onApiError();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", "Internet connection problem");
                listener.onApiError();
            }
        });
    }

    @Override
    public void saveNameInDatabase(String name) {
        marvelDatabaseHelper.addName(name);
    }

    @Override
    public List<String> getSearchHistory() {
        return marvelDatabaseHelper.getHistory();
    }
}
