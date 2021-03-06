package com.example.mindabloom.marvelapp.mvp.Interactors;

import com.example.mindabloom.marvelapp.mvp.Presenters.OnSearchResultFinishedListener;

import java.util.List;

/**
 * Created by Ahmed Abdelaziz on 11/10/2016.
 */

public interface SearchInteractor {
    void searchByName(String name, String apiKey, String hash, long timestamp, OnSearchResultFinishedListener listener);

    void saveNameInDatabase(String name);

    List<String> getSearchHistory();
}
