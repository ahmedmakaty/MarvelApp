package com.example.mindabloom.marvelapp.mvp.Presenters;

/**
 * Created by Ahmed Abdelaziz on 11/9/2016.
 */

public interface OnSearchResultFinishedListener {
    void onApiError(int resId);
    void onWrongNameError(int resId);

    void onSuccess(String name, String description, String imagePath, String imageExtension);
}
