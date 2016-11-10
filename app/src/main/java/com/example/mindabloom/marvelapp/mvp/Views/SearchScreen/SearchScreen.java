package com.example.mindabloom.marvelapp.mvp.Views.SearchScreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mindabloom.marvelapp.Adapters.MarvelHistoryAdapter;
import com.example.mindabloom.marvelapp.R;
import com.example.mindabloom.marvelapp.mvp.Interactors.SearchInteractor;
import com.example.mindabloom.marvelapp.mvp.Interactors.SearchInteractorImp;
import com.example.mindabloom.marvelapp.mvp.Presenters.SearchPresenter;
import com.example.mindabloom.marvelapp.mvp.Presenters.SearchPresenterImp;
import com.example.mindabloom.marvelapp.mvp.Views.ResultScreen.ResultScreen;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchScreen extends AppCompatActivity implements SearchView {

    //using butterknife library to bind views to our layout without using findViewById inside onCreate
    @Bind(R.id.character_name)
    EditText characterName;
    @Bind(R.id.show_button)
    Button show;
    @Bind(R.id.history_list)
    RecyclerView history;
    @Bind(R.id.wrong_name_text)
    TextView wrongNameError;

    private ProgressDialog progressDialog;
    private SearchPresenter presenter;
    private SearchInteractor interactor;
    private MarvelHistoryAdapter marvelHistoryAdapter;

    private List<String> names = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        ButterKnife.bind(this);

        interactor = new SearchInteractorImp();
        presenter = new SearchPresenterImp(interactor);
        presenter.attachView(this);

        /* initializing the progress dialog that I will show during handling api request*/
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);

        history.setLayoutManager(new LinearLayoutManager(this));

        /*calling getSearchHistory() retrieves the latest successfully searched character names*/
        presenter.getSearchHistory();
    }

    /*called by the presenter after the interactor is finished fetching the search history
    * is used to populate the recycler view holding the history items
    * */
    @Override
    public void populateHistoryList(List<String> names) {
        marvelHistoryAdapter = new MarvelHistoryAdapter(this, names, onItemClickListener);
        history.setAdapter(marvelHistoryAdapter);
        marvelHistoryAdapter.notifyDataSetChanged();
    }

    //our history list item click listener, the item name is provided to the interface
    private MarvelHistoryAdapter.OnItemClickListener onItemClickListener = new MarvelHistoryAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String name) {

            /*
            * hide the error if exists
            * clears the edittext for cleaner screen
            * calls the presenter's search method
            */
            hideWrongNameError();
            characterName.setText(null);
            presenter.searchByName(name);
        }
    };


    /*is used to redirect to the result screen after a successful search*/
    @Override
    public void startResultActivity(String name, String description, String imagePath, String imageExtension) {
        Intent intent = new Intent(this, ResultScreen.class);
        intent.putExtra("NAME", name);
        intent.putExtra("DESCRIPTION", description);
        intent.putExtra("IMAGEPATH", imagePath);
        intent.putExtra("IMAGEEXTENSION", imageExtension);
        startActivity(intent);
    }

    /*handles the click of the show button (butter knife listener)*/
    @OnClick(R.id.show_button)
    public void showCharacter() {
        /*
        * hiding the wrong name error if exists then extracts the text inside the edittext
        * then calls searchByName() to communicate with interactor
        */
        hideWrongNameError();
        String name = characterName.getText().toString();
        presenter.searchByName(name);
    }

    /*shows progress loading dialog*/
    @Override
    public void showLoading() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    /*hides the progress dialog*/
    @Override
    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.hide();
        }
    }

    /*generate a toast carrying a message after api error*/
    @Override
    public void showApiError(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    /*shows wrong name error*/
    @Override
    public void showWrongNameError(int resId) {
        wrongNameError.setText(getString(resId));
        wrongNameError.setVisibility(View.VISIBLE);
    }

    /*hides wrong name error*/
    @Override
    public void hideWrongNameError() {
        wrongNameError.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getSearchHistory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*this function is responsible for dropping the view passed to the presenter to prevent memory leaks*/
        presenter.onDestroy();
    }
}
