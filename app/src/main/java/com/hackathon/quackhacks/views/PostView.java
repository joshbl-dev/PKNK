package com.hackathon.quackhacks.views;

import android.content.Context;
import android.widget.EditText;
import android.widget.Spinner;

import com.hackathon.quackhacks.R;
import com.hackathon.quackhacks.backend.Recipe;

public class PostView extends BaseView {

    public PostView(Context context) {
        super(context);
        activity.setContentView(R.layout.add_recipe_post);

        activity.findViewById(R.id.createRecipe).setOnClickListener( onclick -> {
            EditText recipeName = activity.findViewById(R.id.recipeTitle);
            String nationality = ((Spinner) activity.findViewById(R.id.nationality)).getSelectedItem().toString();
            String type = ((Spinner) activity.findViewById(R.id.insertType)).getSelectedItem().toString();

            
            String recipeNameStr = recipeName.getText().toString();
            if(recipeNameStr.isEmpty()) {
                recipeName.setError("Recipe Name Not Found!");
            }

            activity.getProfile().addRecipe(activity, recipeNameStr, new Recipe(activity, recipeNameStr, nationality, type));

            activity.changeView(new AdjustRecipe(context, recipeNameStr));
        });

        activity.findViewById(R.id.ExitPost).setOnClickListener( onclick -> {
            activity.changeView(new FeedView(context));
        });

    }
}
