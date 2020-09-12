package com.hackathon.quackhacks.views;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hackathon.quackhacks.MainActivity;
import com.hackathon.quackhacks.R;

public class RecipeView extends View {

    public RecipeView(Context context) {
        super(context);

        MainActivity activity = (MainActivity) context;
        activity.setContentView(R.layout.recipedisplay);
        TextView recipeTitle = activity.findViewById(R.id.recipetitle);
    }
}
