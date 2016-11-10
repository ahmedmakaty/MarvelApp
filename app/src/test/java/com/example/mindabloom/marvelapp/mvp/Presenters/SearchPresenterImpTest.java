package com.example.mindabloom.marvelapp.mvp.Presenters;

import com.example.mindabloom.marvelapp.mvp.Interactors.SearchInteractor;
import com.example.mindabloom.marvelapp.mvp.Views.SearchScreen.SearchScreen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by Ahmed Abdelaziz on 11/10/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class SearchPresenterImpTest {

    private SearchPresenter presenter;
    @Mock
    private SearchInteractor interactor;
    @Mock
    private SearchScreen view;

    @Before
    public void setUp() throws Exception {

        presenter = new SearchPresenterImp(interactor);
        presenter.attachView(view);
    }

    @Test
    public void checkIfLoadingAppears() {
        presenter.searchByName("");
        verify(view).showLoading();
    }
}