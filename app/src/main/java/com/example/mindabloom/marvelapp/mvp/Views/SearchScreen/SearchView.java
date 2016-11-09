package com.example.mindabloom.marvelapp.mvp.Views.SearchScreen;

/**
 * Created by Ahmed Abdelaziz on 11/9/2016.
 */

public interface SearchView {
    void showLoading();
    void hideLoading();
    void showApiError(String error);

    void showWrongNameError();
    void hideWrongNameError();
}
