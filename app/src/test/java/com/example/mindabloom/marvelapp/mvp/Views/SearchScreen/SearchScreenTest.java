package com.example.mindabloom.marvelapp.mvp.Views.SearchScreen;

import com.example.mindabloom.marvelapp.BuildConfig;
import com.example.mindabloom.marvelapp.mvp.Interactors.SearchInteractor;
import com.example.mindabloom.marvelapp.mvp.Presenters.SearchPresenter;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by Ahmed Abdelaziz on 11/10/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class SearchScreenTest {

    private SearchScreen searchScreen;
    @Mock
    private SearchPresenter presenter;
    @Mock
    private SearchInteractor interactor;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        searchScreen = new SearchScreen();

    }
}