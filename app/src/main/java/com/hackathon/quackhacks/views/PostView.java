package com.hackathon.quackhacks.views;

import android.content.Context;
import android.widget.EditText;

import com.hackathon.quackhacks.MainActivity;
import com.hackathon.quackhacks.R;
import com.hackathon.quackhacks.backend.Recipe;

public class PostView extends BaseView {

    public PostView(Context context) {
        super(context);
        activity.setContentView(R.layout.add_recipe_post);

        activity.findViewById(R.id.createRecipe).setOnClickListener( onclick -> {
            EditText recipeName = activity.findViewById(R.id.recipeTitle);
            String nationality = activity.findViewById(R.id.nationality).toString();
            String type = activity.findViewById(R.id.insertType).toString();

            if(recipeName.toString().isEmpty()) {
                recipeName.setError("Recipe Name Not Found!");
            }

            activity.getProfile().addRecipe(activity, recipeName.toString(), new Recipe(activity, recipeName.toString(), nationality, type));

            activity.changeView(new AdjustRecipe(context, recipeName.toString()));
        });

        activity.findViewById(R.id.ExitPost).setOnClickListener( onclick -> {
            activity.changeView(new FeedView(context));
        });

    }
}
