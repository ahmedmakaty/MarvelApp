package com.example.mindabloom.marvelapp.mvp.Presenters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.mindabloom.marvelapp.Helpers.Digester;
import com.example.mindabloom.marvelapp.R;
import com.example.mindabloom.marvelapp.mvp.Interactors.SearchInteractor;
import com.example.mindabloom.marvelapp.mvp.Interactors.SearchInteractorImp;
import com.example.mindabloom.marvelapp.mvp.Views.ResultScreen.ResultScreen;
import com.example.mindabloom.marvelapp.mvp.Views.SearchScreen.SearchScreen;
import com.example.mindabloom.marvelapp.mvp.Views.SearchScreen.SearchView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.mindabloom.marvelapp.Injectors.PropertiesReaderInjector.propertiesReader;
import static com.example.mindabloom.marvelapp.MarvelApp.getContext;

/**
 * Created by Ahmed Abdelaziz on 11/9/2016.
 */

public class SearchPresenterImp implements SearchPresenter, OnSearchResultFinishedListener {

    private SearchView view;
    private SearchInteractor searchInteractor;
    public static final String FILE_NAME = "keys.properties";

    public SearchPresenterImp(SearchInteractor searchInteractor) {
        this.view = view;
        this.searchInteractor = searchInteractor;
    }

    @Override
    public void searchByName(String name) {

        Date date = new Date();
        long timestamp = date.getTime();

        String publicKey = propertiesReader(getContext()).getProperties(FILE_NAME).getProperty("publicKey");
        String privateKey = propertiesReader(getContext()).getProperties(FILE_NAME).getProperty("privateKey");
        String hash = Digester.md5Digest(String.valueOf(timestamp) + privateKey + publicKey);

        Log.d("Keys", "Public key:" + publicKey + " private key:" + privateKey + " timestamp:" + timestamp);
        Log.d("Hash", "Hash:" + hash);

        view.showLoading();
        searchInteractor.searchByName(name, publicKey, hash, timestamp, this);
    }

    @Override
    public void getSearchHistory() {
        List<String> names = new ArrayList<>();
        names = searchInteractor.getSearchHistory();
        view.populateHistoryList(names);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void attachView(SearchScreen searchScreen) {
        this.view = searchScreen;
    }

    @Override
    public void onApiError(int resId) {
        view.hideLoading();
        view.showApiError(resId);
    }

    @Override
    public void onWrongNameError(int resId) {
        view.hideLoading();
        view.showWrongNameError(resId);
    }

    @Override
    public void onSuccess(String name, String description, String imagePath, String imageExtension) {
        view.hideLoading();

        /*saving the character name in the database after successful fetch*/
        searchInteractor.saveNameInDatabase(name);

        view.startResultActivity(name, description, imagePath, imageExtension);
    }
}
