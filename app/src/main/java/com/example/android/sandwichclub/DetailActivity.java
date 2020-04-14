package com.example.android.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sandwichclub.model.Sandwich;
import com.example.android.sandwichclub.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mAlsoKnownAs;
    private TextView mPlaceOfOrigin;
    private TextView mDescription;
    private ImageView mImage;
    private TextView mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

       // ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich.getAlsoKnownAs(), sandwich.getPlaceOfOrigin(), sandwich.getDescription(), sandwich.getImage(), sandwich.getIngredients());

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(List<String> alsoKNownAs, String placeOfOrigin, String description, String image, List<String> ingredients) {

        mAlsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        mPlaceOfOrigin = (TextView) findViewById(R.id.origin_tv);
        mDescription = (TextView) findViewById(R.id.description_tv);
        mImage = (ImageView) findViewById(R.id.image_iv);
        mIngredients = (TextView) findViewById(R.id.ingredients_tv);

        /*picasso examples here https://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library*/
        Picasso.with(this)
                .load(image)
                .into(mImage);
        mDescription.setText(description);

        mPlaceOfOrigin.setText(placeOfOrigin);

        for(String knownAs : alsoKNownAs) {
            mAlsoKnownAs.append(knownAs + "\n");
        }

        for(String tempIngredients : ingredients) {
            mIngredients.append(tempIngredients + "\n");
        }
        //System.out.println("knownAs " + alsoKNownAs + " placeOfOrigin " + placeOfOrigin + " descrip " + description + " image " + image + " ingredients " + ingredients);

    }

}
