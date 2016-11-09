package com.example.mindabloom.marvelapp.mvp.Presenters;

/**
 * Created by Ahmed Abdelaziz on 11/9/2016.
 */

public interface OnSearchResultFinishedListener {
    void onApiError();
    void onWrongNameError();

    void onSuccess(String name, String description, String imagePath, String imageExtension);
}
