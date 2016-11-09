package com.example.mindabloom.marvelapp.mvp.Interactors;

import com.example.mindabloom.marvelapp.mvp.Presenters.OnSearchResultFinishedListener;

/**
 * Created by Ahmed Abdelaziz on 11/9/2016.
 */

public interface SearchInteractor {
    void searchByName(String name, String apiKey, String hash, long timestamp, OnSearchResultFinishedListener presenter);

    void saveNameInDatabase(String name);
}
