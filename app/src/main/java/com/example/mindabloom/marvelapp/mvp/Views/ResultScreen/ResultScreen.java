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

        /*
        *retrieve passed bundle data if new instance, or restoring data from saved bundle
        *in case of configuration change like rotating
        */
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

    /*
    *initializeViews() is used to populate the layout views with character data
    */
    @Override
    public void initializeViews() {
        name.setText(characterName);


        //special case when the returned description is empty
        if (characterDescription != null && !characterDescription.matches("")) {
            description.setText(characterDescription);
        } else {
            description.setText(getString(R.string.no_description));
        }

        /*
        *concatenate the image url as per api documentation
        */
        String url = "";
        url += characterImagePath + "/" + IMAGE_VARIANT + "." + characterImageExtension;


        /*
        *using glide lib to load and cache the character image from the compsed url
        */
        Glide.with(getApplicationContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_launcher)
                .into(thumbnail);
    }


    /*this is used to save the passed data to the activity in case any change in configuration like
    *rotating the screen
    */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("NAME", characterName);
        outState.putString("DESCRIPTION", characterDescription);
        outState.putString("IMAGEPATH", characterImagePath);
        outState.putString("IMAGEEXTENSION", characterImageExtension);
    }
}
