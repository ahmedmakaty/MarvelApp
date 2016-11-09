package com.example.mindabloom.marvelapp.mvp.Views.ResultScreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mindabloom.marvelapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultScreen extends AppCompatActivity implements ResultView {

    public static final String IMAGE_VARIANT = "portrait_xlarge";

    @Bind(R.id.thumbnail)
    ImageView thumbnail;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.description)
    TextView description;

    String characterName;
    String characterDescription;
    String characterImagePath;
    String characterImageExtension;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);

        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            characterName = extras.getString("NAME");
            characterDescription = extras.getString("DESCRIPTION");
            characterImagePath = extras.getString("IMAGEPATH");
            characterImageExtension = extras.getString("IMAGEEXTENSION");
        } else {
            characterName = savedInstanceState.getString("NAME");
            characterDescription = savedInstanceState.getString("DESCRIPTION");
            characterImagePath = savedInstanceState.getString("IMAGEPATH");
            characterImageExtension = savedInstanceState.getString("IMAGEEXTENSION");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        initializeViews();
    }

    @Override
    public void initializeViews() {
        name.setText(characterName);
        description.setText(characterDescription);

        String url = "";
        url += characterImagePath + "/" + IMAGE_VARIANT + "." + characterImageExtension;

        Glide.with(getApplicationContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_launcher)
                .into(thumbnail);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("NAME", characterName);
        outState.putString("DESCRIPTION", characterDescription);
        outState.putString("IMAGEPATH", characterImagePath);
        outState.putString("IMAGEEXTENSION", characterImageExtension);
    }
}
