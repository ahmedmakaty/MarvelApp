package com.example.mindabloom.marvelapp.mvp.Views.SearchScreen;

import java.util.List;

/**
 * Created by Ahmed Abdelaziz on 11/10/2016.
 */

public interface SearchView {
    void showLoading();
    void hideLoading();
    void showApiError(int resId);

    void showWrongNameError(int resId);
    void hideWrongNameError();
    void populateHistoryList(List<String> names);

    void startResultActivity(String name, String description, String imagePath, String imageExtension);
}
