package com.hackathon.quackhacks.views;

import android.widget.TextView;

import com.hackathon.quackhacks.MainActivity;
import com.hackathon.quackhacks.R;

public class RecipeView extends MainActivity {

    public RecipeView() {
        setContentView(R.layout.recipedisplay);
        TextView recipeTitle = findViewById(R.id.recipetitle);
    }
}
