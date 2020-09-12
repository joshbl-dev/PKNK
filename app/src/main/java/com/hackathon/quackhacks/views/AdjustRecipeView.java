package com.hackathon.quackhacks.views;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hackathon.quackhacks.R;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class AdjustRecipeView extends BaseView {

    public Map<String, TextView> recMap = new HashMap<>();

    public AdjustRecipeView(Context context) {
        super(context);
    }

    public AdjustRecipeView(Context context, String recipeName) {
        super(context);

        activity.setContentView(R.layout.adjust_recipe);

        TextView title = activity.findViewById(R.id.editTextTextPersonName);
        title.setText(recipeName);

        activity.findViewById(R.id.button4).setOnClickListener(onclick -> {
            EditText ingredient = activity.findViewById(R.id.editTextTextPersonName2);
            EditText quantity = activity.findViewById(R.id.editTextNumber);
            EditText unit = activity.findViewById(R.id.unit);

            if (ingredient.getText().toString().isEmpty()) {
                ingredient.setError("Please enter an ingredient");
            } else if (quantity.getText().toString().isEmpty()) {
                quantity.setError("Please enter a quantity");
            } else if (unit.getText().toString().isEmpty()) {
                unit.setError("Please enter a unit");
            } else {
                activity.getProfile().adjustRecipe(activity, recipeName, ingredient.getText().toString(), parseInt(quantity.getText().toString()), unit.getText().toString());

                LinearLayout lay = activity.findViewById(R.id.lin);

                TextView textView1 = new TextView(activity);
                textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                textView1.setGravity(Gravity.CENTER);
                textView1.setText(String.format("%s %s(s) of %s", quantity.getText().toString(), unit.getText().toString(), ingredient.getText().toString()));
                textView1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
                textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
                lay.addView(textView1);
                recMap.put(ingredient.getText().toString().toLowerCase(), textView1);


                ingredient.setText("");
                quantity.setText("");
                unit.setText("");
            }
        });

        activity.findViewById(R.id.removeIng).setOnClickListener(onclick -> {
            EditText ingredient = activity.findViewById(R.id.editTextTextPersonName2);

            TextView textView = recMap.get(ingredient.getText().toString().toLowerCase());
            activity.getProfile().removeIng(activity, recipeName, ingredient.getText().toString());

            ingredient.setText("");

            if (textView != null) {
                textView.setVisibility(View.INVISIBLE);
                textView.setHeight(0);
            }
        });

        activity.findViewById(R.id.button3).setOnClickListener(onclick -> activity.changeView(new FeedView(context)));

        activity.findViewById(R.id.button5).setOnClickListener(onclick -> {
            EditText instructions = activity.findViewById(R.id.editTextTextPersonName5);
            EditText description = activity.findViewById(R.id.editTextTextPersonName3);

            if (instructions.getText().toString().isEmpty()) {
                instructions.setError("Please enter instructions");
            }
            else if (description.getText().toString().isEmpty()) {
                instructions.setError("Please enter a description");
            }
            else {
                activity.getProfile().addResDesc(activity, recipeName, description.getText().toString(), instructions.getText().toString());
                //recipe.addDesc(description.getText().toString(), instructions.getText().toString());
                activity.getDatabase().setValue(activity.getProfile().getRecipes(), "users", activity.getProfile().getUsername(), "recipes");
                activity.changeView(new FeedView(context));
            }


        });


    }
}
