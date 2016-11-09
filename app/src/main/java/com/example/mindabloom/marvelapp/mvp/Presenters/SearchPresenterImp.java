package com.example.mindabloom.marvelapp.mvp.Presenters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.mindabloom.marvelapp.Helpers.Digester;
import com.example.mindabloom.marvelapp.R;
import com.example.mindabloom.marvelapp.mvp.Interactors.SearchInteractor;
import com.example.mindabloom.marvelapp.mvp.Interactors.SearchInteractorImp;
import com.example.mindabloom.marvelapp.mvp.Views.ResultScreen.ResultScreen;
import com.example.mindabloom.marvelapp.mvp.Views.SearchScreen.SearchView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.mindabloom.marvelapp.Injectors.PropertiesReaderInjector.propertiesReader;

/**
 * Created by Ahmed Abdelaziz on 11/9/2016.
 */

public class SearchPresenterImp implements SearchPresenter, OnSearchResultFinishedListener {
    private Context context;
    private SearchView view;
    private SearchInteractor searchInteractor;
    public static final String FILE_NAME = "keys.properties";

    public SearchPresenterImp(Context context, SearchView view) {
        this.context = context;
        this.view = view;
        this.searchInteractor = new SearchInteractorImp(context);
    }

    @Override
    public void searchByName(String name) {

        Date date = new Date();
        long timestamp = date.getTime();

        String publicKey = propertiesReader(context).getProperties(FILE_NAME).getProperty("publicKey");
        String privateKey = propertiesReader(context).getProperties(FILE_NAME).getProperty("privateKey");
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
    public void onApiError() {
        view.hideLoading();
        view.showApiError(context.getString(R.string.api_error));
    }

    @Override
    public void onWrongNameError() {
        view.hideLoading();
        view.showWrongNameError();
    }

    @Override
    public void onSuccess(String name, String description, String imagePath, String imageExtension) {
        view.hideLoading();

        /*saving the character name in the database after successful fetch*/
        searchInteractor.saveNameInDatabase(name);

        Intent intent = new Intent(context, ResultScreen.class);
        intent.putExtra("NAME", name);
        intent.putExtra("DESCRIPTION", description);
        intent.putExtra("IMAGEPATH", imagePath);
        intent.putExtra("IMAGEEXTENSION", imageExtension);
        context.startActivity(intent);
    }
}
