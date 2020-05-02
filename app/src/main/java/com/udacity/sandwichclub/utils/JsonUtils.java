package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String mainName = jsonObject.getJSONObject("name").getString("mainName");
        Log.v("mainName", mainName);
        JSONArray alsoKnownAs = jsonObject.getJSONObject("name").getJSONArray("alsoKnownAs");
        String placeOforigin = jsonObject.getString("placeOfOrigin");
        String description = jsonObject.getString("description");
        String img_url = jsonObject.getString("image");
        JSONArray ingredients = jsonObject.getJSONArray("ingredients");
        List<String> alsoKnownAs_list = new ArrayList<String>();
        List<String> ingredients_list = new ArrayList<String>();
        if (alsoKnownAs.length() != 0) {
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                alsoKnownAs_list.add(alsoKnownAs.get(i).toString());
            }
        }
        if (ingredients.length() != 0) {
            for (int i = 0; i < ingredients.length(); i++) {
                System.out.println(ingredients);
                ingredients_list.add(ingredients.get(i).toString());
            }
        }
        return new Sandwich(mainName, alsoKnownAs_list, placeOforigin, description, img_url, ingredients_list);
    }
}
