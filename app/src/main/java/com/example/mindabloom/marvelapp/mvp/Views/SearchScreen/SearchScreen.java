package com.example.mindabloom.marvelapp.mvp.Views.SearchScreen;

import android.app.ProgressDialog;
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
import com.example.mindabloom.marvelapp.mvp.Presenters.SearchPresenter;
import com.example.mindabloom.marvelapp.mvp.Presenters.SearchPresenterImp;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchScreen extends AppCompatActivity implements SearchView {

    //Using butterknife library to bind views to our layout without using findViewById inside onCreate
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
    private MarvelHistoryAdapter marvelHistoryAdapter;

    private List<String> names = new ArrayList<String>();

    //Our history list item click listener
    private MarvelHistoryAdapter.OnItemClickListener onItemClickListener = new MarvelHistoryAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String name) {
            presenter.searchByName(name);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        ButterKnife.bind(this);
        presenter = new SearchPresenterImp(this, this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);

        history.setLayoutManager(new LinearLayoutManager(this));

        presenter.getSearchHistory();
    }

    @Override
    public void populateHistoryList(List<String> names) {
        marvelHistoryAdapter = new MarvelHistoryAdapter(this, names, onItemClickListener);
        history.setAdapter(marvelHistoryAdapter);
        marvelHistoryAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.show_button)
    public void showCharacter() {
        hideWrongNameError();
        String name = characterName.getText().toString();
        presenter.searchByName(name);
    }

    @Override
    public void showLoading() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.hide();
        }
    }

    @Override
    public void showApiError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showWrongNameError() {
        wrongNameError.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideWrongNameError() {
        wrongNameError.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getSearchHistory();
    }
}
