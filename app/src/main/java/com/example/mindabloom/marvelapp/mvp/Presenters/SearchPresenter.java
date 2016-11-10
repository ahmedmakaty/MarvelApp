package com.example.mindabloom.marvelapp.mvp.Presenters;

import android.view.View;

import com.example.mindabloom.marvelapp.mvp.Views.SearchScreen.SearchScreen;
import com.example.mindabloom.marvelapp.mvp.Views.SearchScreen.SearchView;

import java.util.List;

/**
 * Created by Ahmed Abdelaziz on 11/10/2016.
 */

public interface SearchPresenter {
    void searchByName(String name);

    void getSearchHistory();

    void onDestroy();

    SearchView getView();

    void attachView(SearchScreen searchScreen);
}
