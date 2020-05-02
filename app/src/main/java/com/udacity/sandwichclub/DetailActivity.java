package com.udacity.sandwichclub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mAlsoKnownAs;
    private TextView mIngredients;
    private TextView mPlaceOfOrigin;
    private TextView mDescription;
    private ImageView ingredientsIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        mIngredients = (TextView) findViewById(R.id.ingredients_tv);
        mPlaceOfOrigin = (TextView) findViewById(R.id.origin_tv);
        mDescription = (TextView) findViewById(R.id.description_tv);
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
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mDescription.append(sandwich.getDescription());
        mPlaceOfOrigin.append(sandwich.getPlaceOfOrigin() + "\n\n\n");
        if (sandwich.getIngredients().size() != 0) {
            for (String string : sandwich.getIngredients()) {
                mIngredients.append(string + ",");
            }
            mIngredients.append("\n\n\n");
        }else {
            mIngredients.append("None");
        }
        if (sandwich.getAlsoKnownAs().size() != 0){
            for (String string:sandwich.getAlsoKnownAs()){
                mAlsoKnownAs.append(string + ",");
            }
            mAlsoKnownAs.append("\n\n\n");
        } else {
            mAlsoKnownAs.append("None");
        }
        ingredientsIv.setImageURI(Uri.parse(sandwich.getImage()));

    }
}
