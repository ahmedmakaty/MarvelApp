package com.example.mindabloom.marvelapp.mvp.Presenters;

import com.example.mindabloom.marvelapp.mvp.Views.SearchScreen.SearchScreen;

import java.util.List;

/**
 * Created by Ahmed Abdelaziz on 11/9/2016.
 */

public interface SearchPresenter {
    void searchByName(String name);

    void getSearchHistory();

    void onDestroy();

    void attachView(SearchScreen searchScreen);
}
