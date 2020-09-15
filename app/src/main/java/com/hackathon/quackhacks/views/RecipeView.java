package com.hackathon.quackhacks.views;

import android.content.Context;
import android.widget.TextView;

import com.hackathon.quackhacks.R;
import com.hackathon.quackhacks.backend.Recipe;

import java.util.List;

// View to represent a recipe
public class RecipeView extends BaseView {

    public RecipeView(Context context) {
        super(context);
    }

    public RecipeView(Context context, Recipe recipe) {
        super(context);
        activity.setContentView(R.layout.recipedisplay);

        TextView recipeTitle = activity.findViewById(R.id.recipetitle);
        recipeTitle.setText(recipe.getTitle());

        TextView description = activity.findViewById(R.id.description);
        description.setText(recipe.getDescription());

        TextView instructions = activity.findViewById(R.id.instuctions);
        instructions.setText(recipe.getInstructions());


        List<String> ingredients = recipe.getIngredients();
        List<Integer> quantities = recipe.getQuantities();
        List<String> units = recipe.getUnits();

        StringBuilder ingredientString = new StringBuilder();

        for (int i = 0; i < ingredients.size(); i++) {
            ingredientString.append(quantities.get(i)).append(" ").append(units.get(i)).append("(s) of ").append(ingredients.get(i)).append("\n");
        }

        ((TextView) activity.findViewById(R.id.ingredients)).setText(ingredientString.toString());
        activity.findViewById(R.id.ExitPost).setOnClickListener(onclick -> activity.changeView(new FeedView(context)));

    }
}
