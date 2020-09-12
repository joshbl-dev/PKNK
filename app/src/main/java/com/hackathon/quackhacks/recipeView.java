package com.hackathon.quackhacks;

import android.app.Activity;
import android.widget.TextView;

public class recipeView {

    public recipeView(Activity activity) {
        activity.setContentView(R.layout.recipedisplay);
        TextView recipeTitle = activity.findViewById(R.id.recipetitle);
    }
}
