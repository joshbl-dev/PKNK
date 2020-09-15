package com.hackathon.quackhacks.views;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hackathon.quackhacks.R;
import com.hackathon.quackhacks.backend.Recipe;
import com.hackathon.quackhacks.backend.UserAccount;

import java.util.HashMap;
import java.util.Map;

public class AdjustRecipeView extends BaseView {

    public Map<String, TextView> recipeTextViews = new HashMap<>();

    public AdjustRecipeView(Context context) {
        super(context);
    }

    public AdjustRecipeView(Context context, String recipeName) {
        super(context);

        activity.setContentView(R.layout.adjust_recipe);

        TextView title = activity.findViewById(R.id.recipeName);
        title.setText(recipeName);

        // Add an ingredient
        activity.findViewById(R.id.addIngredientBtn).setOnClickListener(onclick -> {
            EditText ingredient = activity.findViewById(R.id.ingredientInput);
            EditText quantity = activity.findViewById(R.id.quantityInput);
            EditText unit = activity.findViewById(R.id.unitInput);

            String ingredientStr = ingredient.getText().toString();
            String quantityStr = quantity.getText().toString();
            String unitStr = unit.getText().toString();


            if (ingredientStr.isEmpty()) {
                ingredient.setError("Please enter an ingredient");
            } else if (quantityStr.isEmpty()) {
                quantity.setError("Please enter a quantity");
            } else if (unitStr.isEmpty()) {
                unit.setError("Please enter a unit");
            } else {
                UserAccount user = activity.getProfile();
                user.getRecipe(recipeName).addIngredient(activity.getDatabase(), user.getUsername(), ingredientStr, Integer.parseInt(quantityStr), unitStr);

                // UI addition Ingredient
                LinearLayout ingredientList = activity.findViewById(R.id.ingredientList);

                TextView textView = new TextView(activity);
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                textView.setGravity(Gravity.CENTER);
                textView.setText(String.format("%s %s(s) of %s", quantityStr, unitStr, ingredientStr));
                textView.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
                textView.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
                ingredientList.addView(textView);
                recipeTextViews.put(ingredientStr.toLowerCase(), textView);

                ingredient.setText("");
                quantity.setText("");
                unit.setText("");
            }
        });

        // Remove ingredient
        activity.findViewById(R.id.removeIngredientBtn).setOnClickListener(onclick -> {
            EditText ingredient = activity.findViewById(R.id.ingredientInput);

            String ingredientStr = ingredient.getText().toString();

            // UI removal Ingredient
            TextView textView = recipeTextViews.get(ingredientStr.toLowerCase());

            UserAccount user = activity.getProfile();
            user.getRecipe(recipeName).removeIngredient(activity.getDatabase(), user.getUsername(), ingredientStr);

            ingredient.setText("");

            if (textView != null) {
                textView.setVisibility(View.INVISIBLE);
                textView.setHeight(0);
            }
        });

        // Return to feed
        activity.findViewById(R.id.exitBtn_adjust_recipe).setOnClickListener(onclick -> activity.changeView(new FeedView(context)));

        // Save recipe
        activity.findViewById(R.id.saveRecipeBtn).setOnClickListener(onclick -> {
            EditText instructions = activity.findViewById(R.id.instructionsText);
            EditText description = activity.findViewById(R.id.descriptionText);

            String instructionsStr = instructions.getText().toString();
            String descriptionStr = description.getText().toString();

            if (instructionsStr.isEmpty()) {
                instructions.setError("Please enter instructions");
            } else if (descriptionStr.isEmpty()) {
                instructions.setError("Please enter a description");
            } else {
                UserAccount user = activity.getProfile();

                // Alter recipe
                Recipe recipe = user.getRecipe(recipeName);
                recipe.setDescription(descriptionStr);
                recipe.setInstructions(instructionsStr);

                // Save recipe
                activity.getDatabase().setValue(user.getRecipes(), "users", user.getUsername(), "recipes");
                activity.changeView(new FeedView(context));
            }


        });


    }
}
