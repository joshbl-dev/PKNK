package com.hackathon.quackhacks.views;

import android.content.Context;
import android.widget.TextView;

import com.hackathon.quackhacks.R;
import com.hackathon.quackhacks.backend.Recipe;

import java.util.List;

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

        TextView ingredients = activity.findViewById(R.id.ingredients);
        List<String> ing = recipe.getIngredients();
        List<Integer> qua = recipe.getQuantity();
        List<String> uni = recipe.getUnits();

        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < ing.size(); i++) {
            temp.append(qua.get(i)).append(" ").append(uni.get(i)).append("(s) of ").append(ing.get(i)).append("\n");
        }
        ingredients.setText(temp.toString());
        activity.findViewById(R.id.ExitPost).setOnClickListener(onclick -> activity.changeView(new FeedView(context)));

    }
}
