package com.hackathon.quackhacks.views;

import android.content.Context;
import android.widget.TextView;

import com.hackathon.quackhacks.R;

public class RecipeView extends BaseView {

    public RecipeView(Context context) {
        super(context);
        activity.setContentView(R.layout.recipedisplay);
        TextView recipeTitle = activity.findViewById(R.id.recipetitle);
    }
}
