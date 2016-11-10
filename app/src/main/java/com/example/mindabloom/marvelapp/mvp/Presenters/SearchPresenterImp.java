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
        this.searchInteractor = searchInteractor;
    }

    /*
    *shows loading, crafting arguments passed to the get request by retrieving the public and private
    * keys  from the properties file using a static function to an injected object of propertiesReader
    * then uses the Digester to generate the md5 hash from the timestamp, private and public keys
    * passing the arguments to the interactor to perform the api request
    */
    @Override
    public void searchByName(String name) {
        view.showLoading();
        Date date = new Date();
        long timestamp = date.getTime();

        String publicKey = propertiesReader(getContext()).getProperties(FILE_NAME).getProperty("publicKey");
        String privateKey = propertiesReader(getContext()).getProperties(FILE_NAME).getProperty("privateKey");
        String hash = Digester.md5Digest(String.valueOf(timestamp) + privateKey + publicKey);

        //Log.d("Keys", "Public key:" + publicKey + " private key:" + privateKey + " timestamp:" + timestamp);
        //Log.d("Hash", "Hash:" + hash);

        searchInteractor.searchByName(name, publicKey, hash, timestamp, this);
    }

    /*
    *tells the interactor to retrieve the latest successfully searched characters from the database
    *then tells the view to populate the recycler view with the results
    */
    @Override
    public void getSearchHistory() {
        List<String> names = new ArrayList<>();
        names = searchInteractor.getSearchHistory();
        view.populateHistoryList(names);
    }

    /*
    *dropping the view in case of activity destroy to prevent memory leaks
    */
    @Override
    public void onDestroy() {
        view = null;
    }

    /*
    * attach the view to the presenter which will be used by the presenter to communicate with the
    * activity
    */
    @Override
    public void attachView(SearchScreen searchScreen) {
        this.view = searchScreen;
    }

    /*
    * hides the progress dialog and tells the view to show the api error action
    */
    @Override
    public void onApiError(int resId) {
        view.hideLoading();
        view.showApiError(resId);
    }

    /*
    * hides the progress dialog and tells the view to show the wrong name action
    */
    @Override
    public void onWrongNameError(int resId) {
        view.hideLoading();
        view.showWrongNameError(resId);
    }


    /*
    * hides the progress dialog and tells the interactor to save the successful searched name
    * in the sqlite database then tells the view to navigate to the result screen
    */
    @Override
    public void onSuccess(String name, String description, String imagePath, String imageExtension) {

        view.hideLoading();

        /*saving the character name in the database after successful fetch*/
        searchInteractor.saveNameInDatabase(name);

        view.startResultActivity(name, description, imagePath, imageExtension);
    }
}
