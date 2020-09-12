package com.hackathon.quackhacks.views;

import android.content.Context;
import android.text.Html;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.hackathon.quackhacks.R;
import com.hackathon.quackhacks.backend.Recipe;
import com.hackathon.quackhacks.backend.UserAccount;

import org.w3c.dom.Text;

import java.util.List;

public class RecipeView extends BaseView {
    public RecipeView(Context context, String user, Recipe recipe) {
        super(context);
        activity.setContentView(R.layout.recipedisplay);

        TextView recipeTitle = activity.findViewById(R.id.recipetitle);
        recipeTitle.setText(recipe.getTitle());

        TextView description = activity.findViewById(R.id.description);
        description.setText(recipe.getDescription());

        TextView ingredients = activity.findViewById(R.id.ingredients);
        List<String> ing = recipe.getIngredients();

        String temp = "";

        for(int i = 0; i < ing.size(); i++)
        {
            temp += ing.get(i) + "\n";
        }
        ingredients.setText(temp);

        ingredients.setText(temp);

        activity.findViewById(R.id.ExitPost).setOnClickListener( onclick -> {
            activity.changeView(new FeedView(context));
        });

    }
}
