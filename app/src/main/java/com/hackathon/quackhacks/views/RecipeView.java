package com.hackathon.quackhacks.views;

import android.widget.TextView;

import com.hackathon.quackhacks.MainActivity;
import com.hackathon.quackhacks.R;

public class RecipeView {

    public RecipeView(MainActivity activity) {
        activity.setContentView(R.layout.recipedisplay);
        TextView recipeTitle = activity.findViewById(R.id.recipetitle);
    }
}
