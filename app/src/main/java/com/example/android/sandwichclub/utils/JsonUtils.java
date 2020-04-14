package com.example.android.sandwichclub.utils;

import android.util.Log;

import com.example.android.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /*
    * {"name":{"mainName":"Ham and cheese sandwich","alsoKnownAs":[]},"placeOfOrigin":"","description":"A ham and cheese sandwich is a common type of sandwich. It is made by putting cheese and sliced ham between two slices of bread. The bread is sometimes buttered and/or toasted. Vegetables like lettuce, tomato, onion or pickle slices can also be included. Various kinds of mustard and mayonnaise are also common.","image":"https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Grilled_ham_and_cheese_014.JPG/800px-Grilled_ham_and_cheese_014.JPG","ingredients":["Sliced bread","Cheese","Ham"]}
    * */

    public static Sandwich parseSandwichJson(String json) {

        Sandwich newSandwichObjectInstance = new Sandwich();

        try {

            JSONObject jsonObjRoot = new JSONObject(json);

            JSONObject name = jsonObjRoot.getJSONObject("name");

            String mainName = name.getString("mainName"); //this.mainName

            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs"); //this.alsoKnownAs
            List<String> tempKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                    String currentArrayPosition = alsoKnownAs.optString(i);
                    tempKnownAs.add(currentArrayPosition);
            }
            if(tempKnownAs.size() == 0) {
                tempKnownAs.add("**UnKnown**"); //fallBAck String
            }

            String placeOfOrigin = jsonObjRoot.getString("placeOfOrigin"); //this.placeOfOrigin

            String description = jsonObjRoot.getString("description"); //this.description;

            String image = jsonObjRoot.getString("image"); //this.image;

            List<String> tempIngredients = new ArrayList<>();
            JSONArray ingredients = jsonObjRoot.getJSONArray("ingredients"); //this.ingredients
            for (int i = 0; i < ingredients.length(); i++) {
                    String currentArrayPosition = ingredients.optString(i);
                    tempIngredients.add(currentArrayPosition);
            }
            if(tempIngredients.size() == 0) {
                tempIngredients.add("**UnKnown**"); //fallBAck String
            }

            newSandwichObjectInstance = new Sandwich(mainName,
                    tempKnownAs, placeOfOrigin, description, image, tempIngredients);

        }catch (JSONException e) {
            Log.e("JsonUtils", "Problem parsing the earthquake JSON results", e);
        }

        return newSandwichObjectInstance;
    }

}
