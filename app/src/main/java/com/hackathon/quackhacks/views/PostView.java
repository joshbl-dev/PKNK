package com.hackathon.quackhacks.views;

import android.content.Context;

import com.hackathon.quackhacks.MainActivity;
import com.hackathon.quackhacks.R;
import com.hackathon.quackhacks.backend.Recipe;

public class PostView extends BaseView {

    public PostView(Context context) {
        super(context);
        activity.setContentView(R.layout.add_recipe_post);

        activity.findViewById(R.id.createRecipe).setOnClickListener( onclick -> {
            String recipeName = activity.findViewById(R.id.recipeTitle).toString();
            String nationality = activity.findViewById(R.id.nationality).toString();
            String type = activity.findViewById(R.id.insertType).toString();

            activity.getProfile().addRecipe((MainActivity)context, recipeName, new Recipe((MainActivity)context, recipeName, nationality, type));

            activity.changeView(new AdjustRecipe(context, recipeName));
        });


    }
}
