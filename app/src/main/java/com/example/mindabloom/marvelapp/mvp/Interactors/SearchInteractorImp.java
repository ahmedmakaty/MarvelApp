package com.example.mindabloom.marvelapp.mvp.Interactors;

import android.content.Context;
import android.util.Log;

import com.example.mindabloom.marvelapp.Database.MarvelDatabaseHelper;
import com.example.mindabloom.marvelapp.MarvelApi.MarvelApiClient;
import com.example.mindabloom.marvelapp.MarvelApi.MarvelApiInterface;
import com.example.mindabloom.marvelapp.R;
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

import static com.example.mindabloom.marvelapp.MarvelApp.getContext;

/**
 * Created by Ahmed Abdelaziz on 11/10/2016.
 */

public class SearchInteractorImp implements SearchInteractor {

    private MarvelApiInterface marvelApiInterface;
    private MarvelDatabaseHelper marvelDatabaseHelper;

    public SearchInteractorImp() {
        marvelDatabaseHelper = new MarvelDatabaseHelper(getContext());
    }

    /*
    * performs the api request using the retrofit client and the passed args from the presenter
    * contains a listener to the presenter to inform it with the service result
    */
    @Override
    public void searchByName(String name, String apiKey, String hash, long timestamp, final OnSearchResultFinishedListener listener) {

        HashMap<String, Object> getArgs = new HashMap<>();
        getArgs.put("name", name);
        getArgs.put("apikey", apiKey);
        getArgs.put("hash", hash);
        getArgs.put("ts", timestamp);

        marvelApiInterface = MarvelApiClient.getClient(getContext());

        Call<ResponseBody> call = marvelApiInterface.searchCharacterByName(getArgs);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("Call", "successful");
                    try {
                        /*
                        * Here I could have used a model and let retrofit and gson handle the parsing(json to pojo)
                        * or use Gson to map the json response to a pojo
                        * but I wanted to parse the response JSON object myself
                        */
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

                            /*
                            * passes the character data to the listener and inform it to perform an action as the
                            * search was successful and was able to fetch a character
                            */
                            listener.onSuccess(name, description, imagePath, imageExtension);
                        } else {
                            /*
                            * tells the listener to perform an action in case the result was empty
                            */
                            listener.onWrongNameError(R.string.wrong_name_error);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("JsonException", e.getMessage());
                    }
                } else {
                    /*
                    * parsing the error body to fetch the error message to print it to the console as required
                    */
                    try {
                        JSONObject error = new JSONObject(response.errorBody().string());
                        String errorMessage = error.getString("status");
                        Log.e("Api Error", errorMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listener.onApiError(R.string.api_error);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                /*
                * tells the listener to do an action in case of network failure
                */
                Log.e("Error", "Internet connection problem");
                listener.onApiError(R.string.api_error);
            }
        });
    }

    /*
    * tells the database handler to perform insert query to add the successfully searched name
    */
    @Override
    public void saveNameInDatabase(String name) {
        marvelDatabaseHelper.addName(name);
    }

    /*
    * tells the database to perform select query to retrieve the search history
    */
    @Override
    public List<String> getSearchHistory() {
        return marvelDatabaseHelper.getHistory();
    }
}
